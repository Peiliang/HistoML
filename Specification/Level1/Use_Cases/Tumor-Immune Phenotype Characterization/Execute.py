import numpy as np
from skimage.measure import label, regionprops
import cv2
import sqlite3 as sql
import subprocess

conn = sql.connect('Annotation.db')
fout = open('Area.csv', 'w')
c = conn.cursor()

def process(id, points):
	mnx, mny, mxx, mxy = 1e6, 1e6, 0, 0
	contour = []
	for row in points:
		mnx = min(row[1] - 1, mnx)
		mny = min(row[2] - 1, mny)
		mxx = max(row[1] + 2, mxx)
		mxy = max(row[2] + 2, mxy)
		contour.append([row[1], row[2]])

	w, h = mxx - mnx, mxy - mny
	for point in contour:
		point[0] -= mnx
		point[1] -= mny
	img = np.zeros((h, w), dtype=np.uint8)
	contour = np.array(contour, dtype=np.int32)
	cv2.fillPoly(img, [contour], 255)

	label_img = label(img == 255)
	region = regionprops(label_img)[0]
	fout.write('%d,%d\n' % (id, region.area))

data = c.execute('SELECT * from Line')
points = []
id = 1
for row in data:
	if row[6] != id:
		process(id, points)
		id += 1
		points.clear()
	points.append(row)
process(id, points)

conn.close()
fout.close()

subprocess.call(['java', '-jar', 'CalculateTIL.jar'])
