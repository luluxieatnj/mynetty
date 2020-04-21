package com.xll.decorator;

/**
 *  装饰角色，实现抽象构建角色
 *     1.持有一个被装饰对象
 *     2.实现父类接口
 */
public class Decorator implements Component {

    // 1.持有一个被装饰对象
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    //
    @Override
    public void doSomething() {
        component.doSomething();
    }
}
