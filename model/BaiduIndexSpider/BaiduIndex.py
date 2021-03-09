import csv
import os
import time
import requests
import datetime
import pandas as pd
import json

keywords = ['泰奇八宝粥','银鹭','达利园','娃哈哈八宝粥','五芳斋','方家铺子','禾煜','八宝粥','老干妈','李锦记','海底捞','海天','李子柒','辣椒酱']
cookie = "BIDUPSID=F662D7A0F4D770FE460D55D6B8632ECD; PSTM=1533394749; BAIDUID=F662D7A0F4D770FE460D55D6B8632ECD:SL=0:NR=10:FG=1; BD_UPN=12314753; ispeed_lsm=0; __yjs_duid=1_0f1f3ed8881ff08ae8fbe80577cd5fa31611801871557; BDUSS=dZckxQbkNrTnVHfnhrZWNwLX4xSU40N1VLWFJqTjZYRENudEFKcTlnU3dNVTVnSVFBQUFBJCQAAAAAAAAAAAEAAABivXJDvv3EqtCm2LzSttDeAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALCkJmCwpCZgQW; BDUSS_BFESS=dZckxQbkNrTnVHfnhrZWNwLX4xSU40N1VLWFJqTjZYRENudEFKcTlnU3dNVTVnSVFBQUFBJCQAAAAAAAAAAAEAAABivXJDvv3EqtCm2LzSttDeAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAALCkJmCwpCZgQW; BAIDUID_BFESS=BD20073C06894B69B5D51CDDD6D161F4:FG=1; COOKIE_SESSION=246896_3_3_4_11_17_0_2_1_5_0_9_246902_0_12_0_1613711986_1612519810_1613711974%7C9%2312595763_125_1612519808%7C9; BD_HOME=1; H_PS_PSSID=33425_33442_33272_31253_33570_33461_33585_26350; delPer=0; BD_CK_SAM=1; PSINO=7; sug=3; sugstore=0; ORIGIN=0; bdime=0; BA_HECTOR=ak8h2g81aka50085ij1g340ce0q"
cookie1 = "PSTM=1609118784; BAIDUID=804C05C93FD9294D4C1FCA550BBD2B1B:FG=1; BIDUPSID=BCA2860C7EE75DA4ADC0C2D6C100D01A; BD_UPN=12314753; __yjs_duid=1_9e7a716e7219fe9ee515d915238b06091611756957961; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=7; shifen[7033236_91638]=1613889986; shifen[119054773096_42688]=1613889986; BCLID=10842930977297245707; BDSFRCVID=vbCOJeC62AAadCve-CZMboxjpOJbrz7TH6aoKPim561aOmqvab5mEG0Pex8g0KubDINlogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tRKOoILKfIt3fP36qRQj-ICShUFs-tjmB2Q-5KL-0ljHehOCbDcTWxuVhp7BbURZKjrE_MbdJJjoOUJYhj_-Dt_ghqQq54DtamTxoUJHMInJhhvG-xOzX4AebPRiJ-b9Qg-JKpQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0HPonHjDKj6jb3e; BCLID_BFESS=10842930977297245707; BDSFRCVID_BFESS=vbCOJeC62AAadCve-CZMboxjpOJbrz7TH6aoKPim561aOmqvab5mEG0Pex8g0KubDINlogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tRKOoILKfIt3fP36qRQj-ICShUFs-tjmB2Q-5KL-0ljHehOCbDcTWxuVhp7BbURZKjrE_MbdJJjoOUJYhj_-Dt_ghqQq54DtamTxoUJHMInJhhvG-xOzX4AebPRiJ-b9Qg-JKpQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0HPonHjDKj6jb3e; BAIDUID_BFESS=8AAB1AE8704DF58B155A179EEBEF2B4E:FG=1; BDRCVFR[C0p6oIjvx-c]=rJZwba6_rOCfAF9pywd; COOKIE_SESSION=707_0_5_3_3_5_0_0_5_3_1056_0_345_0_0_0_1613890159_0_1613890133%7C6%230_1_1613889983%7C1; H_PS_PSSID=33425_33515_33439_33258_33272_31253_33584_26350_33266; H_PS_645EC=a94epttD1C%2FEdDQf%2B2ptkknSmFFhq2plmEYf7QLCUGt12CnuMu8JcIw8J88; BD_HOME=1; BDUSS=h6YUkyY0xzOWJPSH5vSExQc1V0aHh0bFpGM1h6bHVRaTdFeHF0bFZKcTltRmxnSVFBQUFBJCQAAAAAAAAAAAEAAADWCqnnAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL0LMmC9CzJgNz; BDUSS_BFESS=h6YUkyY0xzOWJPSH5vSExQc1V0aHh0bFpGM1h6bHVRaTdFeHF0bFZKcTltRmxnSVFBQUFBJCQAAAAAAAAAAAEAAADWCqnnAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL0LMmC9CzJgNz; BA_HECTOR=00212125250hag8g481g342u50q"
cookie2 = "BIDUPSID=DD61C0512763DEA175E4AAE175ADAC9A; PSTM=1546849039; BDUSS=YwcnlwUGVjS2lNWnNxbXhDNkpQaUItWVJnd0t0VC1-S3pmWH5jMzhURXd-eUJjQVFBQUFBJCQAAAAAAAAAAAEAAABrViZMt8W~qsLp09HIw87SwLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADBy-VswcvlbT3; BD_UPN=12314753; BDUSS_BFESS=YwcnlwUGVjS2lNWnNxbXhDNkpQaUItWVJnd0t0VC1-S3pmWH5jMzhURXd-eUJjQVFBQUFBJCQAAAAAAAAAAAEAAABrViZMt8W~qsLp09HIw87SwLQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADBy-VswcvlbT3; BAIDUID=B5F9F68D13935D4CB4659F5BE60AD38D:FG=1; __yjs_duid=1_e61cdcfad9bd00e7eb19fef821e957fb1611552239114; BDSFRCVID_BFESS=qiIOJexroG3VpSbey6m-boLW_mKK0gOTDYLEOwXPsp3LGJLVN4vPEG0Pt_U-mEt-J8jwogKK0gOTH6KF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tbkD_C-MfIvDqTrP-trf5DCShUFs0tbRB2Q-XPoO3KO4qMOPbJu-jRDVhPvUy-riWbRM2MbgylRp8P3y0bb2DUA1y4vpWj3qLgTxoUJ2XMKVDq5mqfCWMR-ebPRiJPb9Qg-qahQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0hI0ljj82e5PVKgTa54cbb4o2WbCQJbQm8pcN2b5oQTtZKbbaKRFqJHQaWhR6Xb6vOIJTXpOUWfAkXpJvQnJjt2JxaqRCBDb-Vh5jDh3MBpQDhtoJexIO2jvy0hvctn3cShPCyUjrDRLbXU6BK5vPbNcZ0l8K3l02V-bIe-t2XjQhDNtDt60jfn3aQ5rtKRTffjrnhPF3MUbQXP6-hnjy3b7pQUOt54I-MxbPhUvRBPuUyN3MWh3RymJ42-39LPO2hpRjyxv4X60B0-oxJpOJXaILWl52HlFWj43vbURvD--g3-AqBM5dtjTO2bc_5KnlfMQ_bf--QfbQ0hOhqP-jBRIEoK0hJC-2bKvPKITD-tFO5eT22-ustGLJ2hcHMPoosIOKKhbDbJcBjRra2M7UXKjiaKJjBMbUoqRHXnJi0btQDPvxBf7pBJnqbp5TtUJM_UKzhfoMqfTbMlJyKMnitIv9-pPKWhQrh459XP68bTkA5bjZKxtq3mkjbPbDfn028DKuDTtajj3QeaRabK6aKC5bL6rJabC3qJTVXU6q2bDeQNbpbpJa56KqW66tahRR_n6oyT3JXp0vWtv4WbbvLT7johRTWqR48CbC0MonDh83Bn_L2xQJHmLOBt3O5hvvhb3O3MA-yUKmDloOW-TB5bbPLUQF5l8-sq0x0bOte-bQXH_E5bj2qRFD_C_y3e; ispeed_lsm=0; BDORZ=FFFB88E999055A3F8A630C64834BD6D0; delPer=0; BD_CK_SAM=1; PSINO=7; BAIDUID_BFESS=67A7B0A6551C14EDAB7DA71027AE1F85:FG=1; H_PS_645EC=10a2nhVNRYCqBIdHUsbgnb7Paw17PcpSQkjzAcgei2%2FtKEnR6wwCzHAw5legeKQtupLkBw; ab_sr=1.0.0_NmMzNTFjZTJkN2RkNjE3YTJkZTM5YmQyYmRjMzdhYWU4MDVkM2RjZGUzMjdmOTM1NmNiZjI1MTE2N2I1OTkzNDc0YTkzODZmNjEwMGM2OGRkMWRkMzUwNGMxMjNmNGQz; BD_HOME=1; H_PS_PSSID=33425_33582_33260_33344_33601_26350; BA_HECTOR=8h"
end_date = datetime.date.today()
start_date = end_date - datetime.timedelta(7)
#每次只能查询到十个月
dateDict = {'2015-01-01': '2015-10-31', '2015-11-01': '2016-08-31', '2016-09-01': '2017-06-30', '2017-07-01': '2018-04-30', '2018-05-01': '2019-02-28', '2019-03-01': '2019-12-31', '2020-01-01': '2020-05-31', '2020-06-01':'2021-01-31'}
testDict = {'2020-03-01': '2021-01-31'}


def get_atlas_index(keyword,atlas_date):
    index_df = baidu_atlas_index(word=keyword, date='2021-02-14', cookie=cookie1)  # 需求图谱
    print(index_df)
    if not os.path.exists('./data/baidu_atlas_index.csv'):
        index_df.to_csv('./data/baidu_atlas_index.csv', mode='a', index=0)
    else:
        index_df.to_csv('./data/baidu_atlas_index.csv', mode='a', index=0, header=0)


def get_age_index(keyword):
    index_df = baidu_age_index(word=keyword, cookie=cookie1)  # 年龄分布
    print(index_df)
    if not os.path.exists('./data/baidu_age_index.csv'):
        index_df.to_csv('./data/baidu_age_index.csv', mode='a', index=0)
    else:
        index_df.to_csv('./data/baidu_age_index.csv', mode='a', index=0, header=0)


def get_gender_index(keyword):
    index_df = baidu_gender_index(word=keyword, cookie=cookie1)  # 性别分布
    print(index_df)
    if not os.path.exists('./data/baidu_gender_index.csv'):
        index_df.to_csv('./data/baidu_gender_index.csv', mode='a', index=0)
    else:
        index_df.to_csv('./data/baidu_gender_index.csv', mode='a', index=0, header=0)


def get_search_index(keyword,start_date,end_date):
    index_df = baidu_search_index(word=keyword, start_date=start_date, end_date=end_date, cookie=cookie1)  # 百度搜索指数
    print(index_df)
    if not os.path.exists('./data/{}_searchIndex.csv'.format(keyword)):
        index_df.to_csv('./data/{}_searchIndex.csv'.format(keyword), mode='a')
    else:
        index_df.to_csv('./data/{}_searchIndex.csv'.format(keyword), mode='a', header=0)
    time.sleep(5)


def get_info_index(keyword,start_date,end_date):
    index_df4 = baidu_info_index(word=keyword, start_date=start_date, end_date=end_date, cookie=cookie2)  # 百度资讯指数
    print(index_df4)
    if not os.path.exists('./data/{}_infoIndex.csv'.format(keyword)):
        index_df4.to_csv('./data/{}_infoIndex.csv'.format(keyword), mode='a')
    else:
        index_df4.to_csv('./data/{}_infoIndex.csv'.format(keyword), mode='a', header=0)
    time.sleep(5)


def get_media_index(keyword,start_date,end_date):
    index_df5 = baidu_media_index(word=keyword, start_date=start_date, end_date=end_date,
                                     cookie=cookie1)  # 百度媒体指数
    print(index_df5)
    if not os.path.exists('./data/{}_mediaIndex.csv'.format(keyword)):
        index_df5.to_csv('./data/{}_mediaIndex.csv'.format(keyword), mode='a')
    else:
        index_df5.to_csv('./data/{}_mediaIndex.csv'.format(keyword), mode='a', header=0)
    time.sleep(5)


def baidu_info_index(word, start_date, end_date, cookie):
    # 百度资讯指数
    headers = {
        "Accept": "application/json, text/plain, */*",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
        "Connection": "keep-alive",
        "Cookie": cookie,
        "Host": "index.baidu.com",
        "Referer": "http://index.baidu.com/v2/main/index.html",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36"
    }
    w = '{"name":"%s","wordType":1}' % word

    url = 'http://index.baidu.com/api/FeedSearchApi/getFeedIndex?area=0&word=[[%s]]&startDate=%s&endDate=%s' % (
    w, start_date, end_date)

    r = requests.get(url=url, headers=headers)
    data = r.json()["data"]
    all_data = data["index"][0]["data"]
    uniqid = data["uniqid"]
    ptbk = get_ptbk(uniqid, cookie)
    result = decrypt(ptbk, all_data).split(",")
    result = [int(item) if item != "" else 0 for item in result]
    temp_df_7 = pd.DataFrame(
        [pd.date_range(start=start_date, end=end_date), result],
        index=["date", word],
    ).T
    temp_df_7.index = pd.to_datetime(temp_df_7["date"])
    del temp_df_7["date"]
    return temp_df_7


def decrypt(t: str, e: str) -> str:
    """
    解密函数
    :param t:
    :type t:
    :param e:
    :type e:
    :return:
    :rtype:
    """
    n, i, a, result = list(t), list(e), {}, []
    ln = int(len(n) / 2)
    start, end = n[ln:], n[:ln]
    a = dict(zip(end, start))
    return "".join([a[j] for j in e])


def get_ptbk(uniqid: str, cookie: str) -> str:
    headers = {
        "Accept": "application/json, text/plain, */*",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
        "Connection": "keep-alive",
        "Cookie": cookie,
        "Host": "index.baidu.com",
        "Referer": "http://index.baidu.com/v2/main/index.html",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36",
        "X-Requested-With": "XMLHttpRequest",
    }
    session = requests.Session()
    session.headers.update(headers)
    with session.get(
        url=f"http://index.baidu.com/Interface/ptbk?uniqid={uniqid}"
    ) as response:
        ptbk = response.json()["data"]
        return ptbk

def baidu_interest_index(word, cookie):
    """
    百度指数 人群画像兴趣分布
    :param word: 关键词
    :param cookie:
    :return:
        desc    兴趣分类
        tgi     TGI指数
        word_rate   关键词分布比率
        all_rate    全网分布比率
        period      周期范围
    """
    try:
        headers = {
            "Accept": "application/json, text/plain, */*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Cache-Control": "no-cache",
            "Cookie": cookie,
            "DNT": "1",
            "Host": "zhishu.baidu.com",
            "Pragma": "no-cache",
            "Proxy-Connection": "keep-alive",
            "Referer": "zhishu.baidu.com",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest",
        }
        url = "http://index.baidu.com/api/SocialApi/interest?wordlist[]=%s" % word
        r = requests.get(url=url, headers=headers)
        data = json.loads(r.text)['data']
        period = "%s|%s" % (data['startDate'], data['endDate'])

        age_list = data['result'][0]['interest']
        age_df = pd.DataFrame(age_list)

        all_list = data['result'][1]['interest']
        all_df = pd.DataFrame(all_list)
        all_df.drop(["tgi", "typeId"], axis=1, inplace=True)

        res_df = pd.merge(age_df, all_df, on='desc')
        res_df['period'] = period
        res_df.drop(["typeId"], axis=1, inplace=True)
        res_df.rename(columns={'rate_x': 'word_rate', 'rate_y': 'all_rate'}, inplace=True)
        return res_df
    except:
        return None


def baidu_gender_index(word, cookie):
    """
    百度指数 人群画像性别分布
    :param word: 关键词
    :param cookie:
    :return:
        desc    性别
        tgi     TGI指数
        word_rate   关键词分布比率
        all_rate    全网分布比率
        period      周期范围
    """
    try:
        headers = {
            "Accept": "application/json, text/plain, */*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Cache-Control": "no-cache",
            "Cookie": cookie,
            "DNT": "1",
            "Host": "zhishu.baidu.com",
            "Pragma": "no-cache",
            "Proxy-Connection": "keep-alive",
            "Referer": "zhishu.baidu.com",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest",
        }
        url = "http://index.baidu.com/api/SocialApi/baseAttributes?wordlist[]=%s" % word
        r = requests.get(url=url, headers=headers)
        data = json.loads(r.text)['data']
        period = "%s|%s" % (data['startDate'], data['endDate'])

        age_list = data['result'][0]['gender']
        age_df = pd.DataFrame(age_list)

        all_list = data['result'][1]['gender']
        all_df = pd.DataFrame(all_list)
        all_df.drop(["tgi", "typeId"], axis=1, inplace=True)

        res_df = pd.merge(age_df, all_df, on='desc')
        res_df['period'] = period
        res_df.drop(["typeId"], axis=1, inplace=True)
        res_df.rename(columns={'rate_x': 'word_rate', 'rate_y': 'all_rate'}, inplace=True)
        return res_df
    except:
        return None


def baidu_age_index(word, cookie):
    """
    百度指数 人群画像年龄分布
    :param word: 关键词
    :param cookie:
    :return:
        desc    年龄范围
        tgi     TGI指数
        word_rate   关键词分布比率
        all_rate    全网分布比率
        period      周期范围
    """
    try:
        headers = {
            "Accept": "application/json, text/plain, */*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Cache-Control": "no-cache",
            "Cookie": cookie,
            "DNT": "1",
            "Host": "zhishu.baidu.com",
            "Pragma": "no-cache",
            "Proxy-Connection": "keep-alive",
            "Referer": "zhishu.baidu.com",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest",
        }
        url = "http://index.baidu.com/api/SocialApi/baseAttributes?wordlist[]=%s" % word
        r = requests.get(url=url, headers=headers)
        data = json.loads(r.text)['data']
        period = "%s|%s" % (data['startDate'], data['endDate'])

        age_list = data['result'][0]['age']
        age_df = pd.DataFrame(age_list)

        all_list = data['result'][1]['age']
        all_df = pd.DataFrame(all_list)
        all_df.drop(["tgi", "typeId"], axis=1, inplace=True)

        res_df = pd.merge(age_df, all_df, on='desc')
        res_df['period'] = period
        res_df.drop(["typeId"], axis=1, inplace=True)
        res_df.rename(columns={'rate_x': 'word_rate', 'rate_y': 'all_rate'}, inplace=True)
        return res_df
    except:
        return None


def baidu_atlas_index(word, cookie, date=None):
    """
    百度指数 需求图谱
    :param word: 关键词
    :param cookie:
    :param date: 周期
    :return:
        period  周期范围
        word    相关词
        pv      搜索热度
        ratio   搜索变化率
    """

    try:
        headers = {
            "Accept": "application/json, text/plain, */*",
            "Accept-Encoding": "gzip, deflate",
            "Accept-Language": "zh-CN,zh;q=0.9",
            "Cache-Control": "no-cache",
            "Cookie": cookie,
            "DNT": "1",
            "Host": "zhishu.baidu.com",
            "Pragma": "no-cache",
            "Proxy-Connection": "keep-alive",
            "Referer": "zhishu.baidu.com",
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36",
            "X-Requested-With": "XMLHttpRequest",
        }
        if date == None:
            date = ""
        url = "http://index.baidu.com/api/WordGraph/multi?wordlist[]=%s&datelist=%s" % (word, date)
        r = requests.get(url=url, headers=headers)
        data = json.loads(r.text)['data']
        wordlist = data['wordlist'][0]['wordGraph']
        res_list = []
        for word in wordlist:
            tmp = {
                "word": word['word'],
                "pv": word['pv'],
                "ratio": word['ratio'],
                "period": data['period']
                # "sim": word['sim']
            }
            res_list.append(tmp)
        df = pd.DataFrame(res_list)
        return df
    except:
        return None


def baidu_search_index(word, start_date, end_date, cookie, type="all"):
    # 百度搜索数据
    headers = {
        "Accept": "application/json, text/plain, */*",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
        "Connection": "keep-alive",
        "Cookie": cookie,
        "Host": "index.baidu.com",
        "Referer": "http://index.baidu.com/v2/main/index.html",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36"
    }
    w = '{"name":"%s","wordType":1}' % word

    url = 'http://index.baidu.com/api/SearchApi/index?area=0&word=[[%s]]&startDate=%s&endDate=%s' % (w, start_date, end_date)

    r = requests.get(url=url, headers=headers)
    data = r.json()["data"]
    all_data = data["userIndexes"][0][type]["data"]
    uniqid = data["uniqid"]
    ptbk = get_ptbk(uniqid, cookie)
    result = decrypt(ptbk, all_data).split(",")
    result = [int(item) if item != "" else 0 for item in result]
    temp_df_7 = pd.DataFrame(
            [pd.date_range(start=start_date, end=end_date), result],
            index=["date", word],
        ).T
    temp_df_7.index = pd.to_datetime(temp_df_7["date"])
    del temp_df_7["date"]
    return temp_df_7


def baidu_media_index(word, start_date, end_date, cookie):
    # 百度媒体指数
    headers = {
        "Accept": "application/json, text/plain, */*",
        "Accept-Encoding": "gzip, deflate",
        "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
        "Connection": "keep-alive",
        "Cookie": cookie,
        "Host": "index.baidu.com",
        "Referer": "http://index.baidu.com/v2/main/index.html",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.90 Safari/537.36"
    }
    w = '{"name":"%s","wordType":1}' % word

    url = 'http://index.baidu.com/api/NewsApi/getNewsIndex?area=0&word=[[%s]]&startDate=%s&endDate=%s' % (
    w, start_date, end_date)

    r = requests.get(url=url, headers=headers)

    data = r.json()["data"]
    all_data = data["index"][0]["data"]
    uniqid = data["uniqid"]
    ptbk = get_ptbk(uniqid, cookie)
    result = decrypt(ptbk, all_data).split(",")
    result = [int(item) if item != "" else 0 for item in result]
    temp_df_7 = pd.DataFrame(
        [pd.date_range(start=start_date, end=end_date), result],
        index=["date", word],
    ).T
    temp_df_7.index = pd.to_datetime(temp_df_7["date"])
    del temp_df_7["date"]
    return temp_df_7


if __name__ == '__main__':
    # end_date = datetime.date.today()
    # start_date = end_date - datetime.timedelta(7)
    # start_date = start_date.strftime('%Y-%m-%d')
    # end_date = end_date.strftime('%Y-%m-%d')
    print(end_date, start_date)
    for keyword in keywords:
        get_atlas_index(keyword,end_date)
        get_gender_index(keyword)
        get_age_index(keyword)
        for start_date, end_date in dateDict.items():
            print(keyword)
            print(start_date,end_date)
            get_search_index(keyword,start_date,end_date)
            # get_info_index(keyword,start_date,end_date)
            # get_media_index(keyword,start_date,end_date)
