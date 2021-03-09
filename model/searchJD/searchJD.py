import time
from lxml import etree
from selenium import webdriver

from bs4 import BeautifulSoup
import urllib
import csv
import pandas as pd
import random

keywordList = []


#获取页面
def get_page(depth):
    # keyword = "麻薯"
    read_keyword('./product_key.csv')   #爬取的商品名称
    print(keywordList)
    headers = {
        # "User-Agent":"Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36"
        "User-Agent":
         #"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    }
    base_url = 'https://search.jd.com/Search?keyword={}&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&stock=1&page={}'
    detail_url = 'https://item.jd.com/{}.html'  #详情页
    # base_url2 = 'https://search.jd.com/search?keyword={}&stop=1&qrst=1&vt=2&stock=1&stock=1&ev=exbrand_{}'  #品牌加产品
    for keyword in keywordList:
        print(keyword[0])
        for page_num in range(1, depth):
            # while True:
            try:
                start_url = base_url.format(urllib.parse.quote(keyword[0]),
                                            page_num * 2 - 1)
                option = webdriver.ChromeOptions()
                option.add_argument('headless')  # 设置option
                driver = webdriver.Chrome(options=option)  # 调用带参数的谷歌浏览器
                driver.get(start_url)
                driver.execute_script(
                    "window.scrollTo(0,document.body.scrollHeight);"
                )  #执行下滑到底部的操作
                time.sleep(4)  #必须休眠，等待获取完全部信息
                #获取页面信息
                source = driver.page_source  #  等同于  response = requests.get(url = start_url,headers=headers)
                # goods = parse_page2(source,keyword[0])
                # writeProduct_csv(goods)
                html = etree.HTML(source)
                item = parse_page(html, keyword[0])
                writeProduct_csv(item)
                print('爬取' + keyword[0] + '第' + str(page_num) + '页商品时成功！')
                if (page_num == 1):
                    brand_item = get_brand(html, keyword[0])
                    writeBrand_csv(brand_item)
                    print('爬取' + keyword[0] + '品牌时成功')
                time.sleep(2)
                # break
                # time.sleep(2)
            except Exception as ex:
                print("出现异常%s"%ex)
                print('爬取' + keyword[0] + '第' + str(page_num) + '页时出错！')
                continue


#解析页面
def parse_page(html, keyword):
    li = html.xpath('//*[@id="J_goodsList"]/ul/li')
    for one_li in li:
        # yield {
        #     'product_name': keyword,
        #     'sku_id': one_li.xpath('@data-sku')[0],
        #     'price': one_li.xpath('div/div[2]/strong/i/text()')[0],
        #     'title': get_title(one_li),
        #     'comment_num': one_li.xpath('div/div[4]/strong/a/text()')[0],
        #     'shop': get_shop(one_li),
        #     'goods_url': 'http://' + one_li.xpath('div/div[1]/a/@href')[0],
        #     # 'img_url': 'http://' + one_li.xpath('div/div[1]/a/img')[0]
        # }
        product_name = keyword
        sku_id = one_li.xpath('@data-sku')[0]
        price = one_li.xpath('div/div[2]/strong/i/text()')[0]
        title = get_title(one_li)
        comment_num = one_li.xpath('div/div[4]/strong/a/text()')[0]
        shop = get_shop(one_li)
        goods_url = 'http:' + one_li.xpath('div/div[1]/a/@href')[0]
        try:
            img_url = 'http:' + one_li.xpath('div/div[1]/a/img/@src')[0]
        except:
            img_url = None
        product_one = [product_name,sku_id,price,title,comment_num,shop,goods_url,img_url]
        yield(product_one)


def parse_page2(html,keyword):
    soup = BeautifulSoup(html, 'lxml')
    # 商品列表
    goods_list = soup.find_all('li', class_='gl-item')
    # 打印goods_list到控制台
    for li in goods_list:  # 遍历父节点
        product_name = keyword
        # 商品编号
        no = li['data-sku']
        # 商品名称
        name = li.find(class_='p-name p-name-type-2').find('em').get_text()
        # 图片路径
        img_url = li.find(class_='p-img').find('img')['src']
        # 价格
        price = li.find(class_='p-price').find('i').get_text()
        # 评论量
        comment = li.find(class_='p-commit').find('a').get_text()
        # 商家
        shop = li.find(class_='p-shop').find('a').get_text()
        # 商品详情地址
        detail_addr = li.find(class_='p-name p-name-type-2').find('a')['href']
        productinfo = [product_name,no,name,img_url,price,shop,comment,detail_addr]
        yield(productinfo)

# 获取相关品牌
def get_brand(html, keyword):
    brand_li = html.xpath('//*[@class="sl-v-logos"]/ul/li')
    for brand in brand_li:
        # yield {
        #     'product_name': keyword,
        #     'brand_id': brand.xpath('@id')[0],
        #     'brand_name': brand.xpath('a/@title')[0],
        #     # 'brand_img': brand.xpath('./a/img/@src')[0]
        # }
        product_name = keyword
        brand_id = brand.xpath('@id')[0]
        brand_name = brand.xpath('a/@title')[0]
        try:
            brand_img = 'http:'+brand.xpath('./a/img/@src')[0]
        except:
            brand_img = None
        brand_one = [product_name,brand_id,brand_name,brand_img]
        yield(brand_one)


# 获取标题
def get_title(item):
    title_list = item.xpath('div/div[3]/a/em/text()')
    title = ' '.join(title_list)
    return title


#获取店铺名称
def get_shop(item):
    shop = item.xpath('div/div[5]/span/a/text()')
    if len(shop) == 0:
        return '未知'
    else:
        return shop[0]


#写入csv文件中
def writeProduct_csv(item):
    good_df = pd.DataFrame(item)
    # time = time.strftime('%Y-%m-%d',time.localtime(time.time()))
    good_df.to_csv('./JingDongFood.csv', mode='a', encoding='utf-8-sig')


def writeBrand_csv(item):
    good_df = pd.DataFrame(item)
    # time = time.strftime('%Y-%m-%d',time.localtime(time.time()))
    good_df.to_csv('./JingDongFoodBrand.csv',
                   mode='a',
                   encoding='utf-8-sig')


def read_keyword(keywordFile):
    with open(keywordFile, 'r', encoding="gbk") as csvfile:
        csv_reader = csv.reader(csvfile)
        count = 0
        for row in csv_reader:
            if (count >= 353):
                keywordList.append(row)
            count += 1
        print(count)

# 儿童奶、膨化、薯片、海苔、威化、烤馍片、辣条···一直到谷物

def main():
    get_page(10)   #指定爬取页数


if __name__ == "__main__":
    main()