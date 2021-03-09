#爬取数据
import random

import demjson as demjson
import numpy as np
import pandas as pd
import requests
import json
import time
import csv

path1 = r'./jd_comments.csv'
path2 = r'./jd_generals.csv'
Comment_columns = ['userId', 'productId', 'productcolor', 'score', 'comment']
General_columns = [
    'skuId', 'averageScore', 'commentCount', 'goodCount', 'goodRate',
    'generalCount', 'generalRate', 'poorCount', 'poorRate'
]
idList = []
#评论页是从数据库调用的，可以直接从下面这个链接，返回json格式评论数据
url0 = u'https://club.jd.com/comment/productPageComments.action?callback=fetchJSON_comment98&productId={}&score=0&sortType=5&page={}&pageSize=10&isShadowSku=0&rid=0&fold=1'

header = []
header1 = {
    'User-Agent':
        'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36'
}
header.append(header1)
header2 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36'
}
header.append(header2)
header3 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36'
}
header.append(header3)
header4 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36'
}
header.append(header4)
header5 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36'
}
header.append(header5)
header6 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36(KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36'
}
header.append(header6)
header7 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4098.3 Safari/537.36'
}
header.append(header7)

header8 = {
    'User-Agent':
        'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.96 Safari/537.36 Edg/88.0.705.53'
}
header.append(header8)

header9 = {
    'User-Agent':
        'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:84.0) Gecko/20100101 Firefox/84.0'
}
header.append(header9)

#构建爬虫函数，这个爬取过程还是比较简单的。
def get_comments():
    # sku_id = '10450610461'
    read_product('./JingDongFood.csv')
    # #模拟浏览器访问

    for sku_id in idList:
        generals = getGeneral(sku_id)

        try:
            SaveGeneralCsv(generals)
            # comments = getComment(sku_id)
            # SaveCommentCsv(comments)
            print('爬取id' + sku_id + '成功！')
        except Exception as e:
            continue


def read_product(idFile):
    with open(idFile, 'r', encoding="gbk") as csvfile:
        csv_reader = csv.reader(csvfile)
        count = 0
        for row in csv_reader:
            if (count >= 3480):
                idList.append(row[2])
            count += 1
        print(count)


def getComment(sku_id):

    #json转换为字典格式，读取评论数据
    while True:
        # 模拟浏览器访问
        i = random.randint(0, 8)
        myheader = header[i]
        url = url0.format(sku_id, 1)
        print(sku_id)
        response = requests.get(url, headers=myheader)
        print(response.status_code)
        # 返回的json不是标准格式，把头/尾的字符去除
        json_response = response.text.replace('fetchJSON_comment98(',
                                              '').replace(');', '')
        try:
            json_response1 = json.loads(json_response)['comments']
            #提取出[用户id,用户名,购买时间，评价时间，商品Id，商品规格信息，用户评分，用户评论/追评]
            columns = ['id', 'referenceId', 'productColor', 'score', 'content']
        except Exception as e:
            print("出现异常%s" % e)
            time.sleep(5)
            continue
        finally:
            break
    # end_columns=['userId','productId','productColor','score','comment']
    #如下循环分别提取数据
    for j in range(10):
        try:
            userid = json_response1[j][columns[0]]
            productid = json_response1[j][columns[1]]
            productcolor = json_response1[j][columns[2]]
            score = json_response1[j][columns[3]]
            try:
                comment = json_response1[j][columns[4]]
            except:
                comment = ''

            comment_one = [sku_id, userid, productid, productcolor, score, comment]
            #生成器返回提取出的列表数据
            yield (comment_one)
        except Exception as ex:
            # time.sleep(5)
            print("出现异常%s"%ex)
            continue
    # time.sleep(1)
    print('爬取结束！')

def getGeneral(sku_id):

    while True:
        i = random.randint(0, 8)
        myheader = header[i]
        # print(myheader)
        url = url0.format(sku_id, 1)
        print(sku_id)
        response = requests.get(url, headers=myheader)
        # 返回的json不是标准格式，把头/尾的字符去除
        json_response = response.text.replace('fetchJSON_comment98(',
                                              '').replace(');', '')
        columns = [
            'skuId', 'averageScore', 'commentCount', 'goodCount', 'goodRate',
            'generalCount', 'generalRate', 'poorCount', 'poorRate'
        ]
        try:
            json_response2 = (json.loads(json_response))['productCommentSummary']
            skuId = json_response2[columns[0]]
            averageScore = json_response2[columns[1]]
            commentCount = json_response2[columns[2]]
            goodCount = json_response2[columns[3]]
            goodRate = json_response2[columns[4]]
            generalCount = json_response2[columns[5]]
            generalRate = json_response2[columns[6]]
            poorCount = json_response2[columns[7]]
            poorRate = json_response2[columns[8]]
            general_one = [
                skuId, averageScore, commentCount, goodCount, goodRate, generalCount,
                generalRate, poorCount, poorRate
            ]
            # print(general_one)
            yield (general_one)
            print('爬取' + sku_id + '基本信息成功')
            break
        except Exception as e:
            print("出现异常%s" % e)
            time.sleep(5)
            continue
    # json_response3 = json.loads(json_response)['hotCommentTagStatistics']

    # print(json_response3)
    # TagName = json_response3[0][columns[8]]



#存入csv文件
def  SaveCommentCsv(comments):
    comment = pd.DataFrame(comments)
    comment.to_csv(path1,
                   mode='a',
                   encoding='utf-8-sig')
    

def SaveGeneralCsv(generals):
    general = pd.DataFrame(generals)
    general.to_csv(path2,
                   mode='a',
                   encoding='utf-8-sig')
    # with open(path2, "a+", newline='', encoding='utf-8') as csvfile:
    #     writer = csv.writer(csvfile)
    #     #以读的方式打开csv 用csv.reader方式判断是否存在标题。
    #     with open(path2, "r", newline="") as f:
    #         reader = csv.reader(f)
    #         if not [row for row in reader]:
    #             writer.writerow(General_columns)
    #             writer.writerow(generals)
    #         else:
    #             writer.writerow(generals)


#运行爬虫函数，爬取评论数据并保存
def main():
    # comments = open(path1, 'w', newline='', encoding='utf-8')
    # w = csv.writer(comments)
    # w.writerow(Comment_columns)
    # generals = open(path2, 'w', newline='', encoding='utf-8')
    # w = csv.writer(generals)
    # w.writerow(General_columns)
    get_comments()

if __name__ == "__main__":
    main()