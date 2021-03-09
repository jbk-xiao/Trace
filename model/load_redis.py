import os
import redis
import csv
import time


# path = r"/Users/sskura/Downloads/competition2.0/competition2.0"
path = r"/data/st01/trace_data/competData"
r2j_fileList = []
compet_company_fileList = []
compet_product_fileList = []
product_list = []
pool = redis.ConnectionPool(host='localhost', port=6579, password='nopassword', max_connections=8, decode_responses=True)
r = redis.Redis(host='localhost', port=6579, password='nopassword', db=2, decode_responses=True)
r1 = redis.Redis(host='localhost', port=6579, password='nopassword', db=1, decode_responses=True)
r2 = redis.Redis(host='localhost', port=6579, password='nopassword', db=4, decode_responses=True)

def get_file_list(path):
    files = os.listdir(path)
    for file in files:
        if(os.path.isfile(path + '/' + file)):
            if(file == 'company-jd.csv'):
                r2j_fileList.append(path + '/' + file)
            if(file == 'compet.csv'):
                compet_company_fileList.append(path + '/' + file)
            if(file == 'compet_sku2.1.csv'):
                compet_product_fileList.append(path + '/' + file)
            if(file == 'product_list(2).csv'):
                product_list.append(path + '/' + file)


def get_r2jData(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        print(r1.ping())
        reader = csv.reader(csvfile)
        count = 0
        for row in reader:
            if(count >= 1):
                r1.set(row[0], row[1])
            count += 1
        print(count)


def get_competCompanyData(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        print(r.ping())
        reader = csv.reader(csvfile)
        count = 0
        for row in reader:
            if(count >= 1):
                r.sadd(row[1], row[0])
            count += 1
        print(count)


def get_competProductData(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        print(r.ping())
        reader = csv.reader(csvfile)
        count = 0
        for row in reader:
            if(count >= 1):
                r.zadd(row[1], {row[0]: row[2]})
            count += 1
        print(count)


def get_product_list(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        print(r2.ping())
        reader = csv.reader(csvfile)
        count = 0
        for row in reader:
            if(count >= 1):
                r2.hset(row[0],row[1],row[2])
            count += 1
        print(count)

if __name__ == "__main__":
    get_file_list(path)
    start = time.clock()
   # for file in r2j_fileList:
   #     get_r2jData(file)
   #     print(file, "successed")
   # for file in compet_company_fileList:
   #     get_competCompanyData(file)
   #     print(file, "successed")
    for file in compet_product_fileList:
        get_competProductData(file)
        print(file, "successed")
   # for file in product_list:
   #     get_product_list(file)
   #     print(file, "successed")
    print("Time used:", time.clock() - start)
