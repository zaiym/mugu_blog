nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-user-boot -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms200m -Xmx200m blog-user-boot.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-auth-server -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms200m -Xmx200m blog-oauth-server.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-picture -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms100m -Xmx100m blog-picture.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-gateway -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms400m -Xmx400m blog-gateway.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-friendlinks -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms200m -Xmx200m blog-friendlinks.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-file-server -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms200m -Xmx200m blog-file-server.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-comments -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms400m -Xmx400m blog-comments.jar &

nohup java  -Dfile.encoding=utf-8 -javaagent:E:\mugu_blog\apache-skywalking-apm-bin-es7\agent\skywalking-agent.jar -Dskywalking.agent.service_name=blog-article -Dskywalking.collector.backend_service=47.111.0.135:8854 -jar -Xms500m -Xmx500m blog-article.jar &