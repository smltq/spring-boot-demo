package com.easy.pipeline;

import lombok.var;

/**
 * 管道模式
 */
public class PipelineTest {

    public static void main(String[] args) {
        var filters = new Pipeline<>(new RemoveAlphabetsHandler())
                .addHandler(new RemoveDigitsHandler())
                .addHandler(new ConvertToCharArrayHandler());
        filters.execute("GoYankees123!");
    }
}
