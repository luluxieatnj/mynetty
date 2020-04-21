package com.xll.decorator;

/**
 *  装饰者1  具体装饰者，
 */
public class ConcreteDecorator1  extends Decorator{

    public ConcreteDecorator1(Component component) {
        super(component);
    }

    @Override
    public void doSomething() {
        super.doSomething();
        this.doAntherthing();
    }

    public void doAntherthing() {
        System.out.println("功能B");
    }
}
