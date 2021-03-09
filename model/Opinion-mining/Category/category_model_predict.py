# -*- coding: utf-8 -*-
from predict import Category_Model_Predict
import numpy as np


def model_predict():
    model = Category_Model_Predict(tokenize_path, ca_model_path_m, max_len=100)
    ca_score = model.predict(predict_text)
    print([np.argmax(i) for i in ca_score])


if __name__ == '__main__':
    # load conf
    from setting import *

    # example
    predict_text = ['口味非常不错', '不喜欢', '包装好丑']
    model_predict()
