'''获取地域分布'''
import csv

from baidux.utils import test_cookies
from baidux import config
from baidux import BaiduIndex, ExtendedBaiduIndex
import datetime


cookies = """PSTM=1609118784; BAIDUID=804C05C93FD9294D4C1FCA550BBD2B1B:FG=1; BIDUPSID=BCA2860C7EE75DA4ADC0C2D6C100D01A; BD_UPN=12314753; __yjs_duid=1_9e7a716e7219fe9ee515d915238b06091611756957961; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; BD_CK_SAM=1; PSINO=7; shifen[7033236_91638]=1613889986; shifen[119054773096_42688]=1613889986; BCLID=10842930977297245707; BDSFRCVID=vbCOJeC62AAadCve-CZMboxjpOJbrz7TH6aoKPim561aOmqvab5mEG0Pex8g0KubDINlogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tRKOoILKfIt3fP36qRQj-ICShUFs-tjmB2Q-5KL-0ljHehOCbDcTWxuVhp7BbURZKjrE_MbdJJjoOUJYhj_-Dt_ghqQq54DtamTxoUJHMInJhhvG-xOzX4AebPRiJ-b9Qg-JKpQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0HPonHjDKj6jb3e; BCLID_BFESS=10842930977297245707; BDSFRCVID_BFESS=vbCOJeC62AAadCve-CZMboxjpOJbrz7TH6aoKPim561aOmqvab5mEG0Pex8g0KubDINlogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF_BFESS=tRKOoILKfIt3fP36qRQj-ICShUFs-tjmB2Q-5KL-0ljHehOCbDcTWxuVhp7BbURZKjrE_MbdJJjoOUJYhj_-Dt_ghqQq54DtamTxoUJHMInJhhvG-xOzX4AebPRiJ-b9Qg-JKpQ7tt5W8ncFbT7l5hKpbt-q0x-jLTnhVn0MBCK0HPonHjDKj6jb3e; BAIDUID_BFESS=8AAB1AE8704DF58B155A179EEBEF2B4E:FG=1; BDRCVFR[C0p6oIjvx-c]=rJZwba6_rOCfAF9pywd; COOKIE_SESSION=707_0_5_3_3_5_0_0_5_3_1056_0_345_0_0_0_1613890159_0_1613890133%7C6%230_1_1613889983%7C1; H_PS_PSSID=33425_33515_33439_33258_33272_31253_33584_26350_33266; H_PS_645EC=a94epttD1C%2FEdDQf%2B2ptkknSmFFhq2plmEYf7QLCUGt12CnuMu8JcIw8J88; BD_HOME=1; BDUSS=h6YUkyY0xzOWJPSH5vSExQc1V0aHh0bFpGM1h6bHVRaTdFeHF0bFZKcTltRmxnSVFBQUFBJCQAAAAAAAAAAAEAAADWCqnnAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL0LMmC9CzJgNz; BDUSS_BFESS=h6YUkyY0xzOWJPSH5vSExQc1V0aHh0bFpGM1h6bHVRaTdFeHF0bFZKcTltRmxnSVFBQUFBJCQAAAAAAAAAAAEAAADWCqnnAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAL0LMmC9CzJgNz; BA_HECTOR=00212125250hag8g481g342u50q"""


def getProvinceIndex(keywords,start_date,end_date):
    dict = config.PROVINCE_CODE
    print(dict)
    # print(config.CITY_CODE)

    # 获取所有省份的百度搜索指数
    for province, code in dict.items():
        all_index = 0
        print(province, code)
        baidu_index = BaiduIndex(
            keywords=keywords,
            start_date=start_date,
            end_date=end_date,
            cookies=cookies,
            area=code
        )
        # for index in baidu_index.get_index():
        #     print(index)
        with open('./data/provinceIndex.csv', 'a', encoding='utf-8') as f:
            w = csv.writer(f)
            for index in baidu_index.get_index():
                print(index)
                if index['type'] == 'all':
                    all_index = int(index['index']) + all_index
                    print(all_index)
                index['province'] = province
                w.writerow(index.values())
        keyword = keywords[0][0].split('\'')[0]
        date = start_date + '|' + end_date
        weekIndex = str(all_index)
        print(all_index)
        province = province
        week_index = [keyword, date, weekIndex, province]
        out = open('./data/provinceWeekIndex.csv', 'a', newline='', encoding='utf-8')
        csv_write = csv.writer(out, dialect='excel')
        csv_write.writerow(week_index)


def get_media_index(keywords,start_date,end_date):
    # 获取百度媒体指数
    news_index = ExtendedBaiduIndex(
        keywords=keywords,
        start_date=start_date,
        end_date=end_date,
        cookies=cookies,
        kind='news'
    )
    # out = open('./data/MediaIndex.csv', 'a', newline='', encoding='utf-8')
    # csv_write = csv.writer(out, dialect='excel')
    for index in news_index.get_index():
        print(index)
        # csv_write.writerow(index)


def get_info_index(keywords,start_date,end_date):
    # 获取百度咨询指数
    feed_index = ExtendedBaiduIndex(
        keywords=keywords,
        start_date=start_date,
        end_date=end_date,
        cookies=cookies,
        kind='feed'
    )
    for index in feed_index.get_index():
        print(index)


# Press the green button in the gutter to run the script.
if __name__ == '__main__':

    # 测试cookies是否配置正确
    # True为配置成功，False为配置不成功
    print(test_cookies(cookies))

    keywordsList = [[['泰奇八宝粥']],[['银鹭']],[['达利园']],[['燕之坊']],[['娃哈哈八宝粥']],[['五芳斋']],[['方家铺子']],[['禾煜']],[['八宝粥']],[['老干妈']],[['李锦记']],[['海底捞']],[['海天']],[['李子柒']],[['辣椒酱']]]

    # 获取城市代码, 将代码传入area可以获取不同城市的指数, 不传则为全国
    # 媒体指数不能分地区获取
    print(config.PROVINCE_CODE)
    # 动态获取当前一个星期的日期
    end_date = datetime.date.today()
    start_date = end_date - datetime.timedelta(7)
    start_date = start_date.strftime('%Y-%m-%d')
    end_date = end_date.strftime('%Y-%m-%d')
    print(end_date, start_date)

    for keywords in keywordsList:
        getProvinceIndex(keywords,start_date,end_date)
        # get_media_index(keywords,start_date,end_date)




# See PyCharm help at https://www.jetbrains.com/help/pycharm/
