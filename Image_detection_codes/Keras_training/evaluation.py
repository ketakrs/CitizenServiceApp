import numpy as np
np.random.seed(123)  # for reproducibility
import keras
from keras.utils import np_utils
from dataset_pothole import pothole
from keras.models import model_from_json









(X_train, y_train), (X_test, y_test) = pothole.load_data()
X_test = X_test.reshape(X_test.shape[0], 50, 50, 3)
Y_test = np_utils.to_categorical(y_test, 3)

print(X_test.shape)
print(Y_test.shape)
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
# load weights into new model
loaded_model.load_weights("model.h5")
loaded_model.compile(loss='categorical_crossentropy',
              optimizer='adam',
              metrics=['accuracy'])

score = loaded_model.evaluate(X_test, Y_test, verbose=0)
print('Test loss: ', score[0])
print('Test accuracy: ', score[1])


#print(loaded_model.predict(X_test[180:190]))
#print(Y_test[180:190])

