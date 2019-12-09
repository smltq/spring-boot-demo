# WebMagic 实现爬虫入门

本示例实现某电影网站最新片源名称列表及详情页下载地址的抓取。

webmagic是一个开源的Java垂直爬虫框架，目标是简化爬虫的开发流程，让开发者专注于逻辑功能的开发。

## WebMagic 特点：

- 完全模块化的设计，强大的可扩展性。

- 核心简单但是涵盖爬虫的全部流程，灵活而强大，也是学习爬虫入门的好材料。

- 提供丰富的抽取页面API。

- 无配置，但是可通过POJO+注解形式实现一个爬虫。

- 支持多线程。

- 支持分布式。

- 支持爬取js动态渲染的页面。

- 无框架依赖，可以灵活的嵌入到项目中去。

## 示例

本示例实现：https://www.dytt8.net/html/gndy/dyzz/list_23_1.html 电影网站最新片源名称及详情页影片下载链接内容的抓取。

### 配置Maven依赖

pom.xml配置，这里因为日志文件和spring boot冲突了，所以移除webmagic的日志依赖 log4j12
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.9.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.easy</groupId>
    <artifactId>webmagic</artifactId>
    <version>0.0.1</version>
    <name>webmagic</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <encoding>UTF-8</encoding>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-core</artifactId>
            <version>0.7.3</version>
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-extension</artifactId>
            <version>0.7.3</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <scope>compile</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>

```

### 创建列表及详情页解析类

PageProcessor负责解析页面，抽取有用信息，以及发现新的链接。WebMagic使用Jsoup作为HTML解析工具，并基于其开发了解析XPath的工具Xsoup。

ListPageProcesser.java 实现影片名称列表获取
```java
package com.easy.webmagic.controller;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class ListPageProcesser implements PageProcessor {
    private Site site = Site.me().setDomain("127.0.0.1");

    @Override
    public void process(Page page) {
        page.putField("title", page.getHtml().xpath("//a[@class='ulink']").all().toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}
``` 

DetailPageProcesser.java 实现详情页影片下载地址获取
```java
package com.easy.webmagic.controller;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DetailPageProcesser implements PageProcessor {
    private Site site = Site.me().setDomain("127.0.0.1");

    @Override
    public void process(Page page) {
        page.putField("download", page.getHtml().xpath("//*[@id=\"Zoom\"]/span/table/tbody/tr/td/a").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }
}

```

### 使用Pipeline处理抓取结果

Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。

Pipeline定义了结果保存的方式，如果你要保存到指定数据库，则需要编写对应的Pipeline。对于一类需求一般只需编写一个Pipeline。

这里不做任何处理，直接把抓包到的结果在控制台输出

MyPipeline.java
```java
package com.easy.webmagic.controller;

import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

@Slf4j
public class MyPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        log.info("get page: " + resultItems.getRequest().getUrl());
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            log.info(entry.getKey() + ":\t" + entry.getValue());
        }
    }
}
```

### 启动抓包入口

Main.java
```java
package com.easy.webmagic.controller;

import us.codecraft.webmagic.Spider;

public class Main {
    public static void main(String[] args) {
        //获取影片标题和页面链接
        Spider.create(new ListPageProcesser()).addUrl("https://www.dytt8.net/html/gndy/dyzz/list_23_1.html")
                .addPipeline(new MyPipeline()).thread(1).run();

        //获取指定详情页面的影片下载地址
        Spider.create(new DetailPageProcesser()).addUrl("https://www.dytt8.net/html/gndy/dyzz/20191204/59453.html")
                .addPipeline(new MyPipeline()).thread(1).run();
    }
}
```

## 运行示例

启动运行Main.java，观察控制台

影片第一页标题列表
```cfml
14:06:28.704 [pool-1-thread-1] INFO com.easy.webmagic.controller.MyPipeline - get page: https://www.dytt8.net/html/gndy/dyzz/list_23_1.html
14:06:28.704 [pool-1-thread-1] INFO com.easy.webmagic.controller.MyPipeline - title:	[<a href="/html/gndy/dyzz/20191204/59453.html" class="ulink">2019年剧情《中国机长》HD国语中英双字</a>, <a href="/html/gndy/dyzz/20191201/59437.html" class="ulink">2019年动画喜剧《雪人奇缘》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191201/59435.html" class="ulink">2019年喜剧《伯纳黛特你去了哪》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191129/59431.html" class="ulink">2019年高分剧情《爱尔兰人/爱尔兰杀手》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191129/59429.html" class="ulink">2019年剧情《唐顿庄园电影版》BD中英双字[修正字幕]</a>, <a href="/html/gndy/dyzz/20191129/59428.html" class="ulink">2018年悬疑动作《雪暴》BD国语中字</a>, <a href="/html/gndy/dyzz/20191128/59427.html" class="ulink">2019年剧情惊悚《官方机密》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191127/59425.html" class="ulink">2019年高分剧情《少年的你》HD国语中字</a>, <a href="/html/gndy/dyzz/20191126/59424.html" class="ulink">2019年剧情冒险《攀登者》HD国语中英双字</a>, <a href="/html/gndy/dyzz/20191126/59423.html" class="ulink">2019年剧情《金翅雀》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191125/59422.html" class="ulink">2019年高分获奖《好莱坞往事》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191125/59421.html" class="ulink">2018年动画冒险《猫与桃花源》BD国粤双语中字</a>, <a href="/html/gndy/dyzz/20191124/59418.html" class="ulink">2019年恐怖《准备好了没/弑婚游戏》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191124/59417.html" class="ulink">2019年剧情悬疑《双魂》BD国粤双语中字</a>, <a href="/html/gndy/dyzz/20191122/59409.html" class="ulink">2019年科幻动作《双子杀手》HD中英双字幕</a>, <a href="/html/gndy/dyzz/20191122/59408.html" class="ulink">2019年奇幻《天堂山/天堂山�f》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191121/59407.html" class="ulink">2019年恐怖《小丑回魂2》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191117/59403.html" class="ulink">2019年高分动画《克劳斯：圣诞节的秘密》BD国英西三语双字</a>, <a href="/html/gndy/dyzz/20191116/59400.html" class="ulink">2019年动作《天使陷落》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191115/59399.html" class="ulink">2019年悬疑惊悚《犯罪现场》HD国粤双语中字</a>, <a href="/html/gndy/dyzz/20191115/59398.html" class="ulink">2019年高分剧情《别告诉她》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191114/59393.html" class="ulink">2019年动作《原始恐惧》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191114/59392.html" class="ulink">2019年剧情《婚礼之后》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191113/59387.html" class="ulink">2019年动作战争《危机：龙潭之战》BD中英双字幕</a>, <a href="/html/gndy/dyzz/20191113/59386.html" class="ulink">2019年犯罪动作《沉默的证人》BD国粤双语中字</a>]
```

详情页影片下载地址
```cfml
14:06:34.365 [pool-2-thread-1] INFO com.easy.webmagic.controller.MyPipeline - get page: https://www.dytt8.net/html/gndy/dyzz/20191204/59453.html
14:06:34.365 [pool-2-thread-1] INFO com.easy.webmagic.controller.MyPipeline - download:	<a href="ftp://ygdy8:ygdy8@yg45.dydytt.net:4233/阳光电影www.ygdy8.com.中国机长.HD.1080p.国语中英双字.mkv">ftp://ygdy8:ygdy8@yg45.dydytt.net:4233/阳光电影www.ygdy8.com.中国机长.HD.1080p.国语中英双字.mkv</a>
```

表示成功抓取到数据，抓取到数据后你就可以做你想做的事情了。

## 爬虫进阶

### 使用Selectable抽取元素

Selectable相关的抽取元素链式API是WebMagic的一个核心功能。使用Selectable接口，你可以直接完成页面元素的链式抽取，也无需去关心抽取的细节。

### 爬虫的配置、启动和终止

Spider是爬虫启动的入口。在启动爬虫之前，我们需要使用一个PageProcessor创建一个Spider对象，然后使用run()进行启动。同时Spider的其他组件（Downloader、Scheduler、Pipeline）都可以通过set方法来进行设置。

### Jsoup和Xsoup

WebMagic的抽取主要用到了Jsoup和我自己开发的工具Xsoup。

### 爬虫的监控

利用这个功能，你可以查看爬虫的执行情况——已经下载了多少页面、还有多少页面、启动了多少线程等信息。该功能通过JMX实现，你可以使用Jconsole等JMX工具查看本地或者远程的爬虫信息。

### 配置代理

ProxyProvider有一个默认实现：SimpleProxyProvider。它是一个基于简单Round-Robin的、没有失败检查的ProxyProvider。可以配置任意个候选代理，每次会按顺序挑选一个代理使用。它适合用在自己搭建的比较稳定的代理的场景。

### 处理非HTTP GET请求

采用在Request对象上添加Method和requestBody来实现。例如:

```java
Request request = new Request("http://xxx/path");
request.setMethod(HttpConstant.Method.POST);
request.setRequestBody(HttpRequestBody.json("{'id':1}","utf-8"));
```

### 使用注解编写爬虫

WebMagic支持使用独有的注解风格编写一个爬虫，引入webmagic-extension包即可使用此功能。

在注解模式下，使用一个简单对象加上注解，可以用极少的代码量就完成一个爬虫的编写。对于简单的爬虫，这样写既简单又容易理解，并且管理起来也很方便。

## 资料

- [WebMagic 爬虫入门示例源码](https://github.com/smltq/spring-boot-demo/blob/master/webmagic)
- [WebMagic GitHub](https://github.com/code4craft/webmagic/blob/master/README-zh.md)