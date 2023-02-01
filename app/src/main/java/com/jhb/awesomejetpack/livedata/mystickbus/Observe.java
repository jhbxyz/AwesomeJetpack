package com.jhb.awesomejetpack.livedata.mystickbus;

/**
 * @author jianghaibo
 * @date 2023/2/1
 * 观察者
 */
public interface Observe<T> {

    void onChange(T t);
}
