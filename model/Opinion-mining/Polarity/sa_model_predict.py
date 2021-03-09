# -*- coding: utf-8 -*-
from predict import SA_Model_Predict
import numpy as np
from snownlp import SnowNLP

def model_predict():
    model = SA_Model_Predict(tokenize_path, sa_model_path_m, max_len=100)
    sa_score = model.predict(predict_text)
    # 情感极性输出
    print([i[1] for i in sa_score])
    print([np.argmax(i) for i in sa_score])

def snowNLP_predict():
    sa_score = []
    for text in predict_text:
        sa_score.append(SnowNLP(text).sentiments)
    # print([i[1] for i in sa_score])
    # print([np.argmax(i) for i in sa_score])
    print(sa_score)

if __name__ == '__main__':
    # load conf
    from setting import *
    # example
    predict_text = ['这个我不喜欢', '这个我喜欢不']
    model_predict()
    # snowNLP_predict()
