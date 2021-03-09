# -*- coding: utf-8 -*-
# @USER: CarryChang

# 设置词典大小为 n_features
n_features = 3000

# 存储断句的文件夹
sentence_cut_path = 'data/sentence_cut_new.txt'
sentence_cut_process_path = 'data/sentence_cut_new_process.txt'

# 主题句文件夹
topic_path = 'output'

# 存储情感极性的图
topic_emotion_pic = 'real'

# 最大句子长度
maxlen = 100

# 最大的tokenizer字典长度
max_words = 1000

# 设置embedding大小
embedding_dim = 300

# train_method : 模型训练方式textcnn
train_method = 'textcnn'

# 模型的保存位置，后续用于推理
sa_model_path_m = 'model_saved/model4.h5'

# 离线保存tokenizer
tokenize_path = 'model_saved/tokenizer4.pickle'