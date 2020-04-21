package com.xll.decorator;

/**
 *  将要被装饰的类，实现接口Component
 */
public class ConcreteComponent implements Component {

    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
