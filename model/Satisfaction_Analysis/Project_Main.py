# -*- coding: utf-8 -*-
import codecs
import csv

# from sa_analysis import topic_sa_analysis
import jieba.posseg as pseg
# from sa_model_train import model_train
import multiprocessing
from setting import *
from tqdm import tqdm
import os
import pandas as pd
import shutil

data_List = []
# 识别标点符号进行句子切分
def doc2sentence(resource_text):
    # jieba 预热
    print(pseg.cut('预热'))
    with open(sentence_cut_path, 'w', encoding='utf-8') as sentence_cut:
        for sentence in tqdm(resource_text):
            if len(sentence.strip()) > 1:
                for word, flag in pseg.cut(sentence):
                    if flag != 'x':
                        sentence_cut.write(word)
                    else:
                        sentence_cut.write('\n')
    file1 = open(sentence_cut_path, 'r', encoding='utf-8')  # 打开要去掉空行的文件
    file2 = open(sentence_cut_process_path, 'w', encoding='utf-8')  # 生成没有空行的文件

    for line in file1.readlines():
        if line == '\n':
            line = line.strip("\n")
        file2.write(line)
    print('输出成功....')
    file1.close()
    file2.close()

# 多线程主题句查询
def find_topic_sentence():
    if not os.path.exists(topic_path):
        os.mkdir(topic_path)
    task_split = []
    for topic_n, key_words_list in topic_words_list1.items():
        task_split.append([topic_path, topic_n, key_words_list])   #读取主题词list到task_split中
    # task multiprocessing
    thread_number = len(topic_words_list1.keys())   #设置线程数
    pool = multiprocessing.Pool(processes=thread_number)
    pool.map(find_key_txt, task_split)
def find_key_txt(data_list):
    sentence_cut = open(sentence_cut_path, 'r', encoding='utf-8')
    with open('{}/{}.txt'.format(data_list[0], data_list[1]), 'w', encoding='utf-8') as key_txt:
        for sentence in sentence_cut.readlines():
            for i in data_list[2]:
                if i in sentence:
                   key_txt.write(sentence)
    # 关闭文件
    sentence_cut.close()
    lines_seen = set()
    outfiile = open('{}/{}process.txt'.format(data_list[0], data_list[1]), 'w', encoding='utf-8')
    f = open('{}/{}.txt'.format(data_list[0], data_list[1]), 'r', encoding='utf-8')
    for line in f:
        if line not in lines_seen:
            outfiile.write(line)
            lines_seen.add(line)
    print('{} 已经查找完成'.format(data_list[1]))


def wordcount(product):
    words = []
    key_txt = open('data_3D/{}.txt'.format(product), 'r', encoding='utf-8').readlines()
    # 读取停用词文件
    stopkey = [w.strip() for w in codecs.open('data/stopWord.txt', 'r', 'utf-8').readlines()]
    stopkey.append(product)
    stopkey.append("大")
    stopkey.append("小")
    pos = ['vd', 'vn', 'a', 'ad', 'al', 'an']  # 定义选取的词性
    for key_line in key_txt:
        for word,flag in pseg.lcut(key_line):
            if word not in stopkey and flag in pos:
                words.append(word)
    print(words)
    word_count = pd.Series(words).value_counts()
    print(word_count[:10])
    keywords = pd.DataFrame(data=word_count)
    keywords.to_csv('wordcount{}.csv'.format(product), encoding='utf-8')


def read_product(brandFile):
    with open(brandFile, 'r', encoding="utf8") as csvfile:
        csv_reader = csv.reader(csvfile)
        count = 0
        for row in csv_reader:
            if (count >= 1):
                data_List.append(row[0])
            count += 1
        print(count)



if __name__ == '__main__':
    # 情感分析模型训练
    # model_train()

    with open('data/txt/alljd.csv', 'r', encoding='utf-8') as resource_text:
        resource_text = resource_text.readlines()
            # 整句切分
        doc2sentence(resource_text)
            # 主题句查询
        find_topic_sentence()
            # 主题情感极性可视化
        # topic_sa_analysis('老干妈')
    # wordcount('泰奇')