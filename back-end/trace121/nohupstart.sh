cd "$(dirname "$0")"
nohup mvn spring-boot:run > trace.out 2>&1 &
#还未写日志路径
#nohup mvn spring-boot:run >> /log.txt 2>&1 &