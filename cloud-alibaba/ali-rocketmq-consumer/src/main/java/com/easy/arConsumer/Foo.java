package com.easy.arConsumer;

import lombok.Data;

@Data
public class Foo {

    private int id;

    private String bar;

    public Foo(int id, String bar) {
        this.id = id;
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "Foo{" + "id=" + id + ", bar='" + bar + '\'' + '}';
    }

}
