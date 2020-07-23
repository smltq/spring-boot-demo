package com.easy.java;

/**
 * java8特性示例
 */
public class Java8 {
    public static void main(String[] args) {
        System.out.println("你好，欢迎来到java8世界！");

        //Lambda 表达式
        //Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
        //Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
        //使用 Lambda 表达式可以使代码变的更加简洁紧凑。

        // 类型声明
        MathOperation add = (int a, int b) -> a + b;

        // 不用类型声明
        MathOperation subtract = (a, b) -> a - b;

        // 大括号中的返回语句
        MathOperation multiply = (int a, int b) -> {
            return a * b;
        };

        // 没有大括号及返回语句
        MathOperation divide = (int a, int b) -> a / b;

        Java8 java8 = new Java8();

        System.out.println("6+2=" + java8.operate(6, 2, add));
        System.out.println("8-2=" + java8.operate(8, 2, subtract));
        System.out.println("2*4=" + java8.operate(2, 4, multiply));
        System.out.println("8/2=" + java8.operate(8, 2, divide));

        // 不用括号
        MsgService msgService1 = message -> System.out.println("Hello " + message);

        // 用括号
        MsgService msgService2 = (message) -> System.out.println("Hello " + message);

        msgService1.sendMsg("java8");
        msgService2.sendMsg("老林");
    }

    private int operate(int a, int b, MathOperation mathOperation) {
        return mathOperation.operation(a, b);
    }
}

/**
 * 操作接口
 */
interface MathOperation {
    int operation(int a, int b);
}

/**
 * 消息服务
 */
interface MsgService {
    void sendMsg(String message);
}
