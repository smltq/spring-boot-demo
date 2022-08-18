package com.easy.pipeline;

/**
 * 处理器(管道的各个阶段)
 */
interface Handler<I, O> {
    O process(I input);
}
