# monika
a framework to monitor real time data flow，it's based on kafka、spark and redis
# 简介
本项目是基于spark streaming 开发的应用层作业框架，旨在通过简单的配置实现实时消息流监控功能。
目前只支持监控业务，不支持其他的实时处理业务。
# 功能清单-20160706
支持的消息格式：json <br>
支持的监控任务类型：简单任务，比较型任务，自定义<br>
支持的监控任务格式：key-value（value为json） <br>
支持的监控结果存储：redis <br>
支持的全量存储：无 <br>
支持的kafka-streaming类型：direct、receiver
