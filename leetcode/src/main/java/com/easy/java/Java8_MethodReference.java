package com.easy.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * java8之方法引用
 */
public class Java8_MethodReference {
    public static void main(String[] args) {
        List names = new ArrayList();

        names.add("baidu");
        names.add("google");
        names.add("bbx");

        //将System.out::println 方法作为静态方法来引用
        names.forEach(System.out::println);

        //构造器引用：它的语法是Class::new，或者更一般的Class< T >::new
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        //静态方法引用：它的语法是Class::static_method
        cars.forEach(Car::collide);

        //特定类的任意对象的方法引用：它的语法是Class::method
        cars.forEach(Car::repair);

        //特定对象的方法引用：它的语法是instance::method
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);
    }
}

class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("调用碰撞检查方法：" + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("调用跟随方法：" + another.toString());
    }

    public void repair() {
        System.out.println("调用修理方法:" + this.toString());
    }
}