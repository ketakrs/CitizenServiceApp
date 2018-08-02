import numpy as np

def load_data(path='/home/yash/ProjectDB/PotholeDataset/rgb50_new_data.npz'):
    """

    # Arguments
        path: path where to cache the dataset locally
            (relative to ~/.keras/datasets).

    # Returns
        Tuple of Numpy arrays: `(x_train, y_train), (x_test, y_test)`.
    """
    f = np.load(path)
    x_train, y_train = f['X_train'], f['y_train']
    x_test, y_test = f['X_test'], f['y_test']
    f.close()
    return (x_train, y_train), (x_test, y_test)
