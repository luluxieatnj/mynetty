package com.xll.decorator;

/**
 * IO操作大量使用装饰者模式，核心
 *    new BufferedInputStream(new FileInputStream(""));
 */

public class Client {
    public static void main(String[] args) {

//        Component component = new ConcreteDecorator2(new ConcreteDecorator1(new ConcreteComponent()));

//        Component component = new ConcreteDecorator1(new ConcreteComponent());

        Component component = new ConcreteComponent();

        component.doSomething();
    }
}
