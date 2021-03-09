"""预测实体识别后的情感极性"""
import csv

from predict import SA_Model_Predict
from setting import *
import pandas as pd
from snownlp import SnowNLP


def _read_tsv(input_file, quotechar=None):
    """Reads a tab separated value file."""
    with open(input_file, "r", encoding='utf-8') as f:
        # reader = csv.reader(f, delimiter="\t", quotechar=quotechar)
        reader = csv.reader(f, quotechar=quotechar)
        lines = []
        count = 0
        for line in reader:
            if count > 0:
                lines.append(line[1])
            count += 1
        print(lines)
        return lines

def _read_label_tsv(input_file, quotechar=None):
    """Reads a tab separated value file."""
    with open(input_file, "r", encoding='utf-8') as f:
        # reader = csv.reader(f, delimiter="\t", quotechar=quotechar)
        reader = csv.reader(f, quotechar=quotechar)
        lines = []
        count = 0
        for line in reader:
            if count > 0:
                lines.append(line[0])
            count += 1
        print(lines)
        return lines


def sa_analysis(lines):
    sentiments_score_list1 = []
    sa_model = SA_Model_Predict(tokenize_path, sa_model_path_m, max_len=100)
    sentiments_score_predict = sa_model.predict(lines)
    # for line in lines:
    #     sentiments_score_list1.append(SnowNLP(line).sentiments)
    # 情感极性输出
    sentiments_score_list = [i[1] for i in sentiments_score_predict]
    # 保存每个的分值
    sentiments_score = pd.DataFrame(data=sentiments_score_list)
    # sentiments_score1 = pd.DataFrame(data=sentiments_score_list1)
    print(sentiments_score.head())
    # sentiments_score['label'] = sentiments_score[0].apply(lambda x: fun(x))
    # sentiments_score1['label'] = sentiments_score1[0].apply(lambda x: fun(x))
    sentiments_score['label'] = sentiments_score[0]
    print(sentiments_score.head())
    sentiments_score = sentiments_score['label']
    # sentiments_score1 = sentiments_score1['label']
    sentiments_score.to_csv('./output/pre_result.csv', encoding='utf-8',index=0,header=1)
    # sentiments_score1.to_csv('./output/pre_result1.csv', encoding='utf-8', index=0, header=1)


def fun(x):
    if x >= 0.5:
        return '正面'
    else:
        return '负面'

def score():
    result_file = './output/pre_result.csv'
    test_file = './data/test.tsv'
    lines = _read_label_tsv(result_file)
    test_lines = _read_label_tsv(test_file)
    count = 0
    for i in range(len(lines)):
        if lines[i] == test_lines[i]:
            count += 1
    print(float(count)/float(len(lines)))


if __name__ == '__main__':
    lines = _read_tsv('./data/test.tsv')
    sa_analysis(lines)
    # score()