# Java FabIndustry


### FabIndustry.java

|Transaction|参数|返回值|用途|
|----|----|----|----|
|initLedger|ctx|void|初始化存入测试值|
|queryAllInfo|ctx|String，合约内所有信息|返回合约内所有信息|
|queryInfoByID|ctx, String id|String|返回指定id对应的工序|
|procedureStart|ctx, String id, name, inname, inmaster, long time|String|接收id、流程名、流程负责人、工序名、工序负责人、当前时间，表示工序开始，并返回当前流程内该id对应所有信息|
|procedureEnd|ctx, String id, name, inname, inmaster, long time|String|接收id、流程名、流程负责人、工序名、工序负责人、当前时间，表示工序结束，并返回当前流程内该id对应所有信息|
