# Trace-BackEnd

## 竞品模块

2020.1.25

数据库存储：

mysql

##### 表1：company

包含所有公司/项目的基本信息，包括

| 字段名        | 含义         |
| ------------- | ------------ |
| regis_id      | 工商注册号   |
| proj_name     | 项目名称     |
| img_url       | logo图片     |
| finance_round | 最新融资轮次 |
| es_time       | 成立时间     |
| region        | 所属地       |
| proj_desc     | 项目简介     |
| company_name  | 所属企业     |
| address       | 地址         |
| lng           | 经度         |
| lat           | 纬度         |
| regis_capital | 注册资本     |
| org_code      | 组织机构代码 |
| phone_num     | 联系电话     |

因为目前主项目和竞品项目爬取的内容全部一样，因此放于一个表格中；同时由于一次返回基本上返回所有信息，因此未进行进一步分表。

建表语句：

```sql
	CREATE TABLE company(
   regis_id VARCHAR(20) NOT NULL,
   proj_name VARCHAR(50) NOT NULL,
	 img_url VARCHAR(100),
	 finance_round VARCHAR(10),
   es_time VARCHAR(20),
	 region VARCHAR(20),
	 proj_desc text,
	 company_name VARCHAR(100),
	 address text,
	 lng VARCHAR(20) NULL,
	 lat VARCHAR(20) NULL,
	 regis_capital VARCHAR(20),
	 org_code VARCHAR(30),
	 phone_num VARCHAR(20),
   PRIMARY KEY ( regis_id )
  )ENGINE=InnoDB CHARSET=utf8;
```

导入数据库部分代码：

```python
def get_company_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = ["regis_id", "proj_name", "img_url", "finance_round", "es_time", "region", "proj_desc", "company_name", "address", "lng", "lat", "regis_capital", "org_code", "phone_num"]
        for row in reader:
            if(count >= 1):
                insert("company", field, row)
            count += 1
        print(count)
```



##### 表2：compet

包含竞品项目和主项目的对应关系

| 字段名          | 含义               |
| --------------- | ------------------ |
| compet_regis_id | 竞品项目工商注册号 |
| regis_id        | 主项目工商注册号   |

因为是一对多的关系

建表语句：

```sql
	CREATE TABLE compet(
   compet_regis_id VARCHAR(20) NOT NULL,
	 regis_id VARCHAR(20) NOT NULL,
	 PRIMARY KEY ( compet_regis_id )
	 )ENGINE=InnoDB CHARSET=utf8;
```

导入数据库部分代码

```python
def get_compet_data(file):
    with open(file, 'r', encoding="utf8") as csvfile:
        reader = csv.reader(csvfile)
        count = 0
        field = ["compet_regis_id", "regis_id"]
        for row in reader:
            if(count >= 1):
                insert("compet", field, row)
            count += 1
        print(count)
```



已爬取内容：

**京东主项目商品和竞品详情页面：**（每个商品都不一样）

商品名称、价格、（商品品类 有点多感觉）、商品详情（品牌、产地、净含量、包装等等等等）、商品图片、品牌、品牌链接

title、price、details、img_url、brand、brand_url

**京东主项目商品类别的（前十五个？）竞品信息**：

标题、店铺、评论数、价格、sku、详情链接

**京东主项目商品和竞品评论内容**：

sku、好评中评差评数量和百分比、评论内容



##### 表3：jd_info

存储所爬取到的京东商品基本信息，包括主商品和相应的竞品商品

建表语句

```sql
	CREATE TABLE jd_info(sku_id VARCHAR(20) NOT NULL,title text,price DOUBLE,shop VARCHAR(50),detail_url text,pname VARCHAR(100),weight VARCHAR(20),origin VARCHAR(20),img_url text,brand VARCHAR(20),brand_url text,commentCount int,goodRate DOUBLE,PRIMARY KEY ( sku_id ))ENGINE=InnoDB CHARSET=utf8;
```



##### 表4：compet_sku

存储竞品的compet_sku_id与对应主商品的sku_id的对应关系

建表语句

```sql
CREATE TABLE compet_sku(compet_sku_id VARCHAR(20) NOT NULL,sku_id VARCHAR(20) NOT NULL,PRIMARY KEY ( compet_sku_id ))ENGINE=InnoDB CHARSET=utf8;
```



##### 表5：company_jd

存储主公司工商注册号regis_id和主商品

```sql
CREATE TABLE company_jd(regis_id VARCHAR(20) NOT NULL,sku_id VARCHAR(20) NOT NULL,PRIMARY KEY ( regis_id ))ENGINE=InnoDB CHARSET=utf8;
```



##### 查询语句（传入regis_id)

```sql
--查询所有京东竞品
select pname,price,detail_url,img_url,brand,brand_url,commentCount,goodRate from jd_info j,company_jd c,compet_sku c2 where j.sku_id = c2.compet_sku_id and c2.sku_id = c.sku_id and c.regis_id = "440108400003939";

--查询京东主商品信息
select pname,price,detail_url,img_url,brand,brand_url,commentCount,goodRate from jd_info j,company_jd c where j.sku_id = c.sku_id and c.regis_id = "440108400003939";

--查询竞品公司信息
select proj_name,img_url,es_time,region,company_name,proj_desc,address,lng,lat,regis_capital,org_code,phone_num,company.regis_id from company,compet where company.regis_id = compet.compet_regis_id and compet.regis_id="440108400003939";

--查询主公司信息
SELECT proj_name,img_url,es_time,region,company_name,proj_desc,address,lng,lat,phone_num FROM company WHERE regis_id = "440108400003939";
```





##### JD商品返回信息

| 字段名       | 含义             |
| ------------ | ---------------- |
| pname        | 商品名称         |
| price        | 价格             |
| detail_url   | 详情页面url      |
| img_url      | 图片地址         |
| brand        | 品牌             |
| brand_url    | 品牌对应搜索页面 |
| commentCount | 评论数           |
| goodRate     | 好评率           |



##### 竞品模块返回示例：

传入regis_id返回以下json

```json
{
    "companyInfo": {
        "proj_name": "老干妈",
        "img_url": "https://zhengxin-pub.cdn.bcebos.com/brandpic/dad5c7ad216fc82776763b404b4ae491_fullsize.jpg",
        "es_time": "1997/10/5",
        "region": "贵阳",
        "company_name": "贵阳南明老干妈风味食品有限责任公司",
        "proj_desc": "老干妈（陶华碧）牌油制辣椒是贵州地区传统风味食品之一。老干妈是国内生产及销售量最大的辣椒制品生产企业，主要生产风味豆豉、风味鸡油辣椒、香辣菜、风味腐乳等20余个系列产品。",
        "address": "贵州省贵阳市南明区龙洞堡见龙路138-15号",
        "lng": "106.786346",
        "lat": "26.539301",
        "phone_num": "0851-85406886"
    },
    "compet_geoList": [
        {
            "proj_name": "小酱仙",
            "company_name": "北京九门坊商贸股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d12d_fullsize.jpg",
            "region": "北京",
            "lng": "116.4908849",
            "lat": "39.92323986"
        },
        {
            "proj_name": "鲜8食品",
            "company_name": "北京八日鲜食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d16d_fullsize.jpg",
            "region": "北京",
            "lng": "116.3529198",
            "lat": "40.03605554"
        },
        {
            "proj_name": "媛家酱",
            "company_name": "北京媛家科技发展有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d14d_fullsize.jpg",
            "region": "北京",
            "lng": "116.278586",
            "lat": "40.13767907"
        },
        {
            "proj_name": "川崎食品",
            "company_name": "上海川崎食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d22d_fullsize.jpg",
            "region": "上海",
            "lng": "121.6176672",
            "lat": "31.33780877"
        },
        {
            "proj_name": "美鑫食品",
            "company_name": "江苏美鑫食品科技有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d11d_fullsize.jpg",
            "region": "徐州",
            "lng": "117.2830176",
            "lat": "34.2999927"
        },
        {
            "proj_name": "福鑫食品",
            "company_name": "福鑫食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d19d_fullsize.jpg",
            "region": "福建",
            "lng": "120.4463718",
            "lat": "30.46960298"
        },
        {
            "proj_name": "老恒和",
            "company_name": "湖州老恒和酿造有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d27d_fullsize.jpg",
            "region": "湖州",
            "lng": "120.165284",
            "lat": "30.84716287"
        },
        {
            "proj_name": "咸亨股份",
            "company_name": "绍兴咸亨食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d25d_fullsize.jpg",
            "region": "绍兴",
            "lng": "120.6047735",
            "lat": "30.02942402"
        },
        {
            "proj_name": "安记食品",
            "company_name": "安记食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d24d_fullsize.jpg",
            "region": "福建",
            "lng": "118.56679",
            "lat": "24.87740594"
        },
        {
            "proj_name": "新青大",
            "company_name": "青岛新青大食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d17d_fullsize.jpg",
            "region": "青岛",
            "lng": "120.391189",
            "lat": "36.12502688"
        },
        {
            "proj_name": "二和嫁嫁",
            "company_name": "武汉明利和丰食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d15d_fullsize.jpg",
            "region": "武汉",
            "lng": "114.3076592",
            "lat": "30.41501682"
        },
        {
            "proj_name": "长剑牌",
            "company_name": "深圳市聚味食品科技有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d10d_fullsize.jpg",
            "region": "深圳",
            "lng": "114.0680991",
            "lat": "22.62821243"
        },
        {
            "proj_name": "海天味业",
            "company_name": "佛山市海天调味食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d21d_fullsize.jpg",
            "region": "广东",
            "lng": "113.128595",
            "lat": "23.05310401"
        },
        {
            "proj_name": "嘉豪食品",
            "company_name": "广东嘉豪食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d20d_fullsize.jpg",
            "region": "广东",
            "lng": "113.3619163",
            "lat": "22.59199782"
        },
        {
            "proj_name": "千禾味业",
            "company_name": "千禾味业食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d23d_fullsize.jpg",
            "region": "四川",
            "lng": "103.8385248",
            "lat": "30.04827213"
        },
        {
            "proj_name": "味之浓",
            "company_name": "四川味之浓食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d18d_fullsize.jpg",
            "region": "四川",
            "lng": "103.8599541",
            "lat": "30.03260251"
        },
        {
            "proj_name": "太阳食品",
            "company_name": "西安太阳食品集团公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d26d_fullsize.jpg",
            "region": "西安",
            "lng": "109.003131",
            "lat": "34.2372613"
        },
        {
            "proj_name": "广海记",
            "company_name": "新疆广海记农业科技发展有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d13d_fullsize.jpg",
            "region": "新疆",
            "lng": "83.64570001",
            "lat": "46.52939588"
        }
    ],
    "jdetail": {
        "pname": "陶华碧老干妈油辣椒",
        "price": "9.9",
        "detail_url": "http://item.jd.com/844099.html",
        "img_url": "https://img14.360buyimg.com/n5/jfs/t1/120265/6/15663/58734/5f8fa697Ef4f51524/9650bf9c0cb1cfb9.jpg",
        "brand": "陶华碧老干妈",
        "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_16737",
        "commentCount": 2127107,
        "goodRate": 0.99
    },
    "Compet_jdetails": [
        {
            "pname": "川南油辣子280g",
            "price": "13.9",
            "detail_url": "http://item.jd.com/100000428823.html",
            "img_url": "https://img13.360buyimg.com/n5/jfs/t1/112636/32/743/243311/5e8fe46aE45776f1a/7c2f89ec422d2685.jpg",
            "brand": "川南",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_21421",
            "commentCount": 103230,
            "goodRate": 0.99
        },
        {
            "pname": "海底捞海底捞好好吃饭精品牛肉佐餐酱200g",
            "price": "18.9",
            "detail_url": "http://item.jd.com/100002398567.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/127025/26/10782/178810/5f45d080E3de7bdcc/2d00289bd4067fd6.jpg",
            "brand": "海底捞",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_7811",
            "commentCount": 1199782,
            "goodRate": 0.99
        },
        {
            "pname": "川娃子烧椒酱",
            "price": "12.8",
            "detail_url": "http://item.jd.com/100005264233.html",
            "img_url": "https://img13.360buyimg.com/n5/jfs/t1/138478/34/18248/120129/5fd48839Ea31e647b/348faed7d6bb1927.jpg",
            "brand": "川娃子",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_277836",
            "commentCount": 246186,
            "goodRate": 0.98
        },
        {
            "pname": "老坛子鲜椒酱",
            "price": "22.8",
            "detail_url": "http://item.jd.com/100006982973.html",
            "img_url": "https://img13.360buyimg.com/n5/jfs/t1/128749/19/19692/179457/5fbcc418E8ae255d6/46c537ad3e6fcffd.jpg",
            "brand": "老坛子",
            "brand_url": "https://list.jd.com/list.html?cat=12218,13553,13578&ev=exbrand_470200",
            "commentCount": 15646,
            "goodRate": 0.98
        },
        {
            "pname": "清净园辣椒酱",
            "price": "18.9",
            "detail_url": "http://item.jd.com/100007770057.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/119715/3/10120/145451/5eff13e8Ee44efcfe/62c6565c6df33f64.jpg",
            "brand": "清净园",
            "brand_url": "https://list.jd.com/list.html?cat=1320,5019,5024&tid=17660&ev=exbrand_14525",
            "commentCount": 128798,
            "goodRate": 0.98
        },
        {
            "pname": "聪厨蒜蓉辣酱180g",
            "price": "14.9",
            "detail_url": "http://item.jd.com/100008050179.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/144033/18/4435/130740/5f278affE92b374b5/c12ad4e698a7658b.jpg",
            "brand": "聪厨",
            "brand_url": "https://list.jd.com/list.html?cat=12218,21455,21457&ev=exbrand_246393",
            "commentCount": 58546,
            "goodRate": 0.98
        },
        {
            "pname": "渡江宴烧椒酱200g*2瓶+彩椒酱200g*2瓶",
            "price": "39.9",
            "detail_url": "http://item.jd.com/100009224435.html",
            "img_url": "https://img10.360buyimg.com/n5/jfs/t1/140174/26/11954/205830/5f93c6faE6e79f24c/39101d3c1487d4fb.jpg",
            "brand": "渡江宴",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_230226",
            "commentCount": 31563,
            "goodRate": 0.98
        },
        {
            "pname": "川珍双椒酱",
            "price": "11.8",
            "detail_url": "http://item.jd.com/100010637500.html",
            "img_url": "https://img10.360buyimg.com/n5/jfs/t1/132004/1/5544/161810/5f2109b5E48b8ad6e/e83ce5961c5b854a.jpg",
            "brand": "川珍",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_5534",
            "commentCount": 46350,
            "goodRate": 0.98
        },
        {
            "pname": "饭扫光剁椒酱200g",
            "price": "12.6",
            "detail_url": "http://item.jd.com/100015029096.html",
            "img_url": "https://img11.360buyimg.com/n5/jfs/t1/114077/23/16258/141141/5f471dfeE06658c39/5eb435ff56a86281.jpg",
            "brand": "饭扫光",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_6655",
            "commentCount": 66492,
            "goodRate": 0.98
        },
        {
            "pname": "大嘴先生双椒酱230g",
            "price": "13.8",
            "detail_url": "http://item.jd.com/100015687534.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/148847/40/9185/174864/5f6c7401Edf331c24/5e357c156129afd8.jpg",
            "brand": "大嘴先生",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_525012",
            "commentCount": 6212,
            "goodRate": 0.97
        },
        {
            "pname": "酱先生鲜椒酱",
            "price": "14.9",
            "detail_url": "http://item.jd.com/100015856592.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/144349/5/14222/228782/5facddd4E16d36a65/a9fea223937cd734.jpg",
            "brand": "酱先生",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_426958",
            "commentCount": 2282,
            "goodRate": 0.98
        },
        {
            "pname": "华源良品广西八角大料香料调料大全桂皮香叶干辣椒茴香花椒厨房香辛料煲汤炖肉大红八角全干货 八角大料 250克",
            "price": "31.2",
            "detail_url": "http://item.jd.com/10020176668539.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/144251/8/7261/408739/5f4dfff4E0b4e1279/c2f6c7332d6c1bea.jpg",
            "brand": "华源良品",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21384&ev=exbrand_593869",
            "commentCount": 1147,
            "goodRate": 0.95
        },
        {
            "pname": "李子柒 贵州糟辣酱 辣椒酱贵州风味特产酸辣鲜香 调味酱 230g*3瓶",
            "price": "34.9",
            "detail_url": "http://item.jd.com/10024090168114.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/171295/38/2626/388970/5fff9d85E28d6451d/b1593611077d8dd1.jpg",
            "brand": "李子柒",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_387375",
            "commentCount": 4217,
            "goodRate": 0.98
        },
        {
            "pname": "李锦记蒜蓉辣酱226g",
            "price": "16.9",
            "detail_url": "http://item.jd.com/1078121.html",
            "img_url": "https://img11.360buyimg.com/n5/jfs/t1/163081/19/3983/181325/600fad6eE1afe6993/c0757fad176ac306.jpg",
            "brand": "李锦记",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_11330",
            "commentCount": 359429,
            "goodRate": 0.99
        },
        {
            "pname": "六婆辣椒油 油泼辣子 四川特色油辣子凉拌红油 熟油辣子230g",
            "price": "14.8",
            "detail_url": "http://item.jd.com/12738293049.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/167145/28/1693/161406/5ff9506bEd3defd32/5c77e5b6fba4cd05.jpg",
            "brand": "六婆",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21382&ev=exbrand_47655",
            "commentCount": 21262,
            "goodRate": 0.99
        },
        {
            "pname": "海天招牌拌饭酱 300g",
            "price": "12.9",
            "detail_url": "http://item.jd.com/1338357.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/84351/29/14025/181954/5db1153bEc5ef8d6b/2d404cb8213120cd.jpg",
            "brand": "海天",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_7876",
            "commentCount": 732884,
            "goodRate": 0.99
        },
        {
            "pname": "亨氏亨氏番茄辣椒酱",
            "price": "9.9",
            "detail_url": "http://item.jd.com/2169939.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t2416/26/1479225670/79008/b96177d7/56654713Nf7dc5e84.jpg",
            "brand": "亨氏（Heinz）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_8241",
            "commentCount": 33443,
            "goodRate": 0.99
        },
        {
            "pname": "湖南超特辣剁辣椒 地道湘2.3kg精制朝天椒 4.6斤大瓶装蒜蓉剁椒酱辣椒酱 蒸菜炒菜拌饭酱调料",
            "price": "29.8",
            "detail_url": "http://item.jd.com/30750526915.html",
            "img_url": "https://img10.360buyimg.com/n5/jfs/t1/40450/6/3205/246233/5cc3f794E1d6c6327/e13d69ef17cca1c6.jpg",
            "brand": "地道湘",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21389&ev=exbrand_355572",
            "commentCount": 8552,
            "goodRate": 0.97
        },
        {
            "pname": "韩今辣酱 500g",
            "price": "32.9",
            "detail_url": "http://item.jd.com/3681877.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/160852/7/3804/120493/600a4e08E32cd68a0/29d8d78ad2d94aa4.jpg",
            "brand": "韩今",
            "brand_url": "https://list.jd.com/list.html?cat=1320,5019,5024&tid=17660&ev=exbrand_497056",
            "commentCount": 257086,
            "goodRate": 0.99
        },
        {
            "pname": "厨邦厨邦蒜蓉辣椒酱210g",
            "price": "5.9",
            "detail_url": "http://item.jd.com/3763759.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/6981/33/9015/109899/5c1320feEe1629799/ee027320a8174f1a.jpg",
            "brand": "厨邦",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_21703",
            "commentCount": 442845,
            "goodRate": 0.99
        },
        {
            "pname": "鹃城牌鹃城牌 郫县豆瓣酱500g",
            "price": "10.9",
            "detail_url": "http://item.jd.com/3798578.html",
            "img_url": "https://img13.360buyimg.com/n5/jfs/t19588/40/2649718355/240859/9b5fb073/5b068789N9f77fb92.jpg",
            "brand": "鹃城牌（juanchengpai）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22173&ev=exbrand_137101",
            "commentCount": 682865,
            "goodRate": 0.99
        },
        {
            "pname": "百利 甜辣酱 1kg 泰式甜辣酱  泰式甜辣酱 辣椒酱 手抓饼炸薯条调味品",
            "price": "18.9",
            "detail_url": "http://item.jd.com/41460886283.html",
            "img_url": "https://img13.360buyimg.com/n5/jfs/t1/100152/21/9460/168392/5e0edfc7E707b8a97/edd997efec3f5bcc.jpg",
            "brand": "百利",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21385&ev=exbrand_241871",
            "commentCount": 497,
            "goodRate": 1.0
        },
        {
            "pname": "仲景调味油麻油 低温冷萃非油炸 凉拌热炒烧烤火锅米线凉拌菜125ml 花椒油+辣椒油",
            "price": "29.6",
            "detail_url": "http://item.jd.com/42269144142.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/131204/20/16777/117754/5fbb8390E2118cd7e/636e8f972ee71d11.jpg",
            "brand": "仲景",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21385&ev=exbrand_21030",
            "commentCount": 6395,
            "goodRate": 0.99
        },
        {
            "pname": "虎邦鲁西牛肉 鲜椒酱 210g",
            "price": "18.8",
            "detail_url": "http://item.jd.com/4410290.html",
            "img_url": "https://img10.360buyimg.com/n5/jfs/t1/138078/23/20214/196826/5fe69ddfE6a0f4acd/ec4d43beaeddaaeb.jpg",
            "brand": "虎邦",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_313861",
            "commentCount": 239716,
            "goodRate": 0.99
        },
        {
            "pname": "吉香居暴下饭川香味250g*1瓶",
            "price": "8.8",
            "detail_url": "http://item.jd.com/4665497.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/147320/38/16243/196908/5fc5ba39Ef98f5927/2a318856fc13374a.jpg",
            "brand": "吉香居（JI XIANG JU）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_41569",
            "commentCount": 684614,
            "goodRate": 0.99
        },
        {
            "pname": "御厨香特辣王下饭江西湖南特辣辣椒酱超辣小米魔鬼辣香辣酱蒜蓉酱 3瓶",
            "price": "35.0",
            "detail_url": "http://item.jd.com/51771110899.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t1/36851/19/14196/157279/5d200fc9Ebc5aca09/396bdd6049098e47.jpg",
            "brand": "御厨香（YUCHUXIANG）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_323502",
            "commentCount": 6373,
            "goodRate": 0.96
        },
        {
            "pname": "【三瓶装】人高一等 香辣麻辣对虾酱200gX3瓶  下饭拌菜凉拌炒菜海鲜酱肉酱辣椒酱 厨房调味品 简装虾酱香辣味x3",
            "price": "39.9",
            "detail_url": "http://item.jd.com/54222597156.html",
            "img_url": "https://img11.360buyimg.com/n5/jfs/t1/112649/25/11113/141327/5efb0258E3626ad46/dd86b4e3652f4e44.jpg",
            "brand": "人高一等",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_314634",
            "commentCount": 970,
            "goodRate": 0.95
        },
        {
            "pname": "春光香辣 灯笼辣椒酱",
            "price": "5.5",
            "detail_url": "http://item.jd.com/5676502.html",
            "img_url": "https://img12.360buyimg.com/n5/jfs/t1/168046/5/3152/153166/60050321E524e17b5/0679bc09e46b50ce.jpg",
            "brand": "春光",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_43453",
            "commentCount": 178660,
            "goodRate": 0.99
        },
        {
            "pname": "特瑞肯特瑞肯烧烤全家福组合321g/盒",
            "price": "19.9",
            "detail_url": "http://item.jd.com/5964486.html",
            "img_url": "https://img11.360buyimg.com/n5/jfs/t1/166989/5/4163/176096/6010d5f6E36bb29f6/1b2169865dc35cfe.jpg",
            "brand": "特瑞肯（TRICON）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21384&ev=exbrand_299242",
            "commentCount": 479519,
            "goodRate": 0.99
        },
        {
            "pname": "南国黄辣椒酱",
            "price": "21.9",
            "detail_url": "http://item.jd.com/623760.html",
            "img_url": "https://img10.360buyimg.com/n5/jfs/t19657/243/864478450/255239/96290898/5aaf4e7eN8ebc0069.jpg",
            "brand": "南国（nanguo）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_13331",
            "commentCount": 58903,
            "goodRate": 0.99
        },
        {
            "pname": "茂德公香辣牛肉225g",
            "price": "15.8",
            "detail_url": "http://item.jd.com/6644451.html",
            "img_url": "https://img11.360buyimg.com/n5/jfs/t1/116838/37/13918/142354/5f2a1b74Ebaa87863/112a975d7d007744.jpg",
            "brand": "茂德公（Modocom）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_193767",
            "commentCount": 293333,
            "goodRate": 0.97
        },
        {
            "pname": "蜀姑娘剁椒酱280g",
            "price": "13.9",
            "detail_url": "http://item.jd.com/8922029.html",
            "img_url": "https://img14.360buyimg.com/n5/jfs/t26137/160/265687491/381152/439f23c9/5b8ce45bN755f649f.jpg",
            "brand": "蜀姑娘",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_274046",
            "commentCount": 200798,
            "goodRate": 0.97
        }
    ]
}
```





前端接口：



```java
/**
     * 根据主公司id获取到公司的信息和公司竞品项目的信息
     * @param regis_id
     * @return
     */
    @RequestMapping(value = "/getCompet/{regis_id}",method = RequestMethod.GET)
    public String getCompetInfo(@PathVariable("regis_id")String regis_id){
        log.info("receive"+regis_id);
        long start=System.currentTimeMillis();
        CompetResponse response=this.searchServiceBlockingStub.searchCompet(CompetRequest.newBuilder().setRegisId(regis_id).build());
        long end=System.currentTimeMillis();
        log.info("search:"+regis_id+"over,time："+(end-start));
        return response.getResponse();

    }
```







2.1

##### 知识图谱

品牌与品类直接关系，一个品牌可能有多种品类的产品，一个品类有多个品牌的产品。可以显示竞争关系

食品、生鲜模块

返回json样例

```json
				"keyword" : "冷冻食品",
        "nodesMap" : [{
                        "name" : "蒙都",
                        "id" : "3481",
                        "category" : "3"
                },
                {
                        "name" : "大午",
                        "id" : "3415",
                        "category" : "3"
                }
        ],
        "linksMap" : [
                {
                        "source" : "12956",
                        "target" : "7427"
                },
                {
                        "source" : "8864",
                        "target" : "12956"
                },
```





##### 雷达图

使用LDA模型来划分主题（不太准确），模型输入为所有目前爬取到的食品评论，大概就是分成5类会比较好，然后每一类聚的词如下：

```
Topic # 0: 
味道 早餐 商品 感觉 客服 牌子 服务 假货 孩子 自营
Topic # 1: 
活动 感觉 味道 盖子 东西 物流 有点 垃圾 品牌 全部
Topic # 2: 
味道 物流 价格 速度 质量 发货 产品 东西 购物 口味
Topic # 3: 
东西 实惠 外包装 商品 hellip 物流 盒子 价格 瓶子 漏油
Topic # 4: 
桂圆 箱子 莲子 物流 品牌 口感 营养 价格 大家 红豆

Topic # 0: 
味道 口味 牛肉 炒菜 感觉 有点 早餐 豆瓣酱 正宗 牌子
Topic # 1: 
口感 物流 营养 风味 产品包装 小哥 孩子 评价 早餐 红豆
Topic # 2: 
质量 速度 物流 购物 发货 信赖 品牌 商品 自营 辣椒酱
Topic # 3: 
活动 辣酱 牌子 黑芝麻 有点 芝麻 产品 米饭 辣味 火锅
Topic # 4: 
东西 价格 小米 实惠 物流 优惠 活动 性价比 感觉 新鲜
```

对上面的词进行重新筛选和划分：包装、价格、物流、味道、服务。每个主题词有对应的相关词，用主题词和相关词来提取评论内容。

```
topic_words_list1 = {
    '包装': {'包装', '罐装', '罐子', '盖子', '瓶子', '质量'},
    '价格': {'价格', '性价比', '价位', '单价', '价钱', '优惠'},
    '物流': {'物流', '配送', '快递', '发货'},
    '味道': {'味道', '口味', '口感', '早餐', '粥', '食材', '辣椒', '营养'},
    '服务': {'服务', '态度', '客服', '投诉', '工作人员', '店家', '商家', '体验', '描述'},
}
```

每个维度有对应的分数

目前有三种方法计算得：

1.TextCNN

有分数计算，有好评率计算

2.snowNLP

有分数计算，有好评率计算

3.情感词典法

返回好评率，因为分数计算不一定能够准确反映，词的分值是自己定的

格式：

```
{"包装":0.78,
"价格":0.78,
"物流":0.78,
"味道":0.78,
"服务":0.78
}
```

需要的话每个维度也有对应的词频统计

Maybe MongoDB来存

```json
{
        "sku_id": 844099,
        "pname": "陶华碧老干妈油辣椒",
        "price": "9.9",
        "detail_url": "http://item.jd.com/844099.html",
        "img_url": "https://img14.360buyimg.com/n5/jfs/t1/120265/6/15663/58734/5f8fa697Ef4f51524/9650bf9c0cb1cfb9.jpg",
        "brand": "陶华碧老干妈",
        "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_16737",
        "commentCount": 2127107,
        "goodRate": 0.99,
        "comment_score": {
            "包装": 0.78,
            "价格": 0.78,
            "物流": 0.78,
            "味道": 0.78,
            "服务": 0.78
        }
    }
```

```json
{
        "sku_id": 844099,
        "pname": "陶华碧老干妈油辣椒",
        "price": "9.9",
        "detail_url": "http://item.jd.com/844099.html",
        "img_url": "https://img14.360buyimg.com/n5/jfs/t1/120265/6/15663/58734/5f8fa697Ef4f51524/9650bf9c0cb1cfb9.jpg",
        "brand": "陶华碧老干妈",
        "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_16737",
        "commentCount": 2127107,
        "goodRate": 0.99,
        "包装": 0.78,
        "价格": 0.78,
        "物流": 0.78,
        "味道": 0.78,
        "服务": 0.78
    }
```

```json
"comment_score":[
  {
	"brand": "泰奇食品",
	"package_score": 0.839622641509434,
	"price_score": 0.7701149425287356,
	"logistics_score": 0.7777777777777778,
	"taste_score": 0.7448630136986302,
	"service_score": 0.7628865979381443,
    "compet_sku": 7552427
},
 {
	"brand": "方家铺子",
	"package_score": 0.6807017543859649,
	"price_score": 0.92,
	"logistics_score": 0.7050359712230215,
	"taste_score": 0.621301775147929,
	"service_score": 0.7731958762886598,
    "compet_sku": 7552427
}
  {
	"brand": "老干妈",
	"package_score": 0.8,
	"price_score": 0.9318181818181818,
	"logistics_score": 0.781021897810219,
	"taste_score": 0.6319702602230484,
	"service_score": 0.6385542168674698,
    "compet_sku": 844099
}
]
```



or

mysql

```
sku_id,package_score,price_score,logistics_score,taste_score,service_score
```

编写MongoDB进行查询测试，不知为何比mysql要慢很多，一次测试要6s多。因此暂时使用mysql

##### 表6：comment_score

建表语句

```sql
create table comment_score (
	sku_id VARCHAR(20) NOT NULL,
	brand VARCHAR(20) NOT NULL,
	package_score VARCHAR(20),
	price_score VARCHAR(20),
	logistics_score VARCHAR(20),
	taste_score VARCHAR(20),
	service_score VARCHAR(20),
  compet_sku VARCHAR(20),
	PRIMARY KEY ( sku_id )
 )ENGINE=InnoDB CHARSET=utf8;
```



```sql
create table comment_score (
	sku_id VARCHAR(20) NOT NULL,
	brand VARCHAR(20) NOT NULL,
	package_score VARCHAR(20),
	price_score VARCHAR(20),
	logistics_score VARCHAR(20),
	taste_score VARCHAR(20),
	service_score VARCHAR(20),
	PRIMARY KEY ( sku_id )
 )ENGINE=InnoDB CHARSET=utf8;
```

##### 玫瑰图

对商品的所有评论进行情感打分，或者对每个维度情感打分，获取到每条评论的分数，存储所有评论分数，根据分数的分布来绘制玫瑰图。

例如0-0.2非常不满意、0.2-0.4不满意、0.4-0.6一般、0.6-0.8满意、0.8-1.0非常满意





##### 词云图

每个产品的词频统计

python处理后如下：

```
好,271
不错,208
快,88
方便,49
实惠,36
优惠,25
贵,25
老,25
甜,24
自营,20
服务,19
便宜,18
活动,16
足,16
完好,15
很足,14
最好,13
确实,12
很好,12
挺好吃,12
丰富,10
高,10
新,10
软,7
辛苦,7
很甜,6
健康,6
不腻,5
合适,5
稠,4
香,4
快捷,4
最,4
挺好,3
偏贵,3
重要,3
全,3
浓稠,3
直接,3
有贵,3
便捷,3
清淡,3
完整,3
完美,3
舒心,3
试试,3
浓郁,3
精美,3
稀,3
迅速,3
连续,2
浓,2
成功,2
俱佳,2
很棒,2
正好,2
最爱,2
热,2
温暖,2
很,2
稠糊,2
坏,2
容易,2
愉快,2
细腻,2
发一,2
干活,2
粘腻,1
近,1
多稠,1
变,1
甜腻,1
考研,1
简单,1
高效,1
不,1
强,1
卫生,1
凹,1
妥妥,1
调节,1
很纯,1
传输,1
长,1
软软,1
有大,1
固体,1
生活,1
蛮好,1
最贵,1
挑,1
仔细,1
正面,1
低廉,1
厉害,1
压破,1
激烈,1
制作,1
精良,1
久,1
严格,1
烦恼,1
生产,1
浓厚,1
出奇,1
合理,1
粘稠,1
快乐,1
清纯,1
生鲜,1
合作,1
甜美,1
严实,1
不好意思,1
完全,1
良心,1
重,1
寡淡,1
清甜,1
干净,1
活跃,1
发腻,1
很软,1
运输,1
惊喜,1
严谨,1
```

目前不知道怎么存这一部分数据，maybe MongoDB

```json
{
  "sku_id": 1234567,
  "wordCount":[
    {
      "word": "好",
      "count": 271
    },
    {
      "word": "不错",
      "count": 208
    },
    ...
  ]
}
```







#### 数据库2.0

使用regis_id来查询，到redis找到regis_id对应的竞品公司，regis_id对应的sku_id，sku_id对应的竞品商品，传给mysql所有的regis_id和sku_id。

redis使用数据库1和2

##### mysql：

建表语句

```sql
CREATE TABLE comment_score (
	sku_id VARCHAR ( 20 ) NOT NULL,
	brand VARCHAR ( 20 ) NOT NULL,
	package_score VARCHAR ( 20 ),
	price_score VARCHAR ( 20 ),
	logistics_score VARCHAR ( 20 ),
	taste_score VARCHAR ( 20 ),
	service_score VARCHAR ( 20 ),
	PRIMARY KEY ( sku_id ) 
) ENGINE = INNODB CHARSET = utf8;

CREATE TABLE company (
	regis_id VARCHAR ( 20 ) NOT NULL,
	proj_name VARCHAR ( 50 ) NOT NULL,
	img_url VARCHAR ( 100 ),
	finance_round VARCHAR ( 10 ),
	es_time VARCHAR ( 20 ),
	region VARCHAR ( 20 ),
	proj_desc text,
	company_name VARCHAR ( 100 ),
	address text,
	lng VARCHAR ( 20 ) NULL,
	lat VARCHAR ( 20 ) NULL,
	regis_capital VARCHAR ( 20 ),
	org_code VARCHAR ( 30 ),
	phone_num VARCHAR ( 20 ),
	PRIMARY KEY ( regis_id ) 
) ENGINE = INNODB CHARSET = utf8;

CREATE TABLE jd_info (
	sku_id VARCHAR ( 20 ) NOT NULL,
	title text,
	price DOUBLE,
	shop VARCHAR ( 50 ),
	detail_url text,
	pname VARCHAR ( 100 ),
	weight VARCHAR ( 20 ),
	origin VARCHAR ( 20 ),
	img_url text,
	brand VARCHAR ( 20 ),
	brand_url text,
	commentCount INT,
goodRate DOUBLE,
PRIMARY KEY ( sku_id )) ENGINE = INNODB CHARSET = utf8;
```

Mapper:

```xml
   <select id="selectCompetBySkuIds" parameterType="java.util.List" resultType="JDdetail">
        select pname,price,detail_url,img_url,brand,brand_url,commentCount,goodRate
        from `jd_info`
        where `sku_id` in
        <foreach collection="skuIds" item="skuId" index="index"
                 open="(" close=")" separator=",">
            #{skuId}
        </foreach>
        order by field(
        `sku_id`,
        <foreach collection="skuIds" separator="," item="skuId">
            #{skuId}
        </foreach>
        )
    </select>

    <select id="selectCompetByRegisIds" parameterType="java.util.List" resultType="Compet_geo">
        select proj_name,company_name,img_url,region,lng,lat
        from `company`
        where `regis_id` in
        <foreach collection="regisIds" item="regisId" index="index"
                 open="(" close=")" separator=",">
            #{regisId}
        </foreach>
    </select>

    <select id="selectCommentScoreBySkuIds" parameterType="java.util.List" resultType="Comment_score">
        select brand,package_score,price_score,logistics_score,taste_score,service_score
        from `comment_score`
        where `sku_id` in
        <foreach collection="skuIds" item="skuId" index="index"
                 open="(" close=")" separator=",">
            #{skuId}
        </foreach>
        order by field(
        `sku_id`,
        <foreach collection="skuIds" separator="," item="skuId">
            #{skuId}
        </foreach>
        )
    </select>
```

```java
		@Select("SELECT proj_name,img_url,es_time,region,company_name,proj_desc,address,lng,lat,phone_num FROM company WHERE regis_id = #{regis_id}")
    CompanyInfo selectCompanyBasicInfo(@Param("regis_id") String regis_id);

		List<JDdetail> selectCompetBySkuIds(@Param("skuIds") List<String> skuIds);

    @Select("select pname,price,detail_url,img_url,brand,brand_url,commentCount,goodRate from jd_info where sku_id = #{skuId}")
    JDdetail selectInfoBySkuId(@Param("skuId") String skuId);

    List<Compet_geo> selectCompetByRegisIds(@Param("regisIds") List<String> regisIds);

    List<Comment_score> selectCommentScoreBySkuIds(@Param("skuIds") List<String> skuIds);
```

```java
				String regis_id = "440108400003939";
        List<String> competsList = redisDao.getCompetRegisId(regis_id);
        String skuId = redisDao.getSkuId(regis_id);
        List<String> skuIdList = redisDao.getCompetSkuId(skuId);
     		log.info(skuIdList.toString())
        List<Compet_geo> compets = competMapper.selectCompetByRegisIds(competsList);
        CompanyInfo companyInfo = competMapper.selectCompanyBasicInfo(regis_id);
        JDdetail jDdetail = competMapper.selectInfoBySkuId(skuId);
        List<JDdetail> jDdetails = competMapper.selectCompetBySkuIds(skuIdList);
    		skuIdList.add(0,skuId);
        List<Comment_score> scoreList = competMapper.selectCommentScoreBySkuIds(skuIdList);
        Allinfo allinfo = new Allinfo();
        allinfo.setCompanyInfo(companyInfo);
        allinfo.setCompet_geoList(compets);
        allinfo.setJdetail(jDdetail);
        allinfo.setCompet_jdetails(jDdetails);
        allinfo.setScoreList(scoreList);
        String jd_info=gson.toJson(allinfo);
        System.out.println(jd_info);
```

返回样例：

```json
{
    "companyInfo": {
        "proj_name": "老干妈",
        "img_url": "https://zhengxin-pub.cdn.bcebos.com/brandpic/dad5c7ad216fc82776763b404b4ae491_fullsize.jpg",
        "es_time": "1997/10/5",
        "region": "贵阳",
        "company_name": "贵阳南明老干妈风味食品有限责任公司",
        "proj_desc": "老干妈（陶华碧）牌油制辣椒是贵州地区传统风味食品之一。老干妈是国内生产及销售量最大的辣椒制品生产企业，主要生产风味豆豉、风味鸡油辣椒、香辣菜、风味腐乳等20余个系列产品。",
        "address": "贵州省贵阳市南明区龙洞堡见龙路138-15号",
        "lng": "106.786346",
        "lat": "26.539301",
        "phone_num": "0851-85406886"
    },
    "compet_geoList": [
        {
            "proj_name": "小酱仙",
            "company_name": "北京九门坊商贸股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d12d_fullsize.jpg",
            "region": "北京",
            "lng": "116.4908849",
            "lat": "39.92323986"
        },
        {
            "proj_name": "鲜8食品",
            "company_name": "北京八日鲜食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d16d_fullsize.jpg",
            "region": "北京",
            "lng": "116.3529198",
            "lat": "40.03605554"
        },
        {
            "proj_name": "媛家酱",
            "company_name": "北京媛家科技发展有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d14d_fullsize.jpg",
            "region": "北京",
            "lng": "116.278586",
            "lat": "40.13767907"
        },
        {
            "proj_name": "川崎食品",
            "company_name": "上海川崎食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d22d_fullsize.jpg",
            "region": "上海",
            "lng": "121.6176672",
            "lat": "31.33780877"
        },
        {
            "proj_name": "美鑫食品",
            "company_name": "江苏美鑫食品科技有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d11d_fullsize.jpg",
            "region": "徐州",
            "lng": "117.2830176",
            "lat": "34.2999927"
        },
        {
            "proj_name": "福鑫食品",
            "company_name": "福鑫食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d19d_fullsize.jpg",
            "region": "福建",
            "lng": "120.4463718",
            "lat": "30.46960298"
        },
        {
            "proj_name": "老恒和",
            "company_name": "湖州老恒和酿造有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d27d_fullsize.jpg",
            "region": "湖州",
            "lng": "120.165284",
            "lat": "30.84716287"
        },
        {
            "proj_name": "咸亨股份",
            "company_name": "绍兴咸亨食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d25d_fullsize.jpg",
            "region": "绍兴",
            "lng": "120.6047735",
            "lat": "30.02942402"
        },
        {
            "proj_name": "安记食品",
            "company_name": "安记食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d24d_fullsize.jpg",
            "region": "福建",
            "lng": "118.56679",
            "lat": "24.87740594"
        },
        {
            "proj_name": "新青大",
            "company_name": "青岛新青大食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d17d_fullsize.jpg",
            "region": "青岛",
            "lng": "120.391189",
            "lat": "36.12502688"
        },
        {
            "proj_name": "二和嫁嫁",
            "company_name": "武汉明利和丰食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d15d_fullsize.jpg",
            "region": "武汉",
            "lng": "114.3076592",
            "lat": "30.41501682"
        },
        {
            "proj_name": "长剑牌",
            "company_name": "深圳市聚味食品科技有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d10d_fullsize.jpg",
            "region": "深圳",
            "lng": "114.0680991",
            "lat": "22.62821243"
        },
        {
            "proj_name": "海天味业",
            "company_name": "佛山市海天调味食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d21d_fullsize.jpg",
            "region": "广东",
            "lng": "113.128595",
            "lat": "23.05310401"
        },
        {
            "proj_name": "嘉豪食品",
            "company_name": "广东嘉豪食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d20d_fullsize.jpg",
            "region": "广东",
            "lng": "113.3619163",
            "lat": "22.59199782"
        },
        {
            "proj_name": "千禾味业",
            "company_name": "千禾味业食品股份有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d23d_fullsize.jpg",
            "region": "四川",
            "lng": "103.8385248",
            "lat": "30.04827213"
        },
        {
            "proj_name": "味之浓",
            "company_name": "四川味之浓食品有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d18d_fullsize.jpg",
            "region": "四川",
            "lng": "103.8599541",
            "lat": "30.03260251"
        },
        {
            "proj_name": "太阳食品",
            "company_name": "西安太阳食品集团公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d26d_fullsize.jpg",
            "region": "西安",
            "lng": "109.003131",
            "lat": "34.2372613"
        },
        {
            "proj_name": "广海记",
            "company_name": "新疆广海记农业科技发展有限公司",
            "img_url": "https://zhengxin-pub.cdn.bcebos.com/logopic/fa2b3dc48749a7f183d46f110802d13d_fullsize.jpg",
            "region": "新疆",
            "lng": "83.64570001",
            "lat": "46.52939588"
        }
    ],
    "jdetail": {
        "pname": "陶华碧老干妈油辣椒",
        "price": "9.9",
        "detail_url": "http://item.jd.com/844099.html",
        "img_url": "https://img14.360buyimg.com/n1/jfs/t1/120265/6/15663/58734/5f8fa697Ef4f51524/9650bf9c0cb1cfb9.jpg",
        "brand": "陶华碧老干妈",
        "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_16737",
        "commentCount": 2127107,
        "goodRate": 0.99
    },
    "compet_jdetails": [
        {
            "pname": "海底捞海底捞好好吃饭精品牛肉佐餐酱200g",
            "price": "18.9",
            "detail_url": "http://item.jd.com/100002398567.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/127025/26/10782/178810/5f45d080E3de7bdcc/2d00289bd4067fd6.jpg",
            "brand": "海底捞",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_7811",
            "commentCount": 1199782,
            "goodRate": 0.99
        },
        {
            "pname": "海天招牌拌饭酱 300g",
            "price": "12.9",
            "detail_url": "http://item.jd.com/1338357.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/84351/29/14025/181954/5db1153bEc5ef8d6b/2d404cb8213120cd.jpg",
            "brand": "海天",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_7876",
            "commentCount": 732884,
            "goodRate": 0.99
        },
        {
            "pname": "吉香居暴下饭川香味250g*1瓶",
            "price": "8.8",
            "detail_url": "http://item.jd.com/4665497.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/147320/38/16243/196908/5fc5ba39Ef98f5927/2a318856fc13374a.jpg",
            "brand": "吉香居（JI XIANG JU）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_41569",
            "commentCount": 684614,
            "goodRate": 0.99
        },
        {
            "pname": "鹃城牌鹃城牌 郫县豆瓣酱500g",
            "price": "10.9",
            "detail_url": "http://item.jd.com/3798578.html",
            "img_url": "https://img13.360buyimg.com/n1/jfs/t19588/40/2649718355/240859/9b5fb073/5b068789N9f77fb92.jpg",
            "brand": "鹃城牌（juanchengpai）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22173&ev=exbrand_137101",
            "commentCount": 682865,
            "goodRate": 0.99
        },
        {
            "pname": "特瑞肯特瑞肯烧烤全家福组合321g/盒",
            "price": "19.9",
            "detail_url": "http://item.jd.com/5964486.html",
            "img_url": "https://img11.360buyimg.com/n1/jfs/t1/166989/5/4163/176096/6010d5f6E36bb29f6/1b2169865dc35cfe.jpg",
            "brand": "特瑞肯（TRICON）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21384&ev=exbrand_299242",
            "commentCount": 479519,
            "goodRate": 0.99
        },
        {
            "pname": "厨邦厨邦蒜蓉辣椒酱210g",
            "price": "5.9",
            "detail_url": "http://item.jd.com/3763759.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/6981/33/9015/109899/5c1320feEe1629799/ee027320a8174f1a.jpg",
            "brand": "厨邦",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_21703",
            "commentCount": 442845,
            "goodRate": 0.99
        },
        {
            "pname": "李锦记蒜蓉辣酱226g",
            "price": "16.9",
            "detail_url": "http://item.jd.com/1078121.html",
            "img_url": "https://img11.360buyimg.com/n1/jfs/t1/163081/19/3983/181325/600fad6eE1afe6993/c0757fad176ac306.jpg",
            "brand": "李锦记",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_11330",
            "commentCount": 359429,
            "goodRate": 0.99
        },
        {
            "pname": "茂德公香辣牛肉225g",
            "price": "15.8",
            "detail_url": "http://item.jd.com/6644451.html",
            "img_url": "https://img11.360buyimg.com/n1/jfs/t1/116838/37/13918/142354/5f2a1b74Ebaa87863/112a975d7d007744.jpg",
            "brand": "茂德公（Modocom）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_193767",
            "commentCount": 293333,
            "goodRate": 0.97
        },
        {
            "pname": "韩今辣酱 500g",
            "price": "32.9",
            "detail_url": "http://item.jd.com/3681877.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/160852/7/3804/120493/600a4e08E32cd68a0/29d8d78ad2d94aa4.jpg",
            "brand": "韩今",
            "brand_url": "https://list.jd.com/list.html?cat=1320,5019,5024&tid=17660&ev=exbrand_497056",
            "commentCount": 257086,
            "goodRate": 0.99
        },
        {
            "pname": "川娃子烧椒酱",
            "price": "12.8",
            "detail_url": "http://item.jd.com/100005264233.html",
            "img_url": "https://img13.360buyimg.com/n1/jfs/t1/138478/34/18248/120129/5fd48839Ea31e647b/348faed7d6bb1927.jpg",
            "brand": "川娃子",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_277836",
            "commentCount": 246186,
            "goodRate": 0.98
        },
        {
            "pname": "虎邦鲁西牛肉 鲜椒酱 210g",
            "price": "18.8",
            "detail_url": "http://item.jd.com/4410290.html",
            "img_url": "https://img10.360buyimg.com/n1/jfs/t1/138078/23/20214/196826/5fe69ddfE6a0f4acd/ec4d43beaeddaaeb.jpg",
            "brand": "虎邦",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_313861",
            "commentCount": 239716,
            "goodRate": 0.99
        },
        {
            "pname": "蜀姑娘剁椒酱280g",
            "price": "13.9",
            "detail_url": "http://item.jd.com/8922029.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t26137/160/265687491/381152/439f23c9/5b8ce45bN755f649f.jpg",
            "brand": "蜀姑娘",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_274046",
            "commentCount": 200798,
            "goodRate": 0.97
        },
        {
            "pname": "春光香辣 灯笼辣椒酱",
            "price": "5.5",
            "detail_url": "http://item.jd.com/5676502.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/168046/5/3152/153166/60050321E524e17b5/0679bc09e46b50ce.jpg",
            "brand": "春光",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_43453",
            "commentCount": 178660,
            "goodRate": 0.99
        },
        {
            "pname": "清净园辣椒酱",
            "price": "18.9",
            "detail_url": "http://item.jd.com/100007770057.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/119715/3/10120/145451/5eff13e8Ee44efcfe/62c6565c6df33f64.jpg",
            "brand": "清净园",
            "brand_url": "https://list.jd.com/list.html?cat=1320,5019,5024&tid=17660&ev=exbrand_14525",
            "commentCount": 128798,
            "goodRate": 0.98
        },
        {
            "pname": "川南油辣子280g",
            "price": "13.9",
            "detail_url": "http://item.jd.com/100000428823.html",
            "img_url": "https://img13.360buyimg.com/n1/jfs/t1/112636/32/743/243311/5e8fe46aE45776f1a/7c2f89ec422d2685.jpg",
            "brand": "川南",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_21421",
            "commentCount": 103230,
            "goodRate": 0.99
        },
        {
            "pname": "饭扫光剁椒酱200g",
            "price": "12.6",
            "detail_url": "http://item.jd.com/100015029096.html",
            "img_url": "https://img11.360buyimg.com/n1/jfs/t1/114077/23/16258/141141/5f471dfeE06658c39/5eb435ff56a86281.jpg",
            "brand": "饭扫光",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_6655",
            "commentCount": 66492,
            "goodRate": 0.98
        },
        {
            "pname": "南国黄辣椒酱",
            "price": "21.9",
            "detail_url": "http://item.jd.com/623760.html",
            "img_url": "https://img10.360buyimg.com/n1/jfs/t19657/243/864478450/255239/96290898/5aaf4e7eN8ebc0069.jpg",
            "brand": "南国（nanguo）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_13331",
            "commentCount": 58903,
            "goodRate": 0.99
        },
        {
            "pname": "聪厨蒜蓉辣酱180g",
            "price": "14.9",
            "detail_url": "http://item.jd.com/100008050179.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/144033/18/4435/130740/5f278affE92b374b5/c12ad4e698a7658b.jpg",
            "brand": "聪厨",
            "brand_url": "https://list.jd.com/list.html?cat=12218,21455,21457&ev=exbrand_246393",
            "commentCount": 58546,
            "goodRate": 0.98
        },
        {
            "pname": "川珍双椒酱",
            "price": "11.8",
            "detail_url": "http://item.jd.com/100010637500.html",
            "img_url": "https://img10.360buyimg.com/n1/jfs/t1/132004/1/5544/161810/5f2109b5E48b8ad6e/e83ce5961c5b854a.jpg",
            "brand": "川珍",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_5534",
            "commentCount": 46350,
            "goodRate": 0.98
        },
        {
            "pname": "亨氏亨氏番茄辣椒酱",
            "price": "9.9",
            "detail_url": "http://item.jd.com/2169939.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t2416/26/1479225670/79008/b96177d7/56654713Nf7dc5e84.jpg",
            "brand": "亨氏（Heinz）",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_8241",
            "commentCount": 33443,
            "goodRate": 0.99
        },
        {
            "pname": "渡江宴烧椒酱200g*2瓶+彩椒酱200g*2瓶",
            "price": "39.9",
            "detail_url": "http://item.jd.com/100009224435.html",
            "img_url": "https://img10.360buyimg.com/n1/jfs/t1/140174/26/11954/205830/5f93c6faE6e79f24c/39101d3c1487d4fb.jpg",
            "brand": "渡江宴",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_230226",
            "commentCount": 31563,
            "goodRate": 0.98
        },
        {
            "pname": "六婆辣椒油 油泼辣子 四川特色油辣子凉拌红油 熟油辣子230g",
            "price": "14.8",
            "detail_url": "http://item.jd.com/12738293049.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/167145/28/1693/161406/5ff9506bEd3defd32/5c77e5b6fba4cd05.jpg",
            "brand": "六婆",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21382&ev=exbrand_47655",
            "commentCount": 21262,
            "goodRate": 0.99
        },
        {
            "pname": "老坛子鲜椒酱",
            "price": "22.8",
            "detail_url": "http://item.jd.com/100006982973.html",
            "img_url": "https://img13.360buyimg.com/n1/jfs/t1/128749/19/19692/179457/5fbcc418E8ae255d6/46c537ad3e6fcffd.jpg",
            "brand": "老坛子",
            "brand_url": "https://list.jd.com/list.html?cat=12218,13553,13578&ev=exbrand_470200",
            "commentCount": 15646,
            "goodRate": 0.98
        },
        {
            "pname": "湖南超特辣剁辣椒 地道湘2.3kg精制朝天椒 4.6斤大瓶装蒜蓉剁椒酱辣椒酱 蒸菜炒菜拌饭酱调料",
            "price": "29.8",
            "detail_url": "http://item.jd.com/30750526915.html",
            "img_url": "https://img10.360buyimg.com/n1/jfs/t1/40450/6/3205/246233/5cc3f794E1d6c6327/e13d69ef17cca1c6.jpg",
            "brand": "地道湘",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21389&ev=exbrand_355572",
            "commentCount": 8552,
            "goodRate": 0.97
        },
        {
            "pname": "仲景调味油麻油 低温冷萃非油炸 凉拌热炒烧烤火锅米线凉拌菜125ml 花椒油+辣椒油",
            "price": "29.6",
            "detail_url": "http://item.jd.com/42269144142.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/131204/20/16777/117754/5fbb8390E2118cd7e/636e8f972ee71d11.jpg",
            "brand": "仲景",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21385&ev=exbrand_21030",
            "commentCount": 6395,
            "goodRate": 0.99
        },
        {
            "pname": "大嘴先生双椒酱230g",
            "price": "13.8",
            "detail_url": "http://item.jd.com/100015687534.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/148847/40/9185/174864/5f6c7401Edf331c24/5e357c156129afd8.jpg",
            "brand": "大嘴先生",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_525012",
            "commentCount": 6212,
            "goodRate": 0.97
        },
        {
            "pname": "李子柒 贵州糟辣酱 辣椒酱贵州风味特产酸辣鲜香 调味酱 230g*3瓶",
            "price": "34.9",
            "detail_url": "http://item.jd.com/10024090168114.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/171295/38/2626/388970/5fff9d85E28d6451d/b1593611077d8dd1.jpg",
            "brand": "李子柒",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_387375",
            "commentCount": 4217,
            "goodRate": 0.98
        },
        {
            "pname": "酱先生鲜椒酱",
            "price": "14.9",
            "detail_url": "http://item.jd.com/100015856592.html",
            "img_url": "https://img12.360buyimg.com/n1/jfs/t1/144349/5/14222/228782/5facddd4E16d36a65/a9fea223937cd734.jpg",
            "brand": "酱先生",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=22171&ev=exbrand_426958",
            "commentCount": 2282,
            "goodRate": 0.98
        },
        {
            "pname": "华源良品广西八角大料香料调料大全桂皮香叶干辣椒茴香花椒厨房香辛料煲汤炖肉大红八角全干货 八角大料 250克",
            "price": "31.2",
            "detail_url": "http://item.jd.com/10020176668539.html",
            "img_url": "https://img14.360buyimg.com/n1/jfs/t1/144251/8/7261/408739/5f4dfff4E0b4e1279/c2f6c7332d6c1bea.jpg",
            "brand": "华源良品",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21384&ev=exbrand_593869",
            "commentCount": 1147,
            "goodRate": 0.95
        },
        {
            "pname": "【三瓶装】人高一等 香辣麻辣对虾酱200gX3瓶  下饭拌菜凉拌炒菜海鲜酱肉酱辣椒酱 厨房调味品 简装虾酱香辣味x3",
            "price": "39.9",
            "detail_url": "http://item.jd.com/54222597156.html",
            "img_url": "https://img11.360buyimg.com/n1/jfs/t1/112649/25/11113/141327/5efb0258E3626ad46/dd86b4e3652f4e44.jpg",
            "brand": "人高一等",
            "brand_url": "https://list.jd.com/list.html?cat=1320,1584,2677&tid=21383&ev=exbrand_314634",
            "commentCount": 970,
            "goodRate": 0.95
        }
    ],
    "comment_scores": [
        {
            "brand": "老干妈",
            "package_score": "0.724137931",
            "price_score": "0.864583333",
            "logistics_score": "0.728813559",
            "taste_score": "0.721276596",
            "service_score": "0.695652174"
        },
        {
            "brand": "海底捞",
            "package_score": "0.688888889",
            "price_score": "0.754098361",
            "logistics_score": "0.788888889",
            "taste_score": "0.613675214",
            "service_score": "0.551724138"
        },
        {
            "brand": "海天",
            "package_score": "0.752808989",
            "price_score": "0.815384615",
            "logistics_score": "0.72826087",
            "taste_score": "0.752475248",
            "service_score": "0.7"
        },
        {
            "brand": "吉香居",
            "package_score": "0.684210526",
            "price_score": "0.933333333",
            "logistics_score": "0.718120805",
            "taste_score": "0.642092747",
            "service_score": "0.61971831"
        },
        {
            "brand": "鹃城牌",
            "package_score": "0.758241758",
            "price_score": "0.843137255",
            "logistics_score": "0.700729927",
            "taste_score": "0.636678201",
            "service_score": "0.685393258"
        },
        {
            "brand": "特瑞肯",
            "package_score": "0.783505155",
            "price_score": "0.790123457",
            "logistics_score": "0.725",
            "taste_score": "0.52027027",
            "service_score": "0.633333333"
        },
        {
            "brand": "厨邦",
            "package_score": "0.782608696",
            "price_score": "0.842105263",
            "logistics_score": "0.810810811",
            "taste_score": "0.743494424",
            "service_score": "0.62962963"
        },
        {
            "brand": "李锦记",
            "package_score": "0.904109589",
            "price_score": "0.818181818",
            "logistics_score": "0.794871795",
            "taste_score": "0.757062147",
            "service_score": "0.634920635"
        },
        {
            "brand": "茂德公",
            "package_score": "0.647058824",
            "price_score": "0.909090909",
            "logistics_score": "0.674698795",
            "taste_score": "0.65210084",
            "service_score": "0.62295082"
        },
        {
            "brand": "韩今",
            "package_score": "0.818181818",
            "price_score": "0.86440678",
            "logistics_score": "0.840909091",
            "taste_score": "0.753456221",
            "service_score": "0.591549296"
        },
        {
            "brand": "川娃子",
            "package_score": "0.807692308",
            "price_score": "0.837209302",
            "logistics_score": "0.817307692",
            "taste_score": "0.640601504",
            "service_score": "0.628205128"
        },
        {
            "brand": "虎邦",
            "package_score": "0.738636364",
            "price_score": "0.848484848",
            "logistics_score": "0.752380952",
            "taste_score": "0.707535121",
            "service_score": "0.578947368"
        },
        {
            "brand": "蜀姑娘",
            "package_score": "0.643678161",
            "price_score": "0.861111111",
            "logistics_score": "0.818181818",
            "taste_score": "0.668174962",
            "service_score": "0.617647059"
        },
        {
            "brand": "春光",
            "package_score": "0.708333333",
            "price_score": "0.87804878",
            "logistics_score": "0.629032258",
            "taste_score": "0.704797048",
            "service_score": "0.696969697"
        },
        {
            "brand": "清净园",
            "package_score": "0.733333333",
            "price_score": "0.80952381",
            "logistics_score": "0.739726027",
            "taste_score": "0.678632479",
            "service_score": "0.655737705"
        },
        {
            "brand": "川南",
            "package_score": "0.728",
            "price_score": "0.864864865",
            "logistics_score": "0.72",
            "taste_score": "0.652605459",
            "service_score": "0.706422018"
        },
        {
            "brand": "饭扫光",
            "package_score": "0.516129032",
            "price_score": "0.888888889",
            "logistics_score": "0.903225806",
            "taste_score": "0.632653061",
            "service_score": "0.555555556"
        },
        {
            "brand": "南国",
            "package_score": "0.62",
            "price_score": "0.766666667",
            "logistics_score": "0.653333333",
            "taste_score": "0.5856",
            "service_score": "0.534482759"
        },
        {
            "brand": "聪厨",
            "package_score": "0.81512605",
            "price_score": "0.952941176",
            "logistics_score": "0.758823529",
            "taste_score": "0.746268657",
            "service_score": "0.742857143"
        },
        {
            "brand": "川珍",
            "package_score": "0.634146341",
            "price_score": "0.85",
            "logistics_score": "0.734693878",
            "taste_score": "0.617486339",
            "service_score": "0.487804878"
        },
        {
            "brand": "亨氏",
            "package_score": "0.733333333",
            "price_score": "0.907407407",
            "logistics_score": "0.788732394",
            "taste_score": "0.638248848",
            "service_score": "0.767857143"
        },
        {
            "brand": "渡江宴",
            "package_score": "0.689320388",
            "price_score": "0.931034483",
            "logistics_score": "0.75",
            "taste_score": "0.730673317",
            "service_score": "0.721153846"
        },
        {
            "brand": "六婆",
            "package_score": "0.540540541",
            "price_score": "0.846153846",
            "logistics_score": "0.733333333",
            "taste_score": "0.533088235",
            "service_score": "0.485714286"
        },
        {
            "brand": "老坛子",
            "package_score": "0.691666667",
            "price_score": "0.890243902",
            "logistics_score": "0.738562092",
            "taste_score": "0.704851752",
            "service_score": "0.689320388"
        },
        {
            "brand": "地道湘",
            "package_score": "0.598484848",
            "price_score": "0.842105263",
            "logistics_score": "0.587301587",
            "taste_score": "0.584026622",
            "service_score": "0.557142857"
        },
        {
            "brand": "仲景",
            "package_score": "0.795180723",
            "price_score": "0.805555556",
            "logistics_score": "0.84375",
            "taste_score": "0.546242775",
            "service_score": "0.620689655"
        },
        {
            "brand": "大嘴先生",
            "package_score": "0.830508475",
            "price_score": "0.84",
            "logistics_score": "0.826923077",
            "taste_score": "0.659663866",
            "service_score": "0.745098039"
        },
        {
            "brand": "李子柒",
            "package_score": "0.771428571",
            "price_score": "0.818181818",
            "logistics_score": "0.6875",
            "taste_score": "0.595918367",
            "service_score": "0.555555556"
        },
        {
            "brand": "酱先生",
            "package_score": "0.578947368",
            "price_score": "0.857142857",
            "logistics_score": "0.727272727",
            "taste_score": "0.745454545",
            "service_score": "0.6"
        },
        {
            "brand": "华源良品",
            "package_score": "0.777777778",
            "price_score": "0.777777778",
            "logistics_score": "0.611111111",
            "taste_score": "0.588235294",
            "service_score": "0.625"
        },
        {
            "brand": "人高一等",
            "package_score": "0.642857143",
            "price_score": "0.714285714",
            "logistics_score": "0.8",
            "taste_score": "0.530120482",
            "service_score": "0.421052632"
        }
    ]
}
```



## 区块链溯源模块

##### 图片和视频传输问题：

图片和视频放在192服务器内网，前端直接访问不到url。

方案1：先转为base64，传String回去

问题：只能传很小的图片和图标，pass



方案2：传二进制流给前端

问题：好像在grpc不能直接通信



方案3：传byte[]

==使用方案3==



##### 二维码生成

已完成



##### 图片存储逻辑

图片名称样例：`工厂_烧制玻璃瓶-20210217172801.jpg`

在redis中存储图片的名称。摄像头根据逻辑不定时传来文件，使用scp传到192服务器上。

同时传送图片名称到trace192proj中，使用controller接收图片名字。

每一个生产环节对应一个redis库中，使用map来存，根据图片名称的前面部分`工厂_烧制玻璃瓶`来判断所在数据库。

每个redis库中，也就是每个环节中存着String结构的最新溯源码信息，每当有新的输入时，则往redis存入新的溯源码，~~使用`jedis.set("latest","xxxxxxxxx")`。当存图片信息时，先使用`jedis.get("latest")`来获取最新的溯源码~~，然后再存入对应溯源码的list中，使用`jedis.rpush("pic-name")`，这样保证某一时间段的图片能够对应溯源码。

**2.0修改版：**

每个redis库中，也就是每个环节中存着String结构的最新溯源码信息，每当有新的输入时，则往redis存入新的溯源码，使用jedis.lpush("latest",traceCode)。当存图片信息时，先使用jedis.lpop("latest")获取到最新的溯源码。然后再存入对应溯源码的list中，使用`jedis.rpush("pic-name")`，这样保证某一时间段的图片能够对应溯源码。

##### 溯源码设计

> 参考文献：**基于区块链的农产品可信溯源系统研究与实现_吴晓彤**
>
> 参考网址：http://www.ancc.org.cn    **中国物品编码中心网站**
>
> http://www.aidchina.com.cn/zhwl/54656.htm   **编码设计**

老干妈：69218047（厂商识别代码）0020（商品项目代码，大多数都是00xx）7（前面12位的校验码，有校验码计算公式）210217123050（采摘日期年月日时分秒）xxxxxxx（环节标识码，我们好像没有分支的所以不确定是乱加上去还是怎么弄）

泰奇食品：6902613（厂商识别代码）10002（商品项目代码）0（前面12位校验码）210217123050（采摘日期年月日时分秒）xxxxxxx（环节标识码，我们好像没有分支的所以不确定是乱加上去还是怎么弄）

**校验码计算公式**

```
代码位置序号

代码位置序号是指包括校验码在内的，由右至左的顺序号（校验码的代码位置序号为1）。

计算步骤

校验码的计算步骤如下：

a. 从代码位置序号2开始，所有偶数位的数字代码求和。

b. 将步骤a的和乘以3。

c. 从代码位置序号3开始，所有奇数位的数字代码求和。

d. 将步骤b与步骤c的结果相加。

e. 用大于或等于步骤d所得结果且为10最小整数倍的数减去步骤d所得结果，其差即为所求校验码的值。
```

目前由于商品只有一种，因此前面十三位都固定，变化的只有时间。



##### 溯源流程

网页上有新增产品初始信息接口，用户登录后可输入产品初始信息，即为第一个流程。输入信息到区块链的同时，更新redis中的溯源码。输入信息后即可生成这一批次产品对应的溯源码以及内部二维码。

接下来的流程只需要扫描内部二维码（个人觉得还可以登录后输入溯源码），来完成接下来各个流程信息的输入。

最后一个流程输入完成后，生成消费者溯源查询二维码。

消费者通过溯源码或者扫描二维码，即可看到该产品的溯源信息。



初始阶段：

输入：初始信息

输出：溯源码、内部二维码

存储：初始信息、redis中存储溯源码、图片信息



##### 第一个流程

通过登录，直接获取到所在公司。使用公司提前设计好的产品品类和名称，做下拉选择框供工作人员选择，同时输入其他信息。

redis数据库：4==【需不需要保存到mysql或者MongoDB中？】==

| Company（改为regis_id) | Product                            | code          |
| ---------------------- | ---------------------------------- | ------------- |
|                        | 原味八宝粥-370g-速食粥             | 6902613100020 |
|                        | 原味八宝粥-430g-速食粥             | 6902613100037 |
|                        | 紫薯粥八宝粥-370g-速食粥           | 6902613100044 |
|                        | 玉米粥八宝粥-370g-速食粥           | 6902613100051 |
|                        | 红豆香沙八宝粥-370g-速食粥         | 6902613100068 |
|                        | 桂圆莲子八宝粥-370g-速食粥         | 6902613100075 |
|                        | 风味豆豉油制辣椒-280g-辣椒酱       | 6921804700207 |
|                        | 精制牛肉末豆豉油辣椒酱-210g-辣椒酱 | 6921804700214 |
|                        | 油辣椒酱-275g-辣椒酱               | 6921804700221 |
|                        | 风味鸡油辣椒酱-280g-辣椒酱         | 6921804700238 |
|                        | 红油腐乳-260g-腐乳                 | 6921804701006 |
|                        | 油辣椒-210g-辣椒酱                 | 6921804700245 |
|                        | 辣三丁油辣椒-210g-辣椒酱           | 6921804700252 |
|                        | 番茄辣酱-210g-辣椒酱               | 6921804700269 |
|                        | 香辣菜-60g-榨菜/酱腌菜             | 6921804702003 |
|                        | 香辣菜-188g-榨菜/酱腌菜            | 6921804702010 |
|                        | 植物油火锅底料-160g-火锅底料       | 6921804703000 |
|                        | 糟辣椒火锅底料-210g-火锅底料       | 6921804703017 |

使用redis存储，==注意后面是否有空格！！！==

hash结构

key为regis_id，value为食品名称规格分类and溯源码

**逻辑**

前端登录后，**点击输入第一个流程的接口**

- 后端获取regis_id，根据regis_id从redis中获取公司下所有商品名称
- 使用regis_id调用后端mapper获取到所有的公司信息
- 拼接返回

返回给前端：公司基本信息，商品列表

**示例数据**

```json
{
    "proj_name": "老干妈",
    "img_url": "https://zhengxin-pub.cdn.bcebos.com/brandpic/dad5c7ad216fc82776763b404b4ae491_fullsize.jpg",
    "es_time": "1997/10/5",
    "region": "贵阳",
    "company_name": "贵阳南明老干妈风味食品有限责任公司",
    "proj_desc": "老干妈（陶华碧）牌油制辣椒是贵州地区传统风味食品之一。老干妈是国内生产及销售量最大的辣椒制品生产企业，主要生产风味豆豉、风味鸡油辣椒、香辣菜、风味腐乳等20余个系列产品。",
    "address": "贵州省贵阳市南明区龙洞堡见龙路138-15号",
    "lng": "106.786346",
    "lat": "26.539301",
    "phone_num": "0851-85406886",
    "productNameList": [
        {
            "name": "油辣椒-210g-辣椒酱"
        },
        {
            "name": "香辣菜-60g-榨菜/酱腌菜"
        },
        {
            "name": "糟辣椒火锅底料-210g-火锅底料"
        },
        {
            "name": "风味豆豉油制辣椒-280g-辣椒酱"
        },
        {
            "name": "辣三丁油辣椒-210g-辣椒酱"
        },
        {
            "name": "香辣菜-188g-榨菜/酱腌菜"
        },
        {
            "name": "番茄辣酱-210g-辣椒酱"
        },
        {
            "name": "风味鸡油辣椒酱-280g-辣椒酱"
        },
        {
            "name": "红油腐乳-260g-腐乳"
        },
        {
            "name": "精制牛肉末豆豉油辣椒酱-210g-辣椒酱"
        },
        {
            "name": "植物油火锅底料-160g-火锅底料"
        },
        {
            "name": "油辣椒酱-275g-辣椒酱"
        }
    ]
}
```



**填写并提交第一个流程的表单后**

前端返回：公司基本信息，所有输入信息

中间流程：

- 获取溯源码：传入公司名称，商品名称
- 存入redis溯源码，使用溯源码前十三位作为key，溯源码作为list的value
- 生成二维码图片：传入输入界面的链接以及溯源码
- 存进区块链

后端返回：（二维码、溯源码）图片

**示例数据**





##### 管理员模块

给公司管理员提供**输入接口**，注意需要判断产品是否重复，产品条形码是否重复，加锁？

是否需要提供删除产品列表的接口？

是否需要做个管理界面？（像数据库系统那样子哈哈哈哈哈哈哈哈哈哈哈哈哈哈）整个公司的溯源啥都能看到的那种



**管理员界面的功能确定：**

- 首页可以展示公司信息（感觉就是公司信息加上竞品界面）？
- 可查看该公司所有溯源列表信息，无需输入溯源码搜索或者扫描二维码。但需要选择是哪一个商品。
- 提供商品列表的增删改
- ……（想要的话可以有很多555555555【例如增删改公司信息，管理公司账号等



**新的接口**：管理员可以查看该公司所有的溯源信息，分商品查看【可下拉框选择商品列表这样子？redis中存有商品列表】（溯源码前十三位不同）。all

传入：公司名称、商品名称

返回：一个列表，包括所有的溯源码和基本信息，展示类似微博列表界面，分页展示

1.根据商品名称，从redis中获取该商品名称对应的`前十三位溯源码`,`jedis.hget("公司名称","商品名称")`

2.从redis中取出十条该商品最新的溯源码`jedis.lrange("前十三位溯源码",start,stop)`，传给区块链部分。

3.区块链部分根据溯源码取出基本信息作为列表返回给前端。

**示例数据**

```json
[
  {
        "pageCount": "5"
  },
  {
    "id": "6921804700221210219194001",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194400",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194001",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194400",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194001",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194400",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194001",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194400",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194001",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  },
  {
    "id": "6921804700221210219194400",
    "foodName": "油辣椒酱",
    "specification": "275g",
    "category": "辣椒酱",
    "latestProcess": "经销商超市",
    "time":1611970163415
  }
]
```





**管理员登录问题**

在登录的时候选择登录通道：普通登录、管理员登录





##### 登录模块

- 普通登录后就直接输入第一个流程信息吗？如果不是的话，那么在登录后，网页还是一样的，只是在录入新的批次的时候不用再次登录，这个逻辑该如何判断？
- 选择录入新的产品批次时需要登录。
- ==管理员登录后直接跳到管理员界面吗？==还是只是登录后会出现管理员界面的接口。
- 登录后输入溯源码跳转到产品溯源界面，是否可以直接在该溯源界面增加新的溯源信息？【讨论后觉得要，弄个➕，作为添加后续流程的接口（也就是二维码的接口一样的）】
- 参考：    https://github.com/bootsrc/jpassport    https://github.com/zaiyunduan123/springboot-seckill



User

```sql
CREATE TABLE `sk_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `nickname` varchar(255) NOT NULL COMMENT '昵称',
  `password` varchar(32) DEFAULT NULL COMMENT 'MD5(MD5(pass明文+固定salt)+salt',
  `salt` varchar(20) DEFAULT NULL COMMENT '混淆盐',
  `head` varchar(128) DEFAULT NULL COMMENT '头像，云存储的ID',
  `register_date` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```





##### 还需要增加

- 自定义全局异常处理类
- 返回状态实体类，错误码定义 【参考： https://github.com/zaiyunduan123/springboot-seckill 】

- 各种增删改查（哭了）注意debug  注意高并发问题。==例如在输入第一次溯源信息的时候（我们的产品列表是直接返回到界面的），管理员删除了某一个产品列表，这时候就出现管理员删除的产品列表还出现在录入信息的列表里面。==   【可以后端判断是否查询到，如果查询不到则返回失败信息，注意其他信息也不要录入】
- 登录问题（注意输入格式校验）
- redis缓存



**问题：**

输入流程如何知道溯源码？



#### 指数爬取

------

**目前问题**：

- 爬取关键词不确定【暂定4个，八宝粥、辣椒酱、泰奇八宝粥、老干妈】
- 有些爬取时间段是固定的，需要先找规律
- 展示出来的图不确定



**数据库存储**

全部使用mysql存储，注意主键增加和选取。

##### 需求图谱

字段含义：

- word(关联搜索)
- pv（浏览量）【相当于搜索热度】
- ratio（搜索变化率）【大于1上升】
- period（时间段）【周期范围】

```
keyword,word,pv,ratio,period
八宝粥,八宝粥有哪8种材料,758,0.8,20200223|20210214
八宝粥,银鹭八宝粥,3434,1.46,20200223|20210214
八宝粥,八宝粥的做法,6842,0.8,20200223|20210214
八宝粥,八宝粥品牌,190,0.84,20200223|20210214
八宝粥,X战警几部,36,1.0,20200223|20210214
八宝粥,五谷杂粮粥,950,0.63,20200223|20210214
八宝粥,营养粥的做法,40,0.51,20200223|20210214
八宝粥,杂粮粥,736,0.6,20200223|20210214
八宝粥,娃哈哈八宝粥,1164,0.99,20200223|20210214
八宝粥,八宝粥材料是哪八种,9018,0.7,20200223|20210214
八宝粥,腊八粥图片大全大图,116,0.69,20200223|20210214
八宝粥,腊八饭怎么做,28,0.74,20200223|20210214
八宝粥,美容养颜粥,102,0.69,20200223|20210214
八宝粥,黑米粥,3118,0.57,20200223|20210214
八宝粥,八宝粥材料,824,0.47,20200223|20210214
八宝粥,八宝粥图片,476,0.57,20200223|20210214
八宝粥,八宝粥怎么煮,326,0.58,20200223|20210214
八宝粥,传统腊八粥的8种材料,760,1.02,20200223|20210214
八宝粥,八宝粥的配料是哪八样,52,0.43,20200223|20210214
八宝粥,八宝,2148,1.09,20200223|20210214
八宝粥,腊八粥,11026,0.57,20200223|20210214
八宝粥,正宗八宝粥的配料,556,0.53,20200223|20210214
八宝粥,芭比娃娃的动画片,176,1.28,20200223|20210214
八宝粥,腊八粥做法,108,0.43,20200223|20210214
八宝粥,八宝饭,44052,1.57,20200223|20210214
八宝粥,美式期权,268,0.5,20200223|20210214
八宝粥,好粥道,676,0.81,20200223|20210214
八宝粥,刀豆土豆,184,0.79,20200223|20210214
八宝粥,同福,954,1.08,20200223|20210214
八宝粥,八宝粥的热量,290,0.53,20200223|20210214
```

建表语句

```sql
CREATE TABLE relate_search(
	relate_id INT AUTO_INCREMENT,
	word VARCHAR(70),
	pv VARCHAR(20),
	ratio VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(relate_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
```



##### 年龄分布

字段含义：

- desc（年龄范围）
- tgi（tgi指数）
- word_rate（关键词分布比率）
- all_rate（全网分布比率）【全网的年龄阶段比率】
- period（时间段）【周期范围】

```
desc,tgi,word_rate,all_rate,period,keyword
0-19,48.61,4.53,9.33,2021-01-01|2021-01-31,八宝粥
20-29,85.88,28.63,33.34,2021-01-01|2021-01-31,八宝粥
30-39,99.44,33.92,34.12,2021-01-01|2021-01-31,八宝粥
40-49,114.27,19.2,16.81,2021-01-01|2021-01-31,八宝粥
50+,214.34,13.71,6.4,2021-01-01|2021-01-31,八宝粥
```

建表语句

```sql
CREATE TABLE age_distribution(
	age_id INT AUTO_INCREMENT,
	age_range VARCHAR(20),
	tgi VARCHAR(20),
	word_rate VARCHAR(20),
	all_rate VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(age_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
```



##### 性别分布

字段含义：（与上面相同）

```
desc,tgi,word_rate,all_rate,period,keyword
女,130.81,63.15,48.27,2021-01-01|2021-01-31,八宝粥
男,71.24,36.85,51.73,2021-01-01|2021-01-31,八宝粥
女,98.61,47.6,48.27,2021-01-01|2021-01-31,辣椒酱
男,101.3,52.4,51.73,2021-01-01|2021-01-31,辣椒酱
女,76.2,36.78,48.27,2021-01-01|2021-01-31,老干妈
男,122.21,63.22,51.73,2021-01-01|2021-01-31,老干妈
女,85.76,41.4,48.27,2021-01-01|2021-01-31,泰奇八宝粥
男,113.29,58.6,51.73,2021-01-01|2021-01-31,泰奇八宝粥
```

建表语句

```sql
CREATE TABLE sex_distribution(
	sex_id INT AUTO_INCREMENT,
	sex_range VARCHAR(20),
	tgi VARCHAR(20),
	word_rate VARCHAR(20),
	all_rate VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(sex_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
```



##### 兴趣分布【觉得这个暂时没有必要】xxxxxxxxxxxxxxxxxxx

字段含义：（与上面相同）

```
   desc     tgi  word_rate  all_rate                 period
0  影视音乐  103.45      98.34     95.06  2021-01-01|2021-01-31
1  医疗健康  111.84      97.22     86.92  2021-01-01|2021-01-31
2    资讯  109.92      96.00     87.34  2021-01-01|2021-01-31
3  餐饮美食  121.56      95.70     78.73  2021-01-01|2021-01-31
4  软件应用  104.80      94.62     90.29  2021-01-01|2021-01-31
5  教育培训  109.21      94.20     86.26  2021-01-01|2021-01-31
6  旅游出行  118.70      89.90     75.74  2021-01-01|2021-01-31
7  书籍阅读  109.26      89.70     82.10  2021-01-01|2021-01-31
8  休闲爱好  116.64      88.24     75.65  2021-01-01|2021-01-31
9  金融财经  117.18      87.90     75.01  2021-01-01|2021-01-31
```

##### 地域分布【头条指数】or【百度指数√】

**1.头条指数**

字段含义：

- value（渗透率）
- name（省份）

```
     value name
14  0.1192   山东
18  0.0815   广东
2   0.0767   河北
15  0.0762   河南
9   0.0663   江苏
26  0.0408   陕西
16  0.0398   湖北
5   0.0394   辽宁
11  0.0383   安徽
10  0.0375   浙江
22  0.0345   四川
17  0.0303   湖南
3   0.0299   山西
19  0.0293   广西
12  0.0244   福建
0   0.0232   北京
13  0.0229   江西
7   0.0215  黑龙江
8   0.0209   上海
27  0.0191   甘肃
6   0.0184   吉林
4   0.0183  内蒙古
1   0.0174   天津
24  0.0165   云南
30  0.0160   新疆
21  0.0133   重庆
23  0.0112   贵州
20  0.0070   海南
29  0.0059   宁夏
28  0.0036   青海
25  0.0006   西藏
32  0.0001   香港
31  0.0000   台湾
33  0.0000   澳门
```

**2.百度指数【爬取时候麻烦一点，需要对每个省单独爬，然后再计算一段时间内的总和排名**

以后可以每隔七天运行一次，每次运行前删除原文件，运行后重新导入数据库

```
keyword,period,index,province
八宝粥,2021-02-15|2021-02-22,1597,山东
八宝粥,2021-02-15|2021-02-22,521,贵州
八宝粥,2021-02-15|2021-02-22,635,江西
八宝粥,2021-02-15|2021-02-22,642,重庆
八宝粥,2021-02-15|2021-02-22,671,内蒙古
八宝粥,2021-02-15|2021-02-22,888,湖北
八宝粥,2021-02-15|2021-02-22,918,辽宁
八宝粥,2021-02-15|2021-02-22,748,湖南
八宝粥,2021-02-15|2021-02-22,866,福建
八宝粥,2021-02-15|2021-02-22,1034,上海
八宝粥,2021-02-15|2021-02-22,1228,北京
八宝粥,2021-02-15|2021-02-22,939,广西
八宝粥,2021-02-15|2021-02-22,1508,广东
八宝粥,2021-02-15|2021-02-22,922,四川
八宝粥,2021-02-15|2021-02-22,665,云南
八宝粥,2021-02-15|2021-02-22,1147,江苏
八宝粥,2021-02-15|2021-02-22,1148,浙江
八宝粥,2021-02-15|2021-02-22,175,青海
八宝粥,2021-02-15|2021-02-22,414,宁夏
八宝粥,2021-02-15|2021-02-22,1179,河北
八宝粥,2021-02-15|2021-02-22,727,黑龙江
八宝粥,2021-02-15|2021-02-22,857,吉林
八宝粥,2021-02-15|2021-02-22,808,天津
八宝粥,2021-02-15|2021-02-22,1004,陕西
八宝粥,2021-02-15|2021-02-22,541,甘肃
八宝粥,2021-02-15|2021-02-22,592,新疆
八宝粥,2021-02-15|2021-02-22,1346,河南
八宝粥,2021-02-15|2021-02-22,880,安徽
八宝粥,2021-02-15|2021-02-22,861,山西
八宝粥,2021-02-15|2021-02-22,475,海南
八宝粥,2021-02-15|2021-02-22,57,台湾
八宝粥,2021-02-15|2021-02-22,173,西藏
八宝粥,2021-02-15|2021-02-22,114,香港
八宝粥,2021-02-15|2021-02-22,0,澳门
```

建表语句

```sql
CREATE TABLE province_index(
	province_id INT AUTO_INCREMENT,
	province VARCHAR(20),
	sum_index VARCHAR(20),
	period VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(province_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
```



##### 百度搜索指数

从最早开始爬、按月显示，可以预测

字段含义：

- date（日期）按月整合，把该月每一天的搜索指数相加
- index（指数）
- 由于我非常菜，因此难以再添加一列关键词，每次爬取时生成带有关键词的文件名，导入数据库时再根据文件名导入关键词

```
date,index
2015-01,77382
2015-02,64444
2015-03,137751
2015-04,80379
2015-05,84204
2015-06,70536
2015-07,75114
2015-08,83938
2015-09,81707
2015-10,81306
2015-11,101651
2015-12,110560
2016-01,76904
2016-02,71664
2016-03,76313
2016-04,73474
2016-05,90066
2016-06,77996
2016-07,81806
2016-08,82633
2016-09,84588
2016-10,104520
2016-11,89818
2016-12,106550
2017-01,77878
2017-02,118045
2017-03,101804
2017-04,72435
2017-05,103174
2017-06,82055
2017-07,90215
2017-08,98125
2017-09,70988
2017-10,81643
2017-11,80264
2017-12,69086
2018-01,68316
2018-02,57454
2018-03,79003
2018-04,72534
2018-05,74434
2018-06,67780
2018-07,87315
2018-08,83199
2018-09,71908
2018-10,78628
2018-11,120829
2018-12,63096
2019-01,57554
2019-02,46954
2019-03,57512
2019-04,55483
2019-05,67343
2019-06,55434
2019-07,62152
2019-08,87928
2019-09,68518
2019-10,60643
```

建表语句

```sql
CREATE TABLE baidu_index(
	index_id INT AUTO_INCREMENT,
	date VARCHAR(20),
	sum_index VARCHAR(40),
	keyword VARCHAR(20),
	PRIMARY KEY(index_id)
	)ENGINE=InnoDB AUTO_INCREMENT=1001 CHARSET=utf8;
```



##### 百度资讯指数（接口失败）



```
               八宝粥
date              
2021-01-01   11062
2021-01-02   23277
2021-01-03    9517
2021-01-04   18057
2021-01-05    5114
2021-01-06    3317
2021-01-07     374
2021-01-08    1237
2021-01-09    1050
2021-01-10     709
2021-01-11     672
2021-01-12     447
2021-01-13     787
2021-01-14     247
2021-01-15   11117
2021-01-16   18714
2021-01-17    6652
2021-01-18   16864
2021-01-19   35582
2021-01-20   30947
2021-01-21   12839
2021-01-22   15562
2021-01-23   40917
2021-01-24   72187
2021-01-25   80627
2021-01-26   17249
2021-01-27    5834
2021-01-28    3454
2021-01-29     817
2021-01-30    2545
2021-01-31    5117
2021-02-01    1022
2021-02-02    1429
2021-02-03    2402
2021-02-04   65744
2021-02-05    4204
2021-02-06    1024
2021-02-07  128999
2021-02-08  189587
2021-02-09   88297
2021-02-10   86149
2021-02-11   35107
2021-02-12   34204
2021-02-13    8287
2021-02-14    4649
```



##### 百度媒体指数（接口失败）



```
           八宝粥
date          
2021-01-01   0
2021-01-02   0
2021-01-03   0
2021-01-04   0
2021-01-05   0
2021-01-06   0
2021-01-07   0
2021-01-08   0
2021-01-09   0
2021-01-10   0
2021-01-11   0
2021-01-12   0
2021-01-13   0
2021-01-14   0
2021-01-15   0
2021-01-16   0
2021-01-17   0
2021-01-18   0
2021-01-19   0
2021-01-20   0
2021-01-21   8
2021-01-22  13
2021-01-23   1
2021-01-24   1
2021-01-25   0
2021-01-26   2
2021-01-27   1
2021-01-28   0
2021-01-29   1
2021-01-30   1
2021-01-31   1
2021-02-01   0
2021-02-02   2
2021-02-03   0
2021-02-04   1
2021-02-05   0
2021-02-06   0
2021-02-07   0
2021-02-08   0
2021-02-09   0
2021-02-10   0
2021-02-11   0
2021-02-12   0
2021-02-13   0
2021-02-14   0
```



##### 头条相关性分析【pass】

字段含义：

- relation_word（相关词）
- relation_score  （相关性值）
- searchhot【这个字段无】【中间好像还有挺多没爬出来？
- score_rate（搜索比率？） 
- score_rate_rank（相关性排名）

```
   relation_word  relation_score  ... score_rate score_rate_rank
0             饮料        0.001282  ...      2.43%              30
1             莲子        0.001471  ...          新              11
2             盖子        0.001656  ...          新               7
3             勺子        0.001743  ...          新               5
4            矿泉水        0.003215  ...      1.69%              31
5            葡萄干        0.002308  ...    -16.86%              37
6            八宝饭        0.001124  ...     -6.21%              34
7            让一让        0.002950  ...     29.50%              25
8             一罐        0.002120  ...      8.21%              29
9             粘稠        0.003169  ...     17.20%              27
10            熬粥        0.001640  ...          新               9
11           防腐剂        0.001146  ...          新              14
12            罐装        0.004988  ...    109.57%              20
13            铁轨        0.001262  ...    -16.57%              36
14           腊八节        0.003752  ...     -0.13%              33
15          营养早餐        0.001299  ...          新              12
16           黑米粥        0.002061  ...     53.39%              22
17           娃哈哈        0.003643  ...    -26.55%              39
18           牛肉干        0.001631  ...     38.60%              24
19           腊八粥        0.004538  ...          新               2
20            粥店        0.001955  ...     53.41%              21
21           木糖醇        0.002783  ...    131.08%              19
22           杂粮粥        0.001857  ...          新               4
23           喜多多        0.010171  ...          新               1
24            早会        0.001077  ...     -7.88%              35
25            拉环        0.001837  ...     47.73%              23
26           冰红茶        0.003979  ...      0.82%              32
27           营养粥        0.003356  ...     17.58%              26
28            银鹭        0.002728  ...          新               3
29          三斤四两        0.001644  ...          新               8
30           灌装机        0.001573  ...          新              10
31          功能比较        0.001073  ...          新              18
32           花生奶        0.001682  ...          新               6
33           儿童奶        0.002825  ...     15.20%              28
34           紫薯粥        0.001087  ...          新              17
35           荷叶粥        0.001145  ...    -23.56%              38
36          灌汤蒸饺        0.001138  ...          新              15
37         美味八宝粥        0.001154  ...          新              13
38          保温时间        0.001129  ...          新              16
```



##### 头条城市分布



```
       value name
0   0.022524   北京
1   0.020296   上海
2   0.020211   西安
3   0.017021  石家庄
4   0.016944   天津
5   0.014905   济南
6   0.014746   青岛
7   0.012948   重庆
8   0.012929   郑州
9   0.012576   广州
10  0.012386   武汉
11  0.012355   深圳
12  0.012123   成都
13  0.011871   沈阳
14  0.011522   潍坊
15  0.011471   临沂
16  0.009902   苏州
17  0.009766   烟台
18  0.009724   南京
19  0.009305   保定
20  0.009042   杭州
21  0.008716   大连
22  0.008232   济宁
23  0.007883   合肥
24  0.007453   南阳
25  0.006573   唐山
26  0.006240   太原
27  0.005705   邯郸
28  0.004379  哈尔滨
29  0.004290  张家口
```



##### 搜狗指数【pass】

```
       date  index
0  20210214   6190
1  20210215   5273
2  20210216   5371
3  20210217   6077
4  20210218   5566
5  20210219   6030
6  20210220   5068
```



#### 评论分析

细分市场，提取出关注的点

##### **三维立体图**

z轴销量（问题：评论数并不是销量，没有办法拿到销量）

x轴价格（规格不一样）

y轴质量分数（使用评论分析分数）【暂时用之前的模型】

**解决方案：**

取最近同一段时间内的评论数量作为销量（例如1月或者12月的）

好麻烦啊这个，感觉不太能复现，首先因为竞品是人工筛选出来的（因为京东直接搜产品名称会出现很多重复的商品），然后评论和商品基本内容（价格）是分开来爬的，还需要进行sku匹配。然后商品对应竞品的关系又是存在redis里面的，因此每次更改竞品的时候还得去redis里面改。

==究极问题：到底什么样的才算是竞品？竞品如果现在是我们人工筛选的话，那到时候是由企业自己来界定吗？还有评论自己写的爬虫爬不完整，是否还要继续完善到能够放在服务器上？==

建表语句：

```sql
create table 3d_score (
	sku_id VARCHAR(20) NOT NULL,
	brand VARCHAR(20) NOT NULL,
	comment_score VARCHAR(30) NOT NULL,
	price VARCHAR(20) NOT NULL,
	count VARCHAR(20) NOT NULL,
	PRIMARY KEY ( sku_id )
 )ENGINE=InnoDB CHARSET=utf8;
```



##### **时间序列分析**

目前的评论大概有去年9月到1月的，可以对每个月份的评论进行打分以及关键词提取【评论数量少就硬来，中小企业哪有什么评论（咆哮！！！！！】

折线图

**进行预测**

时间序列预测较难，数据量较小，分数变化相差不大。而且由于之前是按最近时间爬取的，所以有些商品较前面的月份评论数非常少。如果要做的话需要重新爬取。

如果使用销量的话，京东没有销量信息，淘宝才有，但淘宝爬的话也爬不到历史月销量。

**第二种方案：（采用）**

造数据，销量时间序列，进行预测，证明我们有这个模型能力。目前GitHub较多这方面的项目，感觉他们的数据也是比赛提供的。

资料：

- https://github.com/yangwohenmai/LSTM



##### **情感分析【这个要啥图啊？】【可以再问问老师】**

评论标注，bert-ner，评论**实体抽取**，词向量聚类后提取出维度关键词。折线、雷达图、词云图。

**细分**：分正面情感和负面情感关键词抽取

资料：

- https://github.com/EliasCai/viewpoint-mining
- https://github.com/heyangHEY/BERT-CRF
- https://github.com/macanv/BERT-BiLSTM-CRF-NER
- https://github.com/BrikerMan/Kashgari
- https://github.com/GlassyWing/bi-lstm-crf  分词、标注模型
- https://github.com/zhpmatrix/nlp-competitions-list-review  比赛复盘
- https://github.com/yuanxiaosc/BERT-for-Sequence-Labeling-and-Text-Classification  BERT进行序列标注和文本分类的模板代码
- https://github.com/boat-group/fancy-nlp   **Fancy-NLP** 是由腾讯商品广告策略组团队构建的用于建设商品画像的文本知识挖掘工具，其支持诸如实体提取、文本分类和文本相似度匹配等多种常见 NLP 任务。使用需要引用
- https://github.com/yongzhuo/Macadam Macadam是一个以Tensorflow(Keras)和bert4keras为基础，专注于文本分类、序列标注和关系抽取的自然语言处理工具包。



#### 公司信息

##### 竞品对应重新爬取

现有的竞品公司是否要重新爬取？

##### 公司资讯

百度爬取

```
company,news,news_url
老干妈,茅台女总工入围院士候选人，网友称“老干妈”陶华碧也具备资格,https://new.qq.com/omn/20210218/20210218A04J3E00.html
泰奇食品,家豪食品、泰奇食品“失意” 三批次产品被检不合格,https://www.163.com/dy/article/G0HM78910525CR59.html
```

