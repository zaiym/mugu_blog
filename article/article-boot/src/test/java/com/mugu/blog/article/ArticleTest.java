package com.mugu.blog.article;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.mugu.blog.article.common.model.po.Article;
import com.mugu.blog.article.common.model.req.ArticleListReq;
import com.mugu.blog.article.dao.ArticleMapper;
import com.mugu.blog.article.mq.config.RabbitMqConfig;
import com.mugu.blog.article.es.dao.ArticleRepository;
import com.mugu.blog.article.es.model.ArticleEs;
import com.mugu.blog.article.mq.model.ArticleMq;
import com.mugu.blog.article.service.SendMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ArticleTest {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private SendMsgService sendMsgService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

//    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void test7(){
        articleRepository.deleteByArticleId("552893733540986880");
        System.out.println(100);
    }

    @Test
    public void test6() {
        String keyword="spring";
        //根据一个值查询多个字段  并高亮显示  这里的查询是取并集，即多个字段只需要有一个字段满足即可
        //需要查询的字段
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", keyword))
                .should(QueryBuilders.matchQuery("content", keyword));
        //构建高亮查询
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title")
                        , new HighlightBuilder.Field("content"))
                .withHighlightBuilder(new HighlightBuilder().preTags("<span style='color:red'>").postTags("</span>"))
                .build();

        AggregatedPage<ArticleEs> aggregatedPage = elasticsearchTemplate.queryForPage(searchQuery, ArticleEs.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {

                // TODO Auto-generated method stub
                List<T> chunk = new ArrayList<T>();

                for (SearchHit searchHit : response.getHits().getHits()) {
                    chunk.add((T)mergeResult(searchHit));
                }

                AggregatedPage<T> result=new AggregatedPageImpl<T>(chunk,pageable,response.getHits().getTotalHits());

                return result;
            }

            @Override
            public <T> T mapSearchHit(SearchHit searchHit, Class<T> type) {
                return null;
            }
        });

        List<ArticleEs> content = aggregatedPage.getContent();
        System.out.println(content);
    }


    public ArticleEs mergeResult(SearchHit hit){
        ArticleEs articleEs = JSON.parseObject(hit.getSourceAsString(), ArticleEs.class);
        Map<String, HighlightField> highlightFields = hit.getHighlightFields();
        highlightFields.forEach((field,value)->{
            if (StrUtil.equals("title",field)){
                articleEs.setTitle(value.getFragments()[0].toString());
            }else if (StrUtil.equals("content",field)){
                articleEs.setContent(value.getFragments()[0].toString());
            }
        });
        return articleEs;
    }

    /**
     * 根据搜索结果创建esdoc对象
     * @param smap
     * @param hmap
     * @return
     */
    private ArticleEs createEsDoc(Map<String, Object> smap,Map<String, HighlightField> hmap){
        ArticleEs ed = new ArticleEs();
        if (smap.get("title") != null)
            ed.setTitle(smap.get("title").toString());
        if (hmap.get("content") != null)
            ed.setContent(hmap.get("content").fragments()[0].toString());
        else if(smap.get("content")!=null)
            ed.setContent(smap.get("content").toString());
        if (smap.get("id") != null)
            ed.setId(smap.get("id").toString());
        return ed;
    }

    @Test
    public void test4(){
        List<Article> list = articleMapper.pageList(new ArticleListReq());
        for (Article article : list) {
            ArticleMq articleMq = new ArticleMq();
            BeanUtils.copyProperties(article,articleMq);
            articleMq.setFlag(1);
            //TODO 暂时使用同步方式发送消息
            sendMsgService.sendMsg(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.ROUTING_KEY, JSON.toJSONString(articleMq));
        }
    }

    @Test
    public void test3(){
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME,RabbitMqConfig.ROUTING_KEY,"ddddddd",new CorrelationData("1111"));
        rabbitTemplate.setConfirmCallback((correlationData, b, s) -> {
            System.out.println(JSON.toJSONString(correlationData));
        });

    }

    @Test
    public void test1(){
        ArticleEs articleEs = new ArticleEs();
        articleEs.setId("13");
        articleEs.setAuthorId("22L");
        articleEs.setTitle("Feign集成了Ribbon、RestTemplate实现了负载均衡的执行Http调用，只不过对原有的方式（Ribbon+RestTemplate）进行了封装。");
        articleEs.setDescribe("Feign集成了Ribbon、RestTemplate实现了负载均衡的执行Http调用，只不过对原有的方式（Ribbon+RestTemplate）进行了封装。");
        articleEs.setContent("<h2 id=\"h2-1-\"><a name=\"1、前言\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>1、前言</h2><p>前面介绍了Spring Cloud 中的灵魂摆渡者<code>Nacos</code>，和它的前辈们相比不仅仅功能强大，而且部署非常简单。</p>\n" +
                "<p>今天介绍一款服务调用的组件：<code>OpenFeign</code>，同样是一款超越先辈（<code>Ribbon</code>、<code>Feign</code>）的狠角色。</p>\n" +
                "<p>文章目录如下：</p>\n" +
                "<p><img src=\"https://gitee.com/chenjiabing666/BlogImage/raw/master/openFeign/15.png\" alt=\"\"></p>\n" +
                "<h2 id=\"h2-2-feign-\"><a name=\"2、Feign是什么？\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>2、Feign是什么？</h2><p>Feign也是一个狠角色，Feign旨在使得Java Http客户端变得更容易。</p>\n" +
                "<p>Feign集成了Ribbon、RestTemplate实现了负载均衡的执行Http调用，只不过对原有的方式（Ribbon+RestTemplate）进行了封装，开发者不必手动使用RestTemplate调服务，而是定义一个接口，在这个接口中标注一个注解即可完成服务调用，这样更加符合面向接口编程的宗旨，简化了开发。</p>\n" +
                "<p><img src=\"https://gitee.com/chenjiabing666/BlogImage/raw/master/openFeign/1.png\" alt=\"\"></p>\n" +
                "<p>但遗憾的是Feign现在停止迭代了，当然现在也是有不少企业在用。</p>\n" +
                "<p>有想要学习Feign的读者可以上spring Cloud官网学习，陈某这里也不再详细介绍了，不是今天的重点。</p>\n" +
                "<h2 id=\"h2-3-openfeign-\"><a name=\"3、openFeign是什么？\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>3、openFeign是什么？</h2><p>前面介绍过停止迭代的Feign，简单点来说：OpenFeign是springcloud在Feign的基础上支持了SpringMVC的注解，如<code><a href=\"https://github.com/RequestMapping\" title=\"&#64;RequestMapping\" class=\"at-link\">@RequestMapping</a></code>等等。OpenFeign的<code><a href=\"https://github.com/FeignClient\" title=\"&#64;FeignClient\" class=\"at-link\">@FeignClient</a></code>可以解析SpringMVC的<code><a href=\"https://github.com/RequestMapping\" title=\"&#64;RequestMapping\" class=\"at-link\">@RequestMapping</a></code>注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。</p>\n" +
                "<blockquote>\n" +
                "<p>官网地址：<a href=\"https://docs.spring.io/spring-cloud-openfeign/docs/2.2.10.BUILD-SNAPSHOT/reference/html\">https://docs.spring.io/spring-cloud-openfeign/docs/2.2.10.BUILD-SNAPSHOT/reference/html</a></p>\n" +
                "</blockquote>\n" +
                "<h2 id=\"h2-4-feign-openfeign-\"><a name=\"4、Feign和openFeign有什么区别？\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>4、Feign和openFeign有什么区别？</h2><table>\n" +
                "<thead>\n" +
                "<tr>\n" +
                "<th>Feign</th>\n" +
                "<th>openFiegn</th>\n" +
                "</tr>\n" +
                "</thead>\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td>Feign是SpringCloud组件中一个轻量级RESTful的HTTP服务客户端，Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。Feign的使用方式是：使用Feign的注解定义接口，调用这个接口，就可以调用服务注册中心的服务</td>\n" +
                "<td>OpenFeign 是SpringCloud在Feign的基础上支持了SpringMVC的注解，如<a href=\"https://github.com/RequestMapping\" title=\"&#64;RequestMapping\" class=\"at-link\">@RequestMapping</a>等。OpenFeign 的<a href=\"https://github.com/FeignClient\" title=\"&#64;FeignClient\" class=\"at-link\">@FeignClient</a>可以解析SpringMVC的<a href=\"https://github.com/RequestMapping\" title=\"&#64;RequestMapping\" class=\"at-link\">@RequestMapping</a>注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "<h2 id=\"h2-5-\"><a name=\"5、环境准备\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>5、环境准备</h2><p>本篇文章Spring Cloud版本、JDK环境、项目环境均和上一篇Nacos的环境相同：<a href=\"https://mp.weixin.qq.com/s/UHzew6pIl5sRtRfCgBtLMQ\">五十五张图告诉你微服务的灵魂摆渡者Nacos究竟有多强？</a>。</p>\n" +
                "<p>注册中心就不再使用<code>Eureka</code>了，直接使用<code>Nacos</code>作为注册和配置中心，有不会的可以查看Nacos文章。</p>\n" +
                "<p>本篇文章搭建的项目结构如下图：</p>\n" +
                "<p><img src=\"https://gitee.com/chenjiabing666/BlogImage/raw/master/openFeign/image.png\" alt=\"\"></p>\n" +
                "<blockquote>\n" +
                "<p> 注册中心使用<strong>Nacos</strong>，创建个微服务，分别为服务提供者<strong>Produce</strong>，服务消费者<strong>Consumer</strong>。</p>\n" +
                "</blockquote>\n" +
                "<h2 id=\"h2-6-\"><a name=\"6、创建服务提供者\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>6、创建服务提供者</h2><p>既然是微服务之间的相互调用，那么一定会有服务提供者了，创建<code>openFeign-provider9005</code>，注册进入Nacos中，配置如下：</p>\n" +
                "<pre><code class=\"lang-yaml\">server:\n" +
                "  port: 9005\n" +
                "spring:\n" +
                "  application:\n" +
                "    ## 指定服务名称，在nacos中的名字\n" +
                "    name: openFeign-provider\n" +
                "  cloud:\n" +
                "    nacos:\n" +
                "      discovery:\n" +
                "        # nacos的服务地址，nacos-server中IP地址:端口号\n" +
                "        server-addr: 127.0.0.1:8848\n" +
                "management:\n" +
                "  endpoints:\n" +
                "    web:\n" +
                "      exposure:\n" +
                "        ## yml文件中存在特殊字符，必须用单引号包含，否则启动报错\n" +
                "        include: &#39;*&#39;\n" +
                "</code></pre>\n" +
                "<p><strong>注意</strong>：此处的<code>spring.application.name</code>指定的名称将会在openFeign接口调用中使用。</p>\n" +
                "<blockquote>\n" +
                "<p>项目源码都会上传，关于如何注册进入Nacos，添加什么依赖源码都会有，结合陈某上篇Nacos文章，这都不是难事！</p>\n" +
                "</blockquote>\n" +
                "<h2 id=\"h2-7-\"><a name=\"7、创建服务消费者\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>7、创建服务消费者</h2><p>新建一个模块<code>openFeign-consumer9006</code>作为消费者服务，步骤如下。</p>\n" +
                "<h3 id=\"h3-1-\"><a name=\"1、添加依赖\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>1、添加依赖</h3><p>除了Nacos的注册中心的依赖，还要添加openFeign的依赖，如下：</p>\n" +
                "<pre><code class=\"lang-xml\">&lt;dependency&gt;\n" +
                "      &lt;groupId&gt;org.springframework.cloud&lt;/groupId&gt;\n" +
                "      &lt;artifactId&gt;spring-cloud-starter-openfeign&lt;/artifactId&gt;\n" +
                "&lt;/dependency&gt;\n" +
                "</code></pre>");
        articleEs.setCreateTime(new Date());
        articleEs.setUpdateTime(new Date());
        articleEs.setIsAppreciate(1);
        ArticleEs es = articleRepository.save(articleEs);
        System.out.println(es);

    }


    @Test
    public void test2(){
        PageRequest pageable = PageRequest.of(0, 10);
        String keyword="Spring Boot";
        Page<ArticleEs> pages = articleRepository.findByContentLikeOrTitleLike( StrUtil.replace(keyword," ",""),StrUtil.replace(keyword," ",""), pageable);
        System.out.println(pages);
    }
}
