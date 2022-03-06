
UI: http://47.111.0.135:8850/

https://blog.csdn.net/weixin_38087443/article/details/102651384

docker pull kibana:7.1.1

docker run --name kibana -e ELASTICSEARCH_URL=http://47.111.0.135:8851 -p 8850:5601 -d kibana:7.1.1