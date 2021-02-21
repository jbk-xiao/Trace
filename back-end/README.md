### 接口情况

| controller| 示例Url                                                      | 参数     | 返回值                                                     | 备注 |
| ------------------------- | ------------------------------------------------------------ | -------- | ---------------------------------------------------------- | ---- |
| /getPicture/{filename:.+} | http://121.46.19.26:8511/getPicture/01-20210130092923-13.jpg | 图片名称 | 直接返回HttpServletResponse，用\<img src="url"\>可以接收。 |      |
| /getVideo/{filename:.+} | http://121.46.19.26:8511/getVideo/01-20210130092923.mkv | 视频名称 | 直接返回HttpServletResponse，用\<video\> \<source src="url"\>\</video\>可以接收。 |firefox、chrome、edge测试通过，IE不行。目前测试过传输200m大小的视频|
| /getRecentEvent/{process_name}/{page} | http://121.46.19.26:8511/getRecentEvent/product/1 | process_name：生产流程中某个步骤；page：每15条一页 | 返回15个表示标准时间的整数组成的list。第1页是该生产线上最近的15条视频录制的时间（或者15张图片拍摄的时间，图片与视频是一一对应的）。 |由于没有提前商量，不确定时间的传输方式，暂时使用长整型（即1970.1.1至今的时间）。图片和视频的名称需要使用到这个时间（前端展示可能也需要？），所以这里需要改一个合适点的格式。|
| /getCompet/{regis_id} | http://121.46.19.26:8511/getCompet/440108400003939 | 主公司的工商注册号 | 返回竞品模块的主公司基本信息、竞品公司基本信息和地理信息、主公司商品信息、竞品商品信息 ||
| /getCommodity/{query}/{page} | http://121.46.19.26:8511/getCommodity/糖果/1 | 查询商品的检索词、要获取的商品页的页码 | 返回对应的检索结果，即对应页码的20条商品的skuId、标题、商店、价格、京东连接url、图片url、评价、类别等||
|/getOrigin/{origin_id}|http://121.46.19.26:8511/getOrigin/16119701634150000|依据唯一溯源码查询溯源信息|json字符串，嵌套了两层内部类，见示例|暂时尚未与图片视频建立关联，待定。|
| /getGraphByKind/{kind} | http://121.46.19.26:8511/getGraphByKind/冷冻食品 | 某一领域的关键词 | 返回该领域中主要的相关食品品牌信息，用于知识图谱的展示。 ||
| /getGraphByBrand/{brand} | http://121.46.19.26:8511/getGraphByBrand/湾仔码头 | 某一品牌的名称 | 返回品牌在所属领域中的竞品信息，用于知识图谱的展示。 ||
|/addProcess||POST请求接收四个参数：id=123456 &name=hahaha &master=lalala &location=lalala|返回更改后的溯源信息字符串或是报错信息。和/getOrigin返回结果同格式，但picture字段为空||
|/addFirstProcess||POST请求，接收foodType:油辣椒酱-275g-辣椒酱，com:公司名称，processCount:步骤数，name:第一个process的名称，master:第一个process负责人的名称，location:第一个工序所在城市|同上||
|/addProcedure||POST请求接收三个参数：id=123456 &name=hahaha &master=lalala| 同上                                                         ||
|/getAllTraceInfo/{regis_id}/{product_name}/{page}|http://121.46.19.26:8511/getAllTraceInfo/440108400003939/原味八宝粥-370g-速食粥/1|公司工商注册号regis_id、商品名称product_name、页码page| 返回总页码pageCount以及溯源列表信息（溯源码id、产品名称foodname、规格specification、分类category、最新流程latestProcess、时间time） |
|/getFirstProcessInfo/{regis_id}||公司工商注册号regis_id| 该公司的公司基本信息以及公司所有商品名称列表 |第一次流程获取的信息|

