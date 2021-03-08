# Java FabTrace

全project包含FabTrace类和datatype包，FabTrace包含所有fabric中的`Transaction`，此处即指在溯源过程中需要对区块链进行的所有操作。datatype中为实体类(但也有部分逻辑)
### FabTrace.java
实现了ContractInterface接口用于完成智能合约中的交易流程。（接口中的方法全为defaut，因此此处未出现任何Override）

| 成员变量 | 类型 | 含义 |
| ---- | ---- | ---- |
|gson|Gson|用于处理json转化|
|FabTraceError|enum|标记报错类型|
|InitData|enum(long)|处理initLedger中魔术数字问题|

|Transection（其中ctx的对象context为必有参数，意为智能合约中当前世界，调用时不用声明该参数）|返回值类型（实质上只有void和String）|用途|
|---|---|---|
|initLedger(ctx)|void|初始化链码，存入若干条示例记录|
|createFood(ctx,id,foodName...)|String|依据传入的食品基本信息新建一条记录|
|addProcess|String|为指定id对应的商品信息增加一条ProcessInfo|
|addProcedure|String|为指定id对应的商品信息增加一条工序记录|
|queryInfoByID(ctx,id)|String|查询指定id批次商品的记录并返回|
|queryInfosByIDs(ctx,ids)|String|查询若干id批次商品的记录并返回，暂未使用|
|managerQueryInfos|String|查询若干id批次商品的记录，返回TraceManagerInfo的List对应json|
|queryHistoryByID|String|测试，暂时无用|
|queryInfoByEndRange(ctx,endID)|String|依据结束范围查询一系列id对应的状态，暂未使用|
|queryInfoByRange(ctx,start,end)|String|依据开始和结束查询一系列id对应的状态，暂未使用|
|queryAllInfo(ctx)|String|返回当前合约中所有记录|

### com.trace.fabric.fabtrace.datatype

#### TraceInfo.java
| 成员变量 | 类型 | 含义 |
| ---- | ---- | ---- |
|id|String|识别一批商品的唯一批号，13位产品code+12位时间|
|foodName|String|食品名称,如油辣椒酱|
|specification|String|食品规格,如275g|
|category|String|食品品类,如辣椒酱|
|processCount|Integer|生产所需总步骤数|
|process|ArrayList\<ProcessInfo\>，其中`ProcessInfo`是一个内部类|对应id商品在整个生产过程中的流转信息列表|

|方法|返回值|用途|
|----|----|----|
|public TraceInfo|构造方法|使用批号和产品基本信息初始化新的一批产品的记录|
|getter、toString|略|略|
|addProcessInfo|void|依据传入的参数新建一个ProcessInfo的对象并添加到process列表中|

#### ProcessInfo.java

|成员变量|类型|含义|
|---|---|---|
|name|String|流程名称，如菜籽油生产地|
|master|String|流程负责人|
|time|String|流程开始时间|
|location|String|流程所在城市|
|picture|String|最新图片,此处留空|
|procedure|List\<ProcedureInfo\>|内部工序信息|

|方法|返回值|用途|
|----|----|----|
|getter、toString|略|略|
|addProcedureInfo|void|依据传入的参数新建一个ProcedureInfo的对象并添加到procedure列表中|

#### ProcedureInfo.java

|成员变量|类型|含义|
|----|----|----|
|name|String|工序名称|
|master|String|工序负责人|
|time|String|开始时间|
|picture|String|图片url,留空|

#### TraceManagerInfo.java
用于在管理员查询所有信息时返回,作为泛型参数使用在List中。

|成员变量|类型|含义|
|----|----|----|
|id|String|唯一溯源码|
|latestProcess|String|最新进行至的步骤|
|time|String|latestProcess对应的开始时间|