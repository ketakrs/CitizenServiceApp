import os
from PIL import Image
from resizeimage import resizeimage


data_dir = "/home/yash/Downloads/small_holes/"
data_dir2 = "/home/yash/Downloads/small_holes_128/"
listing = os.listdir(data_dir)

for file in listing:
	image = Image.open(data_dir+file)
	cover = image.resize((128,128))
	cover.save(data_dir2+file, image.format)
