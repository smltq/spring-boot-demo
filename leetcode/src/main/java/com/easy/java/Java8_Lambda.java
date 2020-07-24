package com.easy.java;

/**
 * java8之Lambda特性示例
 */
public class Java8_Lambda {
    public static void main(String[] args) {
        System.out.println("你好，欢迎来到java8世界！");

        //Lambda 表达式
        //Lambda 表达式，也可称为闭包，它是推动 Java 8 发布的最重要新特性。
        //Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
        //使用 Lambda 表达式可以使代码变的更加简洁紧凑。

        // 1.类型声明
        MathOperation add = (int a, int b) -> a + b;

        // 2.不用类型声明
        MathOperation subtract = (a, b) -> a - b;

        // 3.大括号中的返回语句
        MathOperation multiply = (int a, int b) -> {
            return a * b;
        };

        // 4.没有大括号及返回语句
        MathOperation divide = (int a, int b) -> a / b;

        Java8_Lambda java8 = new Java8_Lambda();

        System.out.println("6+2=" + java8.operate(6, 2, add));
        System.out.println("8-2=" + java8.operate(8, 2, subtract));
        System.out.println("2*4=" + java8.operate(2, 4, multiply));
        System.out.println("8/2=" + java8.operate(8, 2, divide));

        // 5.不用括号
        MsgService msgService1 = message -> System.out.println("Hello " + message);

        // 6.用括号
        MsgService msgService2 = (message) -> System.out.println("Hello " + message);

        msgService1.sendMsg("java8");
        msgService2.sendMsg("老林");

        //7.变量作用域
        //lambda 表达式只能引用标记了 final 的外层局部变量，这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
        MsgService msgService3 = message -> System.out.println(salutation + message);
        msgService3.sendMsg("这是引用变量示例");

        //8.在 lambda 表达式中访问外层的局部变量
        final int a = 6;
        final int b = 2;
        System.out.println("6+6=" + java8.operate(a, b + 4, add));

        //9.lambda 表达式的局部变量可以不用声明为 final，但是必须不可被后面的代码修改（即隐性的具有 final 的语义）
        String num = "4";
        ConvertOperation<Integer, String> convert1 = (param) -> System.out.println("4+2=" + (param + Integer.parseInt(num)));
        convert1.convert(2);  // 输出结果为 6
        //num = "5";    //如果试图去改变num值，编译器会报错。出错信息：Variable used in lambda expression should be final or effectively final

        //10.不允许声明一个与局部变量同名的参数或者局部变量。
        //ConvertOperation<Integer, String> convert2 = (num) -> System.out.println("4+2=" + (num + 4));   //编译会出错
    }

    final static String salutation = "Hello ";

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

/**
 * 转换操作
 *
 * @param <T1>
 * @param <T2>
 */
interface ConvertOperation<T1, T2> {
    void convert(int i);
}
