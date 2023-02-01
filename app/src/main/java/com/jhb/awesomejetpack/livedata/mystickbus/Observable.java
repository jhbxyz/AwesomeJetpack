package com.jhb.awesomejetpack.livedata.mystickbus;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jianghaibo
 * @date 2023/2/1
 * 被观察着
 */
public class Observable<T> {

    private final List<Observe<T>> observes = new ArrayList<>();
    private final LinkedList<T> tList = new LinkedList<>();

    public void register(Observe<T> observe) {
        if (!observes.contains(observe)) {
            observes.add(observe);
            for (T t : tList) {
                observe.onChange(t);
            }
        }
    }

    public void register(boolean last, Observe<T> observe) {
        if (!observes.contains(observe)) {
            observes.add(observe);
            if (last) {
                T listLast = tList.getLast();
                observe.onChange(listLast);
            }
        }
    }

    public void send(T t) {
        if (!tList.contains(t)) {
            tList.add(t);
        }
        for (Observe<T> observe : observes) {
            observe.onChange(t);
            tList.remove(t);
        }
    }

}
