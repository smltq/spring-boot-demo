package com.easy.webmagic.controller;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class MyPageProcesser implements PageProcessor {

    private Site site = Site.me().setDomain("127.0.0.1");

    @Override
    public void process(Page page) {
        page.putField("download", page.getHtml().xpath("//*[@id=\"Zoom\"]/span/table/tbody/tr/td/a").toString());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new MyPageProcesser()).addUrl("https://www.dytt8.net/html/gndy/dyzz/list_23_1.html")
//                .addPipeline(new ConsolePipeline()).thread(1).run();
        Spider.create(new MyPageProcesser()).addUrl("https://www.dytt8.net/html/gndy/dyzz/20191204/59453.html")
                .addPipeline(new ConsolePipeline()).thread(1).run();
    }
}