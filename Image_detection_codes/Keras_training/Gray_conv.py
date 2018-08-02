import os
from PIL import Image

path1 = '/home/yash/ProjectDB/PotholeDataset/rgb_img_28/'    #path of folder of images    
path2 = '/home/yash/ProjectDB/PotholeDataset/gr28/'  #path of folder to save images    

listing = os.listdir(path1) 

for file in listing:
    im = Image.open(path1 + file)
    gray = im.convert('L')
                #need to do some more processing here           
    gray.save(path2 + file, im.format)
