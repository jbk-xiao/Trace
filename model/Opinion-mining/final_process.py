import argparse

import pandas as pd
import numpy as np
# 读入文件
import os


def readfile(fname, encoding='utf-8'):
    pass
    try:
        with open(fname, 'r', encoding=encoding) as f:
            data = f.read()
            print(data)
        return data
    except:
        return ''


# 保存文本信息到文件
def savetofile(txt, filename, encoding='utf-8'):
    pass
    try:
        with open(filename, 'w', encoding=encoding) as f:
            f.write(str(txt))
        return 1
    except:
        return 0


def getEnum(path='./output/'):
    fnout = os.path.join(path, 'process_result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    print(df)

    # Category
    A = df['Categories'].value_counts()
    print('[%s]列值分布情况:' % 'Categories')
    # ----- 要把映射后的值 保存下来下次用；-----
    print(A)
    dict_oldcol_a = {'Category': A.index, 'values': A.values}
    df_oldcol_a = pd.DataFrame(dict_oldcol_a)
    # print(df_oldcol_a)
    df_oldcol_a.to_csv('./Categories_count.csv')

    # Polarities
    B = df['Polarities'].value_counts()
    print('[%s]列值分布情况:' % 'Polarities')
    # ----- 要把映射后的值 保存下来下次用；-----
    print(B)
    dict_oldcol_b = {'Polarity': B.index, 'values': B.values}
    df_oldcol_b = pd.DataFrame(dict_oldcol_b)
    # print(df_oldcol_b)
    df_oldcol_b.to_csv('./Polarities_count.csv')


def getfilterCategory(path='./output/'):
    fnout = os.path.join(path, 'Result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    print(df)

    Categories = pd.read_csv('./Categories_count.csv',index_col=0)
    print(Categories)
    Categories = np.array(Categories).tolist()
    print(Categories)

    for Category in Categories:
        print(Category)
        Category = Category[0]
        df_new = df.loc[df['Categories'] == Category]
        # df_new1 = df.loc[(df['Categories'] == Category) & (df['AspectTerms'] == '_') & (df['OpinionTerms'] != '_')]
        df_new.to_csv('./xxxx-%s' % Category, sep=',', index=0)
        print(df_new)


def getfilterAspect(path='./output_zlr/'):
    fnout = os.path.join(path, 'Result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    print(df)

    Categories = pd.read_csv('./Categories_count.csv')['Category']
    print(Categories)

    for Category in Categories:
        print(Category)
        # df_new = df.loc[df['Categories'] == Category]
        df_new = df.loc[(df['Categories'] == Category) & (df['AspectTerms'] != '_')]
        df_new.to_csv('./Aspectxxxx-%s' % Category, sep=',', index=0)
        print(df_new)
        A = df_new['AspectTerms'].value_counts()
        print(A)


def getAspect(path='./output_zlr/'):
    fnout = os.path.join(path, 'Result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    print(df)


def get_aspect_count(path='./'): # 不行
    Categories = pd.read_csv('./Categories_count.csv',encoding='utf-8')['Category']
    print(Categories)

    for Category in Categories:
        print(Category)
        # Category.encode('utf-8')
        print(type(Category))
        path1 = 'Aspectxxxx-{}.csv'.format(Category)
        print(path1)
        fnout = os.path.join(path, path1)
        print(fnout)
        df = pd.read_csv('./Aspectxxxx-kouwei.csv')  # 有毒 找不到 中文英文都找不到
        print(df)
        a_counts = df['AspectTerms'].value_counts()
        print('[%s]列值分布情况:' % 'AspectTerms')
        # ----- 要把映射后的值 保存下来下次用；-----
        print(a_counts)
        dict_oldcol = {'Polarity': a_counts.index, 'values': a_counts.values}
        df_oldcol = pd.DataFrame(dict_oldcol)
        print(df_oldcol)
        df_oldcol.to_csv('./AspectTerms_count_%s.csv' % Category)
    return a_counts

# TODO 如何加上传入的产品名称
def getAll(product):  # 试试一次弄出来
    path = './output/'
    fnout = os.path.join(path, 'process_result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    print(df)

    Categories = pd.read_csv('./Categories_count.csv', encoding='utf-8',index_col=0)
    Categories = np.array(Categories).tolist()
    print(Categories)

    with open('./result.json', 'w+', encoding='utf-8') as f:
        f.write('[')
        category_count = 0
        for Category in Categories:
            if category_count > 0:
                f.write(',')
            f.write('{\"name\":\"%s\",'%Category[0])
            f.write('\"value\":%s,'%Category[1])
            f.write('\"product_name\":\"%s\",'%product)
            f.write('\"children\":[')
            df_new = df.loc[df['Categories'] == Category[0]]
            # print(df_new)
            # print(df)
            A_count = df_new['AspectTerms'].value_counts()
            Aspects = list(A_count.index)
            # print(A_count)
            # childrens1 = np.array(df_new).tolist()
            # print(childrens1)
            aspect_count = 0
            for aspect in Aspects:
                if aspect != '_':
                    if aspect_count > 0:
                        f.write(',')
                    # print(A_count[aspect])
                    f.write('{\"name\":\"%s\",'%aspect)
                    f.write('\"value\":[%s,0.5],'%A_count[aspect])
                    f.write('\"children\":[')
                    df_new2 = df.loc[(df['Categories'] == Category[0]) & (df['AspectTerms'] == aspect)]
                    # print(df)
                    childrens = np.array(df_new2).tolist()
                    # print(childrens)
                    children_count = 0
                    for children in childrens:
                        if children_count > 0:
                            f.write(',')
                        print(children[1])
                        f.write('{\"name\":\"%s\",'%children[1])
                        f.write('\"value\":[1,%s]}'%children[3])
                        children_count += 1
                    f.write(']}')
                    aspect_count += 1
                else:
                    df_new3 = df.loc[(df['Categories'] == Category[0]) & (df['AspectTerms'] == '_')]
                    opinions = np.array(df_new3).tolist()
                    print(opinions)
                    for opinion in opinions:
                        if aspect_count > 0:
                            f.write(',')
                        print(opinion[1])
                        f.write('{\"name\":\"%s\",'% opinion[1])
                        f.write('\"value\":[1,%s]}'% opinion[3])
                        aspect_count += 1
            f.write(']}')
            category_count += 1
        f.write(']')
        f.close()


def replace_polarities(path='./output/'):
    '''将正面负面变为1\-1，中性为0'''
    fnout = os.path.join(path, 'process_result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    # print(df)

    df.loc[df['Polarities'] == '正面','Polarities']= 1
    # print(df)
    df.loc[df['Polarities'] == '负面','Polarities']= -1
    df.loc[df['Polarities'] == '中性', 'Polarities'] = 0
    # print(df)
    df.to_csv('./output/process_result.csv')

def filter(path='./output/'):
    '''去除掉只有一个字的'''
    fnout = os.path.join(path, 'Result.csv')
    df = pd.read_csv(fnout, index_col=[0])
    # print(df)

    # df = df.drop(df[(df.AspectTerms == '[SEP]')].index)
    # print(df)
    # df = df.drop(df[(df.OpinionTerms == '[CLS]')].index)
    df = df.drop(df[(df.OpinionTerms == '_')].index)
    df = df.drop_duplicates()
    # df = df.drop(df[(df.Categories == '_')].index)
    stopwords = ['_','好','香','快','赞','咸','辣','高','足','少','低','油']
    # df = df[df['AspectTerms'].str.len]
    # 删除一个字的
    df.loc[df['AspectTerms'] == '_', 'AspectTerms'] = '__'
    for stopword in stopwords:
        df.loc[df['OpinionTerms'] == stopword, 'OpinionTerms'] = '_'+stopword
    count = df['AspectTerms'].str.len()
    count1 = df['OpinionTerms'].str.len()
    dropped = df[~(count==1) & ~(count1==1)].copy()
    # print(dropped)
    dropped = pd.DataFrame(dropped)
    dropped = dropped.reset_index(drop=True)
    # print(dropped)
    dropped.loc[dropped['AspectTerms'] == '__', 'AspectTerms'] = '_'
    for stopword in stopwords:
        dropped.loc[dropped['OpinionTerms'] == '_' + stopword, 'OpinionTerms'] = stopword
    # print(dropped)
    dropped.to_csv('./output/process_result.csv')

if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='数据最终处理')
    parser.add_argument('-product', type=str, default="泰奇",
                        help='选择预测产品，默认泰奇')
    args = parser.parse_args()
    product = args.product
    filter()
    getEnum()
    # replace_polarities()
    getAll(product)
