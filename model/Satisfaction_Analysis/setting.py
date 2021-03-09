# -*- coding: utf-8 -*-
# @USER: CarryChang

# 设置词典大小为 n_features
n_features = 3000

# 存储断句的文件夹
sentence_cut_path = 'data/sentence_cut_new.txt'
sentence_cut_process_path = 'data/sentence_cut_new_process.txt'

# 主题句文件夹
topic_path = 'output'

# 基于字典的查找
topic_words_list1 = {
    '包装': {'包装', '罐', '盖子', '质量', '漏'},
    '价格': {'价格', '性价比', '价位', '单价', '价钱', '优惠'},
    '物流': {'物流', '配送', '快递', '发货'},
    '味道': {'味道', '口味', '口感', '早餐', '粥', '食材', '辣', '营养', '吃'},
    '服务': {'服务', '态度', '客服', '投诉', '工作人员', '店家', '商家', '体验'},
    '整体': {'推荐','失望','支持','品质','喜欢','日期','惊喜','绿色','分量','适合'}
}
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