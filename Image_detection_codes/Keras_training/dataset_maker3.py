import os
from keras.utils import np_utils
import numpy as np
from sklearn.utils import shuffle
from PIL import Image
from numpy import *
from sklearn.cross_validation import train_test_split


path1 = '/home/yash/ProjectDB/PotholeDataset/gr32/'    #path of folder of images    

imlist = os.listdir(path1)

imnbr = len(imlist) # get the number of images

# create matrix to store all flattened images
immatrix = array([array(Image.open(path1 + im2)).flatten()
              for im2 in imlist],'f')
                
label=np.ones((8192,),dtype = int)
label[0:4096]=0
label[4096:]=1

data,Label = shuffle(immatrix,label, random_state=2)
train_data = [data,Label]


#%%
(X, y) = (train_data[0],train_data[1])


# STEP 1: split X and y into training and testing sets

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=4)


np.savez('gray32_data', X_train=X_train, X_test=X_test, y_train=y_train, y_test=y_test)