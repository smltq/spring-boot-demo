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
