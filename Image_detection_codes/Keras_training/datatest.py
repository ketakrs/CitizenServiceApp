import numpy as np
np.random.seed(123)  # for reproducibility
from keras.utils import np_utils
from keras.datasets import mnist
from dataset_pothole import pothole


# 4. Load pre-shuffled MNIST data into train and test sets
(X_train, y_train), (X_test, y_test) = pothole.load_data()
X_train = X_train.reshape(X_train.shape[0], 200, 200, 1)
X_test = X_test.reshape(X_test.shape[0], 200, 200, 1)


 
print(X_train.shape)
print()
print (y_train.shape)
print()
print(y_test.shape)
print()
print(X_test.shape)
