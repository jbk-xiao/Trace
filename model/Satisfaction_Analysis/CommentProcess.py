'''数据预处理，只选取最近一个月的评论'''
import csv
import os

import pandas as pd

csv_file_path = "D:\\项目\\Desktop\\评论"
file_list = []

def get_time_limit(file):
    file_name = file.replace(csv_file_path+'\\',"")
    keyword = file_name.replace(".csv", "")
    print(keyword)
    csv_file = file
    csv_data = pd.read_csv(csv_file, low_memory=False)  # 防止弹出警告
    csv_df = pd.DataFrame(csv_data)
    # print(csv_df.keys())
    csv_df = csv_df.loc[:,['评价内容','时间']]
    csv_df['keyword'] = keyword
    print(csv_df.head(2))
    csv_df.index = pd.to_datetime(csv_df['时间'])
    del csv_df['时间']
    print(csv_df.head(4))
    df = pd.Series(csv_df['评价内容'], index = csv_df.index)
    print(type(df))
    df.head(2)
    print(df['2021-01'])  # 获取某月的数据
    df['2021-01'].to_csv('./data_3D/1月{}.csv'.format(keyword),mode='a',index=0)


def get_file_list(path):
    files = os.listdir(path)
    for file in files:
        if os.path.isfile(path + '\\' + file):
            print(file)
            file_list.append(path + '\\' + file)
    print(file_list)


if __name__ == '__main__':

    get_file_list(csv_file_path)
    for file in file_list:
        get_time_limit(file)