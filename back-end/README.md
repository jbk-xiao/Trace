### 1. 代码结构
后端代码包括，实现业务逻辑的springboot项目：trace121模块和trace192模块；区块链相关文件：blockchain目录下代表智能合约（链码）的chaincode目录和配置文件config目录。

```java
back-end/
├── blockchain			// 区块链相关文件
│   ├── chaincode		// 区块链智能合约
│   │   ├── fabmedia	// fabmedia合约，用于存储非结构化的媒体数据
│   │   │   ├── build.gradle	// gradle项目的依赖配置中心
│   │   │   ├── config			// 样式检查相关配置文件
│   │   │   │   └── checkstyle
│   │   │   │       ├── checkstyle.xml
│   │   │   │       └── suppressions.xml
│   │   │   ├── gradle			// 使用默认的gradle包装器
│   │   │   │   └── wrapper
│   │   │   │       ├── gradle-wrapper.jar
│   │   │   │       └── gradle-wrapper.properties
│   │   │   ├── gradlew			// Linux下gradle启动脚本
│   │   │   ├── gradlew.bat		// Windows下gradle启动脚本
│   │   │   ├── settings.gradle	// gradle项目设置文件
│   │   │   └── src				// 智能合约源文件
│   │   │       ├── main
│   │   │       │   └── java
│   │   │       │       └── com
│   │   │       │           └── trace
│   │   │       │               └── fabric
│   │   │       │                   └── fabtrace
│   │   │       │                       ├── FabMedia.java
│   │   │       │                       ├── MediaInfo.java
│   │   │       │                       └── MediaQueryResult.java
│   │   │       └── test
│   │   └── fabtrace	// fabtrace合约，用于存储结构化的溯源文本信息
│   │       ├── build.gradle
│   │       ├── config
│   │       │   └── checkstyle
│   │       │       ├── checkstyle.xml
│   │       │       └── suppressions.xml
│   │       ├── gradle
│   │       │   └── wrapper
│   │       │       ├── gradle-wrapper.jar
│   │       │       └── gradle-wrapper.properties
│   │       ├── gradlew
│   │       ├── gradlew.bat
│   │       ├── README.md
│   │       ├── settings.gradle
│   │       └── src
│   │           └── main
│   │               ├── java
│   │               │   └── com
│   │               │       └── trace
│   │               │           └── fabric
│   │               │               └── fabtrace
│   │               │                   ├── datatype
│   │               │                   │   ├── ProcedureInfo.java
│   │               │                   │   ├── ProcessInfo.java
│   │               │                   │   ├── TraceInfo.java
│   │               │                   │   └── TraceManagerInfo.java
│   │               │                   └── FabTrace.java
│   │               └── resources
│   │                   └── initLedger.json
│   └── config		// 区块链网络配置文件
│       ├── configtx.yaml
│       ├── core.yaml
│       └── orderer.yaml
├── trace121			// springboot表示层模块
│   ├── nohupstart.sh	// 模块后台运行启动脚本
│   ├── pom.xml			// maven项目对象模型文件，指明项目信息及依赖导入
│   ├── shutdown.sh		// 模块关闭脚本
│   └── src
│       ├── main
│       │   ├── java	//  java源码
│       │   │   └── com
│       │   │       └── trace
│       │   │           └── trace
│       │   │               ├── config	//配置文件包，包括grpc应用配置和反向代理工具的配置
│       │   │               │   ├── SolrProxyServletConfiguration.java
│       │   │               │   └── GrpcConfig.java
│       │   │               ├── controller	// 用于接收前端请求的控制器所在包
│       │   │               │   ├── ChartsController.java
│       │   │               │   ├── InfoController.java
│       │   │               │   ├── ManagerController.java
│       │   │               │   ├── MediaController.java
│       │   │               │   └── TraceController.java
│       │   │               └── Trace121Application.java	// trace121模块启动主类
│       │   ├── proto	// proto源码，用于配置grpc
│       │   │   └── SearchService.proto
│       │   └── resources
│       │       └── application.yml	// springboot配置文件
│       └── test
├── trace192			// springboot业务逻辑层和表示层模块
│   ├── nohupstart.sh	// 模块后台运行启动脚本
│   ├── pom.xml			// maven项目对象模型文件，指明项目信息及依赖导入
│   ├── shutdown.sh		// 模块关闭脚本
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── com
│       │   │       └── trace
│       │   │           └── trace
│       │   │               ├── config	// 模块配置导入
│       │   │               │   ├── MongoConfig.java	// 配置mongodb连接池
│       │   │               │   ├── RedisConfig.java	// 导入redis配置
│       │   │               │   └── RedisIndexConfig.java	// 导入redis数据库索引
│       │   │               ├── controller
│       │   │               │   └── SaveMediaController.java	// 后台监控访问接口
│       │   │               ├── dao		// 数据库访问层文件
│       │   │               │   ├── ChartsMongoDao.java
│       │   │               │   ├── ChartsRedisDao.java
│       │   │               │   ├── CompetRedisDao.java
│       │   │               │   ├── FabricDao.java
│       │   │               │   ├── MongoDao.java
│       │   │               │   ├── ProductRedisDao.java
│       │   │               │   ├── RedisDao.java
│       │   │               │   └── TraceRedisDao.java
│       │   │               ├── entity
│       │   │               │   ├── AgeOrSexDistributionData.java
│       │   │               │   ├── AllCompetinfo.java
│       │   │               │   ├── AllInfo.java
│       │   │               │   ├── Comment.java
│       │   │               │   ├── Comment_score.java
│       │   │               │   ├── CompanyInfo.java
│       │   │               │   ├── Compet_geo.java
│       │   │               │   ├── Compet.java
│       │   │               │   ├── Detail.java
│       │   │               │   ├── Graph.java
│       │   │               │   ├── JDdetail.java
│       │   │               │   ├── ProvinceIndexData.java
│       │   │               │   ├── Query.java
│       │   │               │   ├── Relate.java
│       │   │               │   ├── RelateSearchData.java
│       │   │               │   └── S3dScore.java
│       │   │               ├── mapper	// mybatis对应mapper文件
│       │   │               │   ├── ChartsMapper.java
│       │   │               │   ├── CompetMapper.java
│       │   │               │   ├── DetailMapper.java
│       │   │               │   └── QueryMapper.java
│       │   │               ├── pojo
│       │   │               │   ├── AgeOrSexDistribution.java
│       │   │               │   ├── FabMediaInfo.java
│       │   │               │   ├── ProcedureInfo.java
│       │   │               │   ├── ProcessInfo.java
│       │   │               │   ├── ProvinceIndex.java
│       │   │               │   ├── RelateSearch.java
│       │   │               │   ├── TraceInfo.java
│       │   │               │   └── TraceManagerInfo.java
│       │   │               ├── service	// 业务逻辑层文件
│       │   │               │   ├── ManageProducts.java
│       │   │               │   ├── ManagerServiceImpl.java	// 企业后台管理模块业务逻辑
│       │   │               │   ├── SearchCharts.java
│       │   │               │   ├── SearchChartsServiceImpl.java	// 营销分析图谱业务逻辑
│       │   │               │   ├── SearchCompet.java
│       │   │               │   ├── SearchGraph.java
│       │   │               │   ├── SearchProduct.java
│       │   │               │   ├── SearchServiceImpl.java	// 检索模块业务逻辑
│       │   │               │   ├── SearchTrace.java
│       │   │               │   └── SearchTraceServiceImpl.java	// 溯源模块业务逻辑
│       │   │               ├── Trace192Application.java	// trace192模块启动主类
│       │   │               └── util	// 工具类包
│       │   │                   ├── CreateJson.java	// 接收不同格式对象并生成json
│       │   │                   ├── CreateTraceCode.java	// 生成唯一溯源码
│       │   │                   ├── FabricUtil.java		// 区块链连接工具类
│       │   │                   ├── FileUtil.java		// 文件读取工具类
│       │   │                   ├── JedisUtil.java		// redis连接工具类
│       │   │                   ├── QRCodeUtil.java		// 生成二维码
│       │   │                   └── SaveUtil.java		// 文件保存工具类
│       │   ├── proto
│       │   │   └── SearchService.proto
│       │   └── resources
│       │       ├── application-dev.yml		// springboot开发环境配置文件
│       │       ├── application-prod.yml	// springboot生产环境配置文件
│       │       ├── application.yml
│       │       ├── fabric.properties		// 区块链连接属性文件
│       │       ├── log4j.properties		// slf4j配置文件
│       │       ├── mapper		// mybatis数据库访问配置文件
│       │       │   ├── ChartsMapper.xml
│       │       │   ├── CompetMapper.xml
│       │       │   ├── DetailMapper.xml
│       │       │   └── QueryMapper.xml
│       │       └── redis_index.json	// redis数据库索引对照文件
│       └── test
├── mysql初始化.sql  // mysql数据库初始化文件
└── README.md		// 后端项目说明文件

```

### 2. 环境部署

#### 2.1 环境依赖
后端项目部署于Linux服务器上，其中trace121模块位于121.46.19.26主机，该主机具备公网内的联通性；trace192模块位于内网主机上，可被121主机访问到。
环境依赖包括：

| 名称 | 版本号 | 官网      |
| ---- | ------ | ------------------------ |
|Java SE Development Kit(JDK)  | 1.8.0  | https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html |
|Apache Maven Project|3.6.3|https://maven.apache.org/download.cgi|
|Redis|6.0.10|https://redis.io/download|
|mongoDB|3.4.24|https://www.mongodb.com/try/download/community|
|MySQL|5.7.33|https://dev.mysql.com/downloads/mysql/|
#### 2.2 项目启动
分别在`trace121`和`trace192`目录下使用`mvn compile`指令下载依赖并编译源码，而后分别运行`sh nohupstart.sh`脚本启动项目。
#### 2.3 项目关闭
分别在`trace121`和`trace192`目录下运行`sh shutdown.sh`脚本停止项目。

### 3. 区块链环境部署
我们使用的区块链平台是Hyperledger Fabric，目前搭建出的架构包括两个组织Org1、Org2和一个排序服务，三者各拥有一个节点。Org1模拟第三方认证机构，即本团队，Org2模拟加盟企业。


具体部署过程如下：
#### 3.1 环境准备
docker，版本：20.10.3

docker-compose，版本：1.27.4

相关docker镜像，包括Hyperledger Fabric项目下的：
|名称|版本|
|----|----|
|fabric-javaenv|2.2.0|
|fabric-tools|2.2.0|
|fabric-peer|2.2.0|
|fabric-orderer|2.2.0|
|fabric-ccenv|2.2.0|
|fabric-baseos|2.2.0|
|fabric-ca|1.4.7|
和状态数据库
|名称|版本|
|----|----|
|couchdb|3.1.1|

Hyperledger Fabric所需二进制文件，放置在`bin`目录下，包括：
|名称|版本|
|----|----|
|configtxgen|2.2.0|
|configtxlator|2.2.0|
|cryptogen|2.2.0|
|discover|2.2.0|
|idemixgen|2.2.0|
|orderer|2.2.0|
|peer|2.2.0|
|fabric-ca-client|1.4.7|
|fabric-ca-server|1.4.7|
为便于快速部署，同时我们也准备好Hyperledger Fabric官方提供的测试网络相关文件，包括：https://github.com/hyperledger/fabric-samples/tree/master/test-network 目录下所有文件，即整个test-network目录。
#### 3.2 初始化配置
假定我们工作空间的路径为`workspace`，则上一步中所提到的`bin`目录和`test-network`目录，以及最开始提到的`blockchain`目录下的`chaincode`目录和`config`目录都位于`workspace`目录下。至此，启动区块链网络所需的二进制文件位于`bin`目录下，配置文件位于`config`目录下，链码（智能合约）文件位于`chaincode`目录下，节点配置文件及简化过的启动脚本位于`test-network`目录下。
#### 3.3 启动网络
在`test-network`目录下依次运行：
```shell
sh network.sh up	# 启动网络，包含Org1、Org2、排序服务三个组织
sh network.sh createChannel -c mychannel	# 在三个组织启动名为mychannel的通道
sh network.sh deployCC -c mychannel -ccn fabmedia -ccp ../chaincode/fabmedia -ccl java -cci initLedger	# 部署fabmedia链码
sh network.sh deployCC -c mychannel -ccn fabtrace -ccp ../chaincode/fabtrace -ccl java -cci initLedger	# 部署fabtrace链码
```

### 4. 配置说明
以下列出的是spring项目分别在两台服务器上的配置情况。
#### 4.1 trace121配置
application.yml
```yaml
management:
  endpoint:
    shutdown:
      enabled: true			# 启动actuator（spring管理功能）的shutdown端点
  endpoints:
    web:
      exposure:
        include: "*"			# 启动actuator的其它所有端点
      base-path: /MyActuator	# 设置actuator服务的基本路径
  server:
    port: 12584			# 设置actuator服务监听的端口
    address: 127.0.0.1	# 设置端点可用的地址，此处表示仅可本地访问

server:
  port: 8511			# 设置springboot服务监听的端口

app-config:
  grpc-server-name: 222.200.184.74	# 设置grpc服务端的地址，结合端口定位到trace192模块的grpc服务器上
  grpc-server-port: 8289			# 设置grpc服务端的端口
  max-message-size: 1073741824		# 设置grpc通讯可传输的最大文件大小
```
#### 4.2 trace192配置
application.yml
```yaml
spring:
  profiles:
    active: prod  # 用于指定配置文件，dev用于开发环境，prod用于生产环境
```
由于开发环境与生产环境配置项一样，仅具体参数不同，因此这里只详细说明开发环境的配置文件。
application-prod.yml
```yaml
# 生产环境配置文件
management:
  endpoint:
    shutdown:
      enabled: true			# 启动actuator（spring管理功能）的shutdown端点
  endpoints:
    web:
      exposure:
        include: "*"			# 启动actuator的其它所有端点
      base-path: /MyActuator	# 设置actuator服务的基本路径
  server:
    port: 12583			# 设置actuator服务监听的端口
    address: 127.0.0.1	# 设置端点可用的地址，此处表示仅可本地访问

spring:
  datasource:			# 设置spring项目mysql的数据源
    username: root		# 设置用于连接mysql的用户名
    password: ******	# 设置用于连接mysql的密码
    url: jdbc:mysql://localhost:3306/trace?useUnicode=true&characterEncoding=UTF-8	# 设置连接mysql的url
    driver-class-name: com.mysql.cj.jdbc.Driver		# 设置mysql的连接驱动
    type: com.alibaba.druid.pool.DruidDataSource	# 设置druid连接池
  data:
    mongodb:	# 设置mongodb连接参数
      uri: mongodb://localhost:27017/trace?maxpoolsize=150&minpoolsize=3
  redis:				# 设置redis连接参数
    host: localhost		# 设置redis服务器地址
    port: 6579			# 设置redis服务器端口
    jedis:
      pool:
        min-idle: 5		# 设置jedis连接池最小空闲数
        max-idle: 100	# 设置jedis连接池最大空闲数
    password: ******	# 设置redis连接密码
  application:
    name: hello-grpc-server	# 设置springboot应用服务名称

grpc:
  port: 8289		# 指定grpc服务监听的端口号

server:
  port: 8290		# 指定springboot服务监听的端口号

mybatis:			# 指定mybatis映射文件的位置
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.trace.trace.entity

media:				# 指定媒体文件存储位置及其索引存放位置
  video:
    path: /data/st01/monitor_files
    database: 13
  picture:
    path: /data/st01/monitor_files
    database: 14

fabric:	#这一部分是连接区块链架构Hyperledger Fabric的相关配置
  system:
    host: localhost				# 指定fabric网关的地址
    port: 7054					# 指定fabric网关的端口
    user: appUser				# 指定连接fabric使用的一般用户
    wallet: /data/st01/wallet	# 指定连接区块链网络用户身份凭据的存放位置
    # 指定连接区块链网关属性文件所在位置
    properties: /data/st01/trace192/src/main/resources/fabric.properties
    # 指定连接区块链网关私钥文件所在位置
    ca-pem: /data/st01/myfabric/simple-fabric/test-network/organizations/peerOrganizations/org1.example.com/ca/ca.org1.example.com-cert.pem
    # fabric网络启动时生成的网络连接配置文件
    conpath: /data/st01/myfabric/simple-fabric/test-network/organizations/peerOrganizations/org1.example.com/connection-org1.yaml
```
