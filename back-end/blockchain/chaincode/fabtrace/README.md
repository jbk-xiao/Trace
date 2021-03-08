# Java FabTrace

全project包含FabTrace和TraceInfo两个类，FabTrace包含所有fabric中的`Transaction`，此处即指产品在不同环节间流通的信息。例如，老干妈辣椒酱**进入菜籽油生产地**，经过一段时间后**流出菜籽油生产地**即是两次Transaction。TraceInfo包含每一次`Transaction`中的具体信息，是一个与数据库对接的数据类型对象。
### TraceInfo.java
| 成员变量 | 类型 | 含义 |
| ---- | ---- | ---- |
|id|String|识别一批商品的唯一批号，目前暂时以表示时间的long后边加4位随机整数代替|
|process|ArrayList\<ProcessInfo\>，其中`ProcessInfo`是一个内部类|对应id商品在整个生产过程中的流转信息列表|

|方法|返回值|用途|
|----|----|----|
|public TraceInfo(final String id)|构造方法|使用批号初始化新的一批产品的记录|
|String id,String name,String master,boolean enter,long time|构造方法|使用批号和一个新的ProcessInfo信息初始化新一批产品|
|final String id, final ProcessInfo info|同上|同上|
|getter、toString|略|略|
|addProcessInfo|void|依据传入的参数新建一个ProcessInfo的对象并添加到process列表中|
#### 内部类static final class ProcessInfo
|成员变量|类型|含义|
|---|---|---|
|name|String|流程名称，如菜籽油生产地|
|master|String|流程负责人|
|enter|boolean|true表示产品进入该流程，false表示产品离开该流程|
|time|long|事件发生时间|
### FabTrace.java
实现了ContractInterface接口用于完成智能合约中的交易流程。（接口中的方法全为defaut，因此此处未出现任何Override）

| 成员变量 | 类型 | 含义 |
| ---- | ---- | ---- |
|gson|Gson|用于处理json转化|
|FabTraceError|enum|标记报错类型|
|InitData|enum(long)|处理initLedger中魔术数字问题|

|Transection（其中Context的对象ctx为必有参数，意为智能合约中当前世界，调用时不用声明该参数）|返回值类型（实质上只有void和String）|用途|
|---|---|---|
|initLedger(ctx)|void|初始化链码，存入若干条示例记录|
|objectIn(ctx,id,name,master,time)|String|为id批次的商品增加一条流入记录并返沪这条id下的所有记录|
|objectOut(ctx,id,name,master,time)|String|为id批次的商品增加一条流出记录并返沪这条id下的所有记录|
|queryInfoByID(ctx,id)|String|查询指定id批次商品的记录并返回|
|queryAllInfo(ctx)|String|返回当前合约中所有记录|