import os,sys
from single_image_infer import Inferer, process_instance_micro
import numpy
import cv2

gpus = "5"
n_gpus = len(gpus.split(','))
os.environ['CUDA_VISIBLE_DEVICES'] = gpus
# test_picture_url = "/home1/gzy/NucleiSegmentation/High_CCRCC/Test/Images/high_grade_ccrcc_10.png"

if __name__ == "__main__":
    inferer = Inferer()
    print(sys.argv)
    original_picture_url = sys.argv[1]
    result_file_url = sys.argv[2]
#     annotation_file_url = sys.argv[3]

    pred_map = inferer.run(original_picture_url)
    pred_inst, pred_type = process_instance_micro(pred_map, inferer.nr_types)
    
    from scipy.io import savemat
    result = {'instance_map': pred_inst,
             'class_map': pred_type}
    savemat(result_file_url, result)
    
#     pred_inst += 1
#     annotation_data = []
#     pred_inst = pred_inst.astype(numpy.int16)
#     for i in range(numpy.max(pred_inst)):
#         try:
#             grade = numpy.max(pred_type[pred_inst == i])
#             if grade== 4:
#                 grade = 5
#         except:
#             grade = 0
#         annotation_data.append(grade)
#         if grade!=0:
#             temp = numpy.zeros(pred_inst.shape, dtype = numpy.uint8)
#             temp[pred_inst == i] =  255
#             contours, hierarchy = cv2.findContours(temp, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
#             pred_inst = cv2.drawContours(pred_inst, contours, -1, -1, 1)
      
#     numpy.savetxt(boundary_file_url, pred_inst, fmt='%d', delimiter=",")
#     numpy.savetxt(annotation_file_url, annotation_data, fmt='%d', delimiter=",")
      
    print("Finished!")
