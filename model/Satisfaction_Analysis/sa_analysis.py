# -*- coding: utf-8 -*-
import codecs
import csv

import jieba.posseg as pseg
from predict import SA_Model_Predict
import matplotlib.pyplot as plt
from setting import *
import numpy as np
import pandas as pd
import os
# from snownlp import SnowNLP
# from senti_analy import senti_analy
plt.rcParams['font.sans-serif'] = ['SimHei']
plt.rcParams['axes.unicode_minus'] = False

def topic_sa_analysis(product):
    sa_model = SA_Model_Predict(tokenize_path, sa_model_path_m, max_len=100)
    if not os.path.exists(topic_emotion_pic):
        os.mkdir(topic_emotion_pic)
        print(topic_emotion_pic+'文件夹已经建立，请查看当前文件路径')
    for key_word in topic_words_list1.keys():
        sa_analysis_(key_word, sa_model, product)    #模型预测
        # wordcount(key_word, product)   #关键词统计
        # snowNLP_predict(key_word, product)  #snowNLP模型预测
        # senti_analy(key_word, product)  #情感词典方法


def sa_analysis_(key_word, sa_model, product):
    print('{} 正在执行...'.format(key_word))
    key_txt = open('{}/{}process.txt'.format(topic_path, key_word), 'r', encoding='utf-8').readlines()
    sentiments_score_predict = sa_model.predict(key_txt)
    # 情感极性输出
    sentiments_score_list = [i[1] for i in sentiments_score_predict]
    #平均值
    average_score = np.mean(sentiments_score_list)
    print(key_word + '平均值为：' + str(average_score))
    output = open('./output/Senti_average_litNLP.csv', 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(output, dialect='excel')
    senti = [product, key_word, str(average_score)]
    csv_write.writerow(senti)
    #保存每个的分值
    sentiments_score = pd.DataFrame(data=sentiments_score_list)
    sentiments_score.to_csv('{}/{}.csv'.format(topic_path, key_word), encoding='utf-8')
    #计算好评率
    pos = 0
    for score in sentiments_score_list:
        if score >= 0.5:
            pos += 1
    pos_rating = pos/len(sentiments_score_list)
    print(key_word + '好评率为：' + str(pos_rating))
    out = open('./litNLP/Senti_rating_litNLP.csv', 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(out, dialect='excel')
    senti = [product, key_word,str(pos_rating)]
    csv_write.writerow(senti)
    #绘制图片
    # plt.hist(sentiments_score_list, bins=np.arange(0, 1, 0.01))
    # plt.xlabel("情感值")
    # plt.ylabel("评论数目")
    # plt.title(key_word+'-情感极性分布图')
    # plt.savefig('{}/{}.png'.format(topic_emotion_pic, key_word))
    # plt.show()
    # plt.close()
    # print('{} 情感极性图完成'.format(key_word))


'''使用snowNLP方法'''
# def snowNLP_predict(key_word,product):
#     print('{} snowNLP正在执行...'.format(key_word))
#     sentiments_score_list = []
#     key_txt = open('{}/{}process.txt'.format(topic_path, key_word), 'r', encoding='utf-8').readlines()
#     for key in key_txt:
#         sentiments_score_list.append(SnowNLP(key).sentiments)
#     average_score = np.mean(sentiments_score_list)
#     sentiments_score = pd.DataFrame(data=sentiments_score_list)
#     sentiments_score.to_csv('{}/{}snowNLP.csv'.format(topic_path, key_word), encoding='utf-8')
#     print(key_word + 'snowNLP平均值为：' + str(average_score))
#     output = open('./snowNLP/Senti_average_snowNLP.csv'.format(product), 'a', newline='', encoding='utf-8')
#     csv_write = csv.writer(output, dialect='excel')
#     senti = [product, key_word, str(average_score)]
#     csv_write.writerow(senti)
#     pos = 0
#     for score in sentiments_score_list:
#         if score >= 0.5:
#             pos += 1
#     pos_rating = pos / len(sentiments_score_list)
#     print(key_word + 'snowNLP好评率为：' + str(pos_rating))
#     out = open('./snowNLP/Senti_rating_snowNLP.csv'.format(product), 'a', newline='', encoding='utf-8')
#     csv_write = csv.writer(out, dialect='excel')
#     senti = [product, key_word, str(pos_rating)]
#     csv_write.writerow(senti)
    # plt.hist(sentiments_score_list, bins=np.arange(0, 1, 0.01))
    # plt.xlabel("情感值")
    # plt.ylabel("评论数目")
    # plt.title(key_word + '-情感极性分布图')
    # plt.savefig('{}/{}snowNLP.png'.format(topic_emotion_pic, key_word))
    # plt.show()
    # plt.close()
    # print('{} 情感极性图完成'.format(key_word))


'''获取词频统计'''
def wordcount(key_word,product):
    words = []
    print('{} 正在执行...'.format(key_word))
    key_txt = open('{}/{}process.txt'.format(topic_path, key_word), 'r', encoding='utf-8').readlines()
    # 读取停用词文件
    stopkey = [w.strip() for w in codecs.open('data/stopWord.txt', 'r', 'utf-8').readlines()]
    stopkey.append(key_word)
    pos = ['vd', 'vn', 'a', 'ad', 'al', 'an']  # 定义选取的词性
    for key_line in key_txt:
        for word,flag in pseg.lcut(key_line):
            if word not in stopkey and flag in pos:
                words.append(word)
    print(words)
    word_count = pd.Series(words).value_counts()
    print(word_count[:10])
    keywords = pd.DataFrame(data=word_count)
    keywords.to_csv('{}/{}keyword{}.csv'.format(topic_path, key_word, product), encoding='utf-8')


'''获取整个产品的情感好评率'''
def sa_analysis_new(sa_model,product):
    print('正在执行...')
    key_txt = open('data/sentence_cut_new_process.txt', 'r', encoding='utf-8').readlines()
    sentiments_score_predict = sa_model.predict(key_txt)
    # 情感极性输出
    sentiments_score_list = [i[1] for i in sentiments_score_predict]
    #平均值
    average_score = np.mean(sentiments_score_list)
    print('平均值为：' + str(average_score))
    output = open('./output/Senti_average_new.csv', 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(output, dialect='excel')
    senti = [product, str(average_score)]
    csv_write.writerow(senti)
    #保存每个的分值
    sentiments_score = pd.DataFrame(data=sentiments_score_list)
    sentiments_score.to_csv('{}/{}.csv'.format(topic_path, product), encoding='utf-8')
    #计算好评率
    pos = 0
    for score in sentiments_score_list:
        if score >= 0.5:
            pos += 1
    pos_rating = pos/len(sentiments_score_list)
    print(product + '好评率为：' + str(pos_rating))
    out = open('./output/Senti_rating_new.csv', 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(out, dialect='excel')
    senti = [product, str(pos_rating)]
    csv_write.writerow(senti)
    #绘制图片
    plt.hist(sentiments_score_list, bins=np.arange(0, 1, 0.01))
    plt.xlabel("情感值")
    plt.ylabel("评论数目")
    plt.title(product+'-情感极性分布图')
    plt.savefig('{}/{}.png'.format(topic_emotion_pic, product))
    plt.show()
    plt.close()
    print('{} 情感极性图完成'.format(product))

if __name__ == '__main__':
#     # 添加多线程提升预测速度
#     topic_sa_analysis('禾煜')
    sa_model = SA_Model_Predict(tokenize_path, sa_model_path_m, max_len=100)
    sa_analysis_new(sa_model, '老干妈')