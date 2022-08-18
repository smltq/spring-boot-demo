package com.easy.pipeline;

interface Handler<I, O> {
    O process(I input);
}
