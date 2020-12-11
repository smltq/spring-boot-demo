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