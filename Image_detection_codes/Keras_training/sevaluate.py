#import numpy as np
import keras
from keras.utils import np_utils
from keras.models import model_from_json
from PIL import Image
#from numpy import *
#np.random.seed(123)  # for reproducibility
#import os
#from keras.models import Sequential
#from keras.layers import Dense, Dropout, Activation, Flatten
#from keras.layers import Convolution2D, MaxPooling2D



def MyPrediction(str):
	test = array(array(Image.open(str).resize((50,50))).flatten())

	#print(test.shape)
	X_test = test.reshape((1, 50, 50, 3))

	#print(X_test.shape)

	json_file = open('model.json', 'r')
	loaded_model_json = json_file.read()
	json_file.close()
	loaded_model = model_from_json(loaded_model_json)
	# load weights into new model
	loaded_model.load_weights("model.h5")
	#loaded_model.compile(loss='categorical_crossentropy',
	#              optimizer='adam',
	#              metrics=['accuracy'])
	#prediction = loaded_model.predict_classes(X_test)
	#prob = loaded_model.argmax(X_test)

	#print(prediction)
	proba = loaded_model.predict(X_test)
	#print(proba)

	if ((proba[0][1]>0.999999999999999) & (proba[0][0]<0.0000000000000001)):
		return 1#print("Pothole (Probability: %d)" %(proba[0][1].astype('int32')*100))
	elif ((proba[0][1]<0.00000000000001) & (proba[0][0]>0.999999999999999)):
		return 2 #print("Garbage (Probability: %d)" %(proba[0][0].astype('int32')*100))
	else:
		return 3#print("Unable to predict, please try again")


img = "/home/yash/ProjectDB/PotholeDataset/Dataset/pic37.jpg" #input("Please enter the test filename:")
flag = MyPrediction(img)
print(flag)
