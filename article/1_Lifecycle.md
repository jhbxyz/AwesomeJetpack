# Lifecycle

Lifecycle 库能有效的避免内存泄漏和解决常见的 Android 生命周期难题

Lifecycle 库中包含

* LiveData类
* ViewModel

Lifecycle：定义Android生命周期和状态的类

LifecycleOwner：获取Lifecycle的接口，相当于被观察者，可感知生命周期

LifecycleObserver：观察者，观察LifecycleOwner的生命周期