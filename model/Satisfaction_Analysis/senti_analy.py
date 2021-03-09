'''
'情感得分', 'TF-IDF关键词', 'TextRank关键词'形成csv列表文件
'''
import codecs

import jieba
import numpy as np
from collections import defaultdict
from jieba import analyse
import csv

from setting import topic_path


def LoadDict():
    """Load Dict form disk
    Returns:
        senti_word: senti word dict (BosonNLP)
        not_word: not word dict  （情感极性词典.zip）
        degree_word: degree word dict （大连理工）
    """
    # Sentiment word
    senti_file = open('./data/dict.txt', 'r+', encoding='utf-8')
    # 获取字典内容
    # 去除'\n'
    senti_list = senti_file.read().splitlines()
    # 创建情感字典
    senti_dict = defaultdict()
    # 读取字典文件每一行内容，将其转换为字典对象，key为情感词，value为对应的分值
    for s in senti_list:
        # 对每一行内容根据空格分隔，索引0是情感词，1是情感分值
        if len(s.split(' ')) == 2:
            senti_dict[s.split(' ')[0]] = s.split(' ')[1]

    not_words = open('./data/否定词.txt', encoding='UTF-8').readlines()
    not_dict = {}
    for w in not_words:
        word = w.strip()
        not_dict[word] = float(-1)

    degree_words = open('./data/degreeDict.txt', 'r+', encoding='utf-8').readlines()
    degree_dict = {}
    for w in degree_words:
        word, score = w.strip().split(',')
        degree_dict[word] = float(score)

    return senti_dict, not_dict, degree_dict


senti_dict, not_dict, degree_dict = LoadDict()


def LocateSpecialWord(senti_dict, not_dict, degree_dict, sent):
    """Find the location of Sentiment words, Not words, and Degree words
    The idea is pretty much naive, iterate every word to find the location of Sentiment words,
    Not words, and Degree words, additionally, storing the index into corresponding arrays
    SentiLoc, NotLoc, DegreeLoc.
    Args:
        senti_word: BosonNLP
        not_word: not word location dict  （情感极性词典.zip）
        degree_word: degree word location dict （大连理工）
    Returns:
        senti_word: senti word location dict, with word and their location in the sentence
        not_word: not word location dict
        degree_word: degree word location dict
    """
    senti_word = {}
    not_word = {}
    degree_word = {}

    for index, word in enumerate(sent):
        if word in senti_dict:
            senti_word[index] = senti_dict[word]
        elif word in not_dict:
            not_word[index] = -1.0
        elif word in degree_dict:
            degree_word[index] = degree_dict[word]

    return senti_word, not_word, degree_word


def ScoreSent(senti_word, not_word, degree_word, words):
    """Compute the sentiment score of this sentence
        基于词典的情感分析算法
    Args:
        senti_word: BosonNLP
        not_word: not word location dict  （情感极性词典.zip）
        degree_word: degree word location dict （大连理工）
        words: The tokenized word list.
    Returns:
        score: The sentiment score
    """
    W = 1
    score = 0

    # The location of sentiment words
    senti_locs = list(senti_word.keys())
    not_locs = list(not_word.keys())
    degree_locs = list(degree_word.keys())

    sentiloc = -1

    # iterate every word, i is the word index ("location")
    for i in range(0, len(words)):
        # if the word is positive
        if i in senti_locs:
            sentiloc += 1
            # update sentiment score
            score += W * float(senti_word[i])

            if sentiloc < len(senti_locs) - 1:
                # if there exists Not words or Degree words between
                # this sentiment word and next sentiment word
                # j is the word index ("location")
                for j in range(senti_locs[sentiloc], senti_locs[sentiloc + 1]):
                    # if there exists Not words
                    if j in not_locs:
                        W *= -1
                    # if there exists degree words
                    elif j in degree_locs:
                        W *= degree_word[j]

    return score


def seg_word(sentence):
    """使用jieba对文档分词"""
    seg_list = jieba.cut(sentence)
    seg_result = []
    for w in seg_list:
        seg_result.append(w)
    # 读取停用词文件
    stopwords = set()
    fr = codecs.open('data/stopWord.txt', 'r', 'utf-8')
    for word in fr:
        stopwords.add(word.strip())
    fr.close()
    # 去除停用词
    return list(filter(lambda x: x not in stopwords, seg_result))


def senti_analy(keyword, product):
    key_txt = open('{}/{}.txt'.format(topic_path, keyword), 'r', encoding='utf-8').readlines()
    print('='*40)
    print('词典情感分析')
    print('-'*40)
    print('Sentiment Analysis')
    print('-'*40)
    out = open('./词典/Senti_{}_{}.csv'.format(keyword, product), 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(out, dialect='excel')
    stu1 = ['content', 'score']
    csv_write.writerow(stu1)
    senti_dict, not_dict, degree_dict = LoadDict()
    count = 1
    score_list = []
    for sentence in key_txt:
        # print("处理到：", count)
        senti = []
        senti.append(sentence)
        seg_list = seg_word(sentence)
        senti_word, not_word, degree_word = LocateSpecialWord(senti_dict, not_dict, degree_dict, seg_list)
        score = ScoreSent(senti_word, not_word, degree_word, seg_list)
        score_list.append(score)
        senti.append(score)

        csv_write.writerow(senti)
        count += 1
    average_score = np.mean(score_list)
    print('词典{}平均分:'.format(keyword)+str(average_score))
    pos = 0
    mid = 0
    for score in score_list:
        if score > 0:
            pos += 1
        elif score == 0:
            mid += 1
    pos_rating = pos/(len(score_list)-mid)
    print('词典{}好评率:'.format(keyword)+str(pos_rating))
    out = open('./词典/Senti_rating_dict.csv', 'a', newline='', encoding='utf-8')
    csv_write = csv.writer(out, dialect='excel')
    senti = [product, keyword, str(pos_rating)]
    csv_write.writerow(senti)


if __name__ == '__main__':
    senti_analy('包装')