package com.xll.decorator;

public class ConcreteDecorator2 extends Decorator{

    public ConcreteDecorator2(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAntherthing();
    }

    public void doAntherthing() {
        System.out.println("功能C");
    }
}
