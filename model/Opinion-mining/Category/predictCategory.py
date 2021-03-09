"""预测实体识别后的类别"""
import csv

from predict import Category_Model_Predict
from setting import *
import pandas as pd
import numpy as np

dict = {0: '口味', 1: '包装', 2: '价格', 3: '物流', 4: '体验', 5: '整体'}


def _read_tsv(input_file, quotechar=None):
    """Reads a tab separated value file."""
    with open(input_file, "r", encoding='utf-8') as f:
        # reader = csv.reader(f, delimiter="\t", quotechar=quotechar)
        reader = csv.reader(f, quotechar=quotechar)
        lines = []
        count = 0
        for line in reader:
            if count > 0:
                lines.append(line[1]+line[2])
            count += 1
        print(lines)
        return lines


def ca_analysis(lines):
    ca_model = Category_Model_Predict(tokenize_path, ca_model_path_m, max_len=100)
    ca_predict = ca_model.predict(lines)
    # 情感极性输出
    category_list = [np.argmax(i) for i in ca_predict]
    # 保存每个的分值
    sentiments_score = pd.DataFrame(data=category_list)
    print(sentiments_score.head())
    sentiments_score['label'] = sentiments_score[0].apply(lambda x: fun(x))
    print(sentiments_score.head())
    sentiments_score = sentiments_score['label']
    sentiments_score.to_csv('./output/pre_result.csv', encoding='utf-8', index=0, header=1)


def fun(x):
    result = dict.get(x)
    # print(result)
    return result


if __name__ == '__main__':
    lines = _read_tsv('./data/test.tsv')
    ca_analysis(lines)
    # fun(1)
