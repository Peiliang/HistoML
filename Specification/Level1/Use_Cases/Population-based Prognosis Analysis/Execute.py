import numpy as np
from skimage.measure import label, regionprops, shannon_entropy
import math
import cv2
import os
import json
import scipy.io
import natsort
import sys
import subprocess
# from demo_lpl import run_model

cell_data = dict()
for img_file in natsort.natsorted(os.listdir('Dataset/Images/')):
	mat_file = 'Dataset/Labels/' + img_file.replace('png', 'mat')
	img_file = 'Dataset/Images/' + img_file
	data = scipy.io.loadmat(mat_file)
	img = cv2.imread(img_file)
	inst = data['instance_map']
	grade = data['class_map']
	tcga = str(data['TCGA_file_name'][0]).replace('.svs', '')
	print(tcga)

	output = []
	cnt = np.max(inst)
	for i in range(1, np.max(inst) + 1):
		binary = inst == i
		area = np.sum(inst == i)
		if area <= 0:
			continue
		clazz = int(np.max(grade[binary]))
		entropy = shannon_entropy(img[binary])
		region = regionprops(label(inst == i))[0]
		if region.perimeter <= 0:
			continue
		axis = region.major_axis_length
		circularity = 4 * math.pi * region.area / region.perimeter ** 2
		output.append((clazz, int(region.area), axis, circularity, entropy))

	if tcga in cell_data:
		cell_data[tcga] += output
	else:
		cell_data[tcga] = output

with open('CellData.json', 'w') as fout:
	json.dump(cell_data, fout, indent='\t')

args = ['java', '-jar', 'Analysis.jar']
for arg in sys.argv[1:]:
	args.append(arg)
subprocess.call(args)
