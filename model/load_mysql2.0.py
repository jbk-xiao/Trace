import os
import pymysql
import csv
import time

path = r"/data/st01/trace_data/competData"
# path = r"/Users/sskura/Downloads/competition2.0/competition2.0/"
data_fileList = []
jd_info_fileList = []
comment_score_fileList = []
score_price_fileList = []
conn = pymysql.connect(host='localhost', port=3306,
                       user='root', password='toor',
                       database='trace', charset='utf8')
# conn = pymysql.connect(host='localhost',
#                        port=3306,
#                        user='root',
#                        password='201379',
#                        database='competition2.0',
#                        charset='utf8')


def get_file_list(path):
    files = os.listdir(path)
    for file in files:
        if (os.path.isfile(path + '/' + file)):
            if (file == 'company.csv'):
                data_fileList.append(path + '/' + file)
            if (file == 'JDcompet2.0.csv'):
                jd_info_fileList.append(path + '/' + file)
            if (file == 'comment_score2.1.csv'):
                comment_score_fileList.append(path + '/' + file)
            if (file == 'Senti_rating_new.csv'):
                score_price_fileList.append(path + '/' + file)


def get_company_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = [
            "regis_id", "proj_name", "img_url", "finance_round", "es_time",
            "region", "proj_desc", "company_name", "address", "lng", "lat",
            "regis_capital", "org_code", "phone_num"
        ]
        for row in reader:
            if (count >= 1):
                insert("company", field, row)
            count += 1
        print(count)


def get_jd_info_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = [
            "sku_id", "title", "price", "shop", "detail_url", "pname",
            "weight", "origin", "img_url", "brand", "brand_url",
            "commentCount", "goodRate"
        ]
        for row in reader:
            if (count >= 1):
                insert("jd_info", field, row)
            count += 1
        print(count)


def get_comment_score_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = [
            "sku_id", "brand", "package_score", "price_score",
            "logistics_score", "taste_score", "service_score"
        ]
        for row in reader:
            if (count >= 1):
                insert("comment_score", field, row)
            count += 1
        print(count)

def get_3d_score_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = [
            "brand","comment_score","count","sku_id","price"
        ]
        for row in reader:
            if (count >= 1):
                insert("3d_score", field, row)
            count += 1
        print(count)


def insert(tablename, field, ls):
    conn.ping()
    cursor = conn.cursor()
    sql = "insert ignore into {0} ({1}) values({2});"\
        .format(tablename,\
            ",".join(key for key in field),\
                ",".join('"' + value.replace("'", "\\").replace('"', '\\"') + '"' for value in ls))
    flag = True
    try:
        cursor.execute(sql)
        conn.commit()
    except Exception as e:
        print(sql)
        flag = False
    finally:
        cursor.close()
        conn.close()
        return flag


if __name__ == "__main__":
    get_file_list(path)
    start = time.clock()
    # for file in data_fileList:
    #    print(file)
    #    get_company_data(file)
    #    print(file, "successed")
    # for file in jd_info_fileList:
    #    print(file)
    #    get_jd_info_data(file)
    #    print(file, "successed")
    for file in comment_score_fileList:
        print(file)
        get_comment_score_data(file)
        print(file, "successed")
    for file in score_price_fileList:
        print(file)
        get_3d_score_data(file)
        print(file, "successed")
    print("Time used:", time.clock() - start)
