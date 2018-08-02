import os
from PIL import Image
from resizeimage import resizeimage


data_dir = "/home/yash/Downloads/CarBike/"
data_dir2 = "/home/yash/Downloads/CarBike/Resized/"
listing = os.listdir(data_dir)

for file in listing:
	image = Image.open(data_dir+file)
	img = resizeimage.resize_cover(image, [200, 200], validate=False)
    gray = img.convert('L')
	gray.save(data_dir2+file, image.format)
