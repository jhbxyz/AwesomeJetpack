# Android Jetpack组件（一）Lifecycle基本使用和原理分析

这是Jetpack系列的第一篇文章，先看一下Jetpack的背景历史，总体了解一下什么是Jetpack。

### 1.Jetpack的历史背景

Goole I/O 2017大会时推出了 [Android Architecture Component（AAC](https://developer.android.com/topic/libraries/architecture/index.html)），它包括了[Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle.html)、[LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)、[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)、Room。

Goole I/O 2018大会上，用AndroidX替代了Android Support Library，并在Android Architecture Component的基础上发布了Android Jetpack，AndroidX也是属于Android [Jetpack](https://developer.android.com/jetpack)。

### 2.什么是Jetpack

[Jetpack](https://developer.android.com/jetpack)并不是一个框架或者组件，它是一套库、工具和指南的集合，可帮助开发者更轻松地编写优质应用。

### 3.Lifecycle简介和基础使用

**什么是[Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle.html)**

Lifecycle提供了可用于构建生命周期感知型组件的类和接口，可以根据 Activity 或 Fragment 的当前生命周期状态自动调整其行为。

Lifecycle 库能有效的避免内存泄漏和解决常见的 Android 生命周期难题，这就是我们为什么使用她的意义！

**基础使用**

需求：比如我们想提供一个接口可以感知Activity的生命周期，并且实现回调！用Lifecycle是怎么实现的？

##### 1.首先我们定义一个接口实现 LifecycleObserver，然后定义方法，用上OnLifecycleEvent注解。

```kotlin
interface ILifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume()

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause()

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop()

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy()

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event)

}
```

注意：当然你也可以不实现LifecycleObserver而是实现DefaultLifecycleObserver接口，Google官方更推荐我们使用 **DefaultLifecycleObserver** 类

在build.gradle 中依赖

```groovy
def lifecycle_version = "2.2.0"
implementation "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"
```

```kotlin
class BaseLifecycle : DefaultLifecycleObserver {
		//处理生命周期回调
}
```

##### 2.定义一个类是实现我们定义好的ILifecycleObserver接口

```kotlin
class ActivityLifecycleObserver : ILifecycleObserver {

    var mTag = javaClass.simpleName
    override fun onCreate() {
        "onCreate ".logWithTag(mTag)
    }

    override fun onStart() {
        "onStart ".logWithTag(mTag)
    }

    override fun onResume() {
        "onResume ".logWithTag(mTag)
    }

    override fun onPause() {
        "onPause ".logWithTag(mTag)
    }

    override fun onStop() {
        "onStop ".logWithTag(mTag)
    }

    override fun onDestroy() {
        "onDestroy ".logWithTag(mTag)
    }

    override fun onLifecycleChanged(owner: LifecycleOwner, event: Lifecycle.Event) {
        "onLifecycleChanged  owner = $owner     event = $event".logWithTag(mTag)
    }

}
```

##### 3.在我们的BaseActivity中通过getLifecycle()获取一个Lifecycle，然后把我们的ActivityLifecycleObserver添加进来

```kotlin
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(ActivityLifecycleObserver())//
    }
}
```

##### 4.LifecycleActivity继承BaseActivity，然后运行代码，看日志

```kotlin
class LifecycleActivity : BaseActivity()
```

每当Activity发生了对应的生命周期改变，ActivityLifecycleObserver就会执行对应事件注解的方法，其中onLifecycleChanged的注解是**@OnLifecycleEvent(Lifecycle.Event.ON_ANY)**所以每次都会调用

##### 5.结果日志

```log
E/ActivityLifecycleObserver: onCreate
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_CREATE
E/ActivityLifecycleObserver: onStart
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_START
E/ActivityLifecycleObserver: onResume
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_RESUME
E/ActivityLifecycleObserver: onPause
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_PAUSE
E/ActivityLifecycleObserver: onStop
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_STOP
E/ActivityLifecycleObserver: onDestroy
E/ActivityLifecycleObserver: onLifecycleChanged  owner = com.jhb.awesomejetpack.lifecycle.LifecycleActivity@56d55b5     event = ON_DESTROY
```

启动Activity并关闭页面，生命周期果然，回调了！

**抛出问题：**

* Lifecycle是怎样感知生命周期的？
* Lifecycle是如何处理生命周期的？

* LifecycleObserver的方法是怎么回调是的呢？

- 为什么LifecycleObserver可以感知到Activity的生命周期

以下的原理分析，会具体说明

### 4.Lifecycle的原理分析

##### 1.首先在分析原因之前先要对几个类的进行说明

* Lifecycle：用于存储有关组件（如 Activity 或 Fragment）的生命周期状态的信息，并允许其他对象观察此状态。
* Lifecycle.Event：[`Lifecycle`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle) 类分派的生命周期事件。这些事件映射到 Activity 和 Fragment 中的回调事件。
* Lifecycle.State：由 [`Lifecycle`](https://developer.android.com/reference/androidx/lifecycle/Lifecycle) 对象跟踪的组件的当前状态。

* LifecycleOwner：可获取Lifecycle的接口，生命周期改变可以出发LifecycleObserver对应的生命周期事件

* LifecycleObserver：观察者，观察LifecycleOwner的生命周期



##### 2.先提一下Event和State之间的关系

 Android Activity 生命周期的状态和事件看这个图就能说明

![](https://raw.githubusercontent.com/jhbxyz/AwesomeJetpack/master/images/Lifecycle_1.jpg)

##### 3.了解上面的基本内容，就进行具体具体的源码分析

在基类BaseActivity中的一行代码就能实现对应生命周期的回调

```kotlin
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(ActivityLifecycleObserver())//1
    }
}
```

**注释1** 看下getLifecycle() 方法，点进去

```java
public class ComponentActivity extends xxx implements LifecycleOwner,xxx {//1

	private final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);//2

	@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReportFragment.injectIfNeededIn(this);//4
        
    }

    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;//3
    }

}
```

**注释1：**这里的 LifecycleOwner和LifecycleRegistryOwner是啥？

可以看到ComponentActivity实现了LifecycleOwner接口

```java
public interface LifecycleOwner {
    @NonNull
    Lifecycle getLifecycle();
}
```

她就是来回去一个Lifecycle实例的

**注释2：**在成员变量位置直接实例化LifecycleRegistry (LifecycleRegistry 本身就是一个成熟的 **Lifecycle** 实现类)。

**注释3：**看到getLifecycle() 返回的是一个LifecycleRegistry对象，是Lifecycle的子类

```java
public class LifecycleRegistry extends Lifecycle {}
```

**注释4：**在onCreate方法中，看到初始化了一个ReportFragment，看一下ReportFragment的源码

```java
public class ReportFragment extends Fragment {
	
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dispatchCreate(mProcessListener);
        dispatch(Lifecycle.Event.ON_CREATE);·
    }

    @Override
    public void onStart() {
        super.onStart();
        dispatchStart(mProcessListener);
        dispatch(Lifecycle.Event.ON_START);
    }

    @Override
    public void onResume() {
        super.onResume();
        dispatchResume(mProcessListener);
        dispatch(Lifecycle.Event.ON_RESUME);
    }

    @Override
    public void onPause() {
        super.onPause();
        dispatch(Lifecycle.Event.ON_PAUSE);
    }

    @Override
    public void onStop() {
        super.onStop();
        dispatch(Lifecycle.Event.ON_STOP);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dispatch(Lifecycle.Event.ON_DESTROY);
        // just want to be sure that we won't leak reference to an activity
        mProcessListener = null;
    }

    private void dispatch(Lifecycle.Event event) {
        Activity activity = getActivity();
      	//1
        if (activity instanceof LifecycleRegistryOwner) {
            ((LifecycleRegistryOwner) activity).getLifecycle().handleLifecycleEvent(event);
            return;
        }
				//2
        if (activity instanceof LifecycleOwner) {
            Lifecycle lifecycle = ((LifecycleOwner) activity).getLifecycle();
            if (lifecycle instanceof LifecycleRegistry) {
                ((LifecycleRegistry) lifecycle).handleLifecycleEvent(event);
            }
        }
    }



}
```

等一下，还记得咱们的疑问吗？**Lifecycle是怎样感知生命周期的？**

可以看到在ReportFragment中的各个生命周期都调用了dispatch(Lifecycle.Event event) 方法，传递了不同的Event的值，这个就是在Activity、Fragment生命周期回调时，Lifecycle所要处理的生命周期方法。

**也就是`dispatch(Lifecycle.Event event)`方法**

这个方法中，两个if判断最终都是为了获取LifecycleRegistry的实例，来调用她的handleLifecycleEvent方法，前面我们讲到过，LifecycleRegistry 本身就是一个成熟的 **Lifecycle** 实现类，它被实例化在Activity和Fragment中使用，对在成员变量位置直接实例化过。

此时，就应该看**LifecycleRegistry的handleLifecycleEvent**中的代码了

```java
//LifecycleRegistry.java
public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
    State next = getStateAfter(event);
    moveToState(next);
}
```

根据当前Lifecycle.Event的值，其实也就是Activity/Fragment生命周期回调的值，来获取下一个**Lifecycle.State**的状态，也就是Lifecycle将要到什么状态

```java
//LifecycleRegistry.java
static State getStateAfter(Event event) {
    switch (event) {
        case ON_CREATE:
        case ON_STOP:
            return CREATED;
        case ON_START:
        case ON_PAUSE:
            return STARTED;
        case ON_RESUME:
            return RESUMED;
        case ON_DESTROY:
            return DESTROYED;
        case ON_ANY:
            break;
    }
    throw new IllegalArgumentException("Unexpected event value " + event);
}
```

上面代码结合这个图看，食用效果更加

![](https://raw.githubusercontent.com/jhbxyz/AwesomeJetpack/master/images/Lifecycle_1.jpg)

不同的Lifecycle.Event的生命周期状态对Lifecycle.State的当前状态的取值。



继续跟代码，看下，当到下一个状态时，要发送什么事情

```java
//LifecycleRegistry.java
private void moveToState(State next) {
    if (mState == next) {
        return;
    }
    mState = next;
    if (mHandlingEvent || mAddingObserverCounter != 0) {
        mNewEventOccurred = true;
        return;
    }
    mHandlingEvent = true;
    sync();//1
    mHandlingEvent = false;
}
```

**注释1： sync()方法**

然后看LifecycleRegistry#sync() 方法

```java
//LifecycleRegistry.java
private void sync() {
    LifecycleOwner lifecycleOwner = mLifecycleOwner.get();
    if (lifecycleOwner == null) {
        throw new IllegalStateException("LifecycleOwner of this LifecycleRegistry is already"
                + "garbage collected. It is too late to change lifecycle state.");
    }
    while (!isSynced()) {
        mNewEventOccurred = false;
        // no need to check eldest for nullability, because isSynced does it for us.
        if (mState.compareTo(mObserverMap.eldest().getValue().mState) < 0) {
            backwardPass(lifecycleOwner);//1
        }
        Entry<LifecycleObserver, ObserverWithState> newest = mObserverMap.newest();
        if (!mNewEventOccurred && newest != null
                && mState.compareTo(newest.getValue().mState) > 0) {
            forwardPass(lifecycleOwner);//2
        }
    }
    mNewEventOccurred = false;
}
```

如果没有同步过，会比较mState当前的状态和mObserverMap中的eldest和newest的状态做对比，看是往前还是往后；比如mState由STARTED到RESUMED是状态向前，反过来就是状态向后。这个是和生命周期有关系，但不是一个东西，具体的看上面贴的图，一目了然！

**注释2：往后**这里看下往后的代码**forwardPass(lifecycleOwner);**

然后看LifecycleRegistry#forwardPass() 方法

```java
//LifecycleRegistry.java
private void forwardPass(LifecycleOwner lifecycleOwner) {
    Iterator<Entry<LifecycleObserver, ObserverWithState>> ascendingIterator =
            mObserverMap.iteratorWithAdditions();
    while (ascendingIterator.hasNext() && !mNewEventOccurred) {
        Entry<LifecycleObserver, ObserverWithState> entry = ascendingIterator.next();
        ObserverWithState observer = entry.getValue()；//1
        while ((observer.mState.compareTo(mState) < 0 && !mNewEventOccurred
                && mObserverMap.contains(entry.getKey()))) {
            pushParentState(observer.mState);
            observer.dispatchEvent(lifecycleOwner, upEvent(observer.mState));//2
            popParentState();
        }
    }
}
```

注释1：获取ObserverWithState实例

注释2：调用ObserverWithState的dispatchEvent方法

看下ObserverWithState的代码

```java
//ObserverWithState.java
static class ObserverWithState {
    State mState;
    LifecycleEventObserver mLifecycleObserver;

    ObserverWithState(LifecycleObserver observer, State initialState) {
        mLifecycleObserver = Lifecycling.lifecycleEventObserver(observer);//1
        mState = initialState;
    }

    void dispatchEvent(LifecycleOwner owner, Event event) {
        State newState = getStateAfter(event);
        mState = min(mState, newState);
        mLifecycleObserver.onStateChanged(owner, event);//2
        mState = newState;
    }
}
```

ObserverWithState内部包括了State和LifecycleEventObserver，LifecycleEventObserver是一个接口，它继承了LifecycleObserver接口。

ObserverWithState是怎么初始化的？这里提一句，是在Lifecycle.addObserver(@NonNull LifecycleObserver observer);方法时候初始化的。

注释1：mLifecycleObserver这个的获取的实例其实是**ReflectiveGenericLifecycleObserver**，具体的点进去看一眼就明白了，我就不贴代码了，但是得注意在实例化 ReflectiveGenericLifecycleObserver(object);时候把LifecycleObserver，传入ReflectiveGenericLifecycleObserver的构造中了，此时**ReflectiveGenericLifecycleObserver持有LifecycleObserver的实例**

注释2：关键代码 mLifecycleObserver.onStateChanged(owner, event);那这个onStateChanged方法咋调用的呢？这里调用的是ReflectiveGenericLifecycleObserver的.onStateChanged方法



接下来看下**ReflectiveGenericLifecycleObserver的.onStateChanged方法**

```java
//ReflectiveGenericLifecycleObserver.java
class ReflectiveGenericLifecycleObserver implements LifecycleEventObserver {
    private final Object mWrapped;
    private final CallbackInfo mInfo;

    ReflectiveGenericLifecycleObserver(Object wrapped) {
        mWrapped = wrapped;//LifecycleObserver的实例
        mInfo = ClassesInfoCache.sInstance.getInfo(mWrapped.getClass());//1
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Event event) {
        mInfo.invokeCallbacks(source, event, mWrapped);//2
    }
}
```

**注意：mWrapped其实是LifecycleObserver的实例**

接下来看mInfo的初始化过程，这个是**关键**了

```java
//ClassesInfoCache.java
CallbackInfo getInfo(Class<?> klass) {
    CallbackInfo existing = mCallbackMap.get(klass);
    if (existing != null) {
        return existing;
    }
    existing = createInfo(klass, null);//1
    return existing;
}
```

**注意**：这个klass是LifecycleObserver的字节码文件对象（LifecycleObserver.class）

```java
private CallbackInfo createInfo(Class<?> klass, @Nullable Method[] declaredMethods) {
   ...
     //1
    Method[] methods = declaredMethods != null ? declaredMethods : getDeclaredMethods(klass);
    boolean hasLifecycleMethods = false;
    for (Method method : methods) {
      //2
        OnLifecycleEvent annotation = method.getAnnotation(OnLifecycleEvent.class);
        if (annotation == null) {
            continue;
        }
        hasLifecycleMethods = true;
      
        Class<?>[] params = method.getParameterTypes();
        int callType = CALL_TYPE_NO_ARG;
        if (params.length > 0) {
            callType = CALL_TYPE_PROVIDER;
            if (!params[0].isAssignableFrom(LifecycleOwner.class)) {
                throw new IllegalArgumentException(
                        "invalid parameter type. Must be one and instanceof LifecycleOwner");
            }
        }
      //3
        Lifecycle.Event event = annotation.value();
			//4
        if (params.length > 1) {
            callType = CALL_TYPE_PROVIDER_WITH_EVENT;
            if (!params[1].isAssignableFrom(Lifecycle.Event.class)) {
                throw new IllegalArgumentException(
                        "invalid parameter type. second arg must be an event");
            }
            if (event != Lifecycle.Event.ON_ANY) {
                throw new IllegalArgumentException(
                        "Second arg is supported only for ON_ANY value");
            }
        }
        if (params.length > 2) {
            throw new IllegalArgumentException("cannot have more than 2 params");
        }
      //5
        MethodReference methodReference = new MethodReference(callType, method);
        verifyAndPutHandler(handlerToEvent, methodReference, event, klass);
    }
    CallbackInfo info = new CallbackInfo(handlerToEvent);
    mCallbackMap.put(klass, info);
    mHasLifecycleMethods.put(klass, hasLifecycleMethods);
    return info;
}
```

注释1：获取LifecycleObserver.class 声明的方法

注释2：遍历方法，获取方法上声明的OnLifecycleEvent注解

注释3：获取OnLifecycleEvent注解上的value

注释4：给callType = CALL_TYPE_PROVIDER_WITH_EVENT 赋值

注释5：把callType和当前的method 存储到MethodReference中，接下来取用，看一下代码

```java
//MethodReference.java
static class MethodReference {
    final int mCallType;
    final Method mMethod;

    MethodReference(int callType, Method method) {
        mCallType = callType;
        mMethod = method;
        mMethod.setAccessible(true);
    }
}
```

好的，以上的mInfo 赋值的问题就看完了

然后就是mInfo的invokeCallbacks方法

**继续看ClassesInfoCache的invokeCallbacks方法**

```java
//ClassesInfoCache.java
void invokeCallbacks(LifecycleOwner source, Lifecycle.Event event, Object target) {
    invokeMethodsForEvent(mEventToHandlers.get(event), source, event, target);
    invokeMethodsForEvent(mEventToHandlers.get(Lifecycle.Event.ON_ANY), source, event,
            target);//ON_ANY也会调用
}

private static void invokeMethodsForEvent(List<MethodReference> handlers,
        LifecycleOwner source, Lifecycle.Event event, Object mWrapped) {
    if (handlers != null) {
        for (int i = handlers.size() - 1; i >= 0; i--) {
            handlers.get(i).invokeCallback(source, event, mWrapped);//1
        }
    }
}

```

继续看invokeCallback方法

```java
//ClassesInfoCache.java
void invokeCallback(LifecycleOwner source, Lifecycle.Event event, Object target) {
    //noinspection TryWithIdenticalCatches
    try {
        switch (mCallType) {
            case CALL_TYPE_NO_ARG:
                mMethod.invoke(target);
                break;
            case CALL_TYPE_PROVIDER:
                mMethod.invoke(target, source);
                break;
            case CALL_TYPE_PROVIDER_WITH_EVENT:
                mMethod.invoke(target, source, event);
                break;
        }
    } catch (InvocationTargetException e) {
        throw new RuntimeException("Failed to call observer method", e.getCause());
    } catch (IllegalAccessException e) {
        throw new RuntimeException(e);
    }
}
```

看到最后是用反射调用了mMethod.invoke(target);这里的**target就是LifecycleObserver**之前解释过了

mCallType和mMethod是什么呢？就是在前面**初始化mInfo存的值**，再看下源码

```java
static class MethodReference {
    final int mCallType;
    final Method mMethod;

    MethodReference(int callType, Method method) {
        mCallType = callType;
        mMethod = method;
        mMethod.setAccessible(true);
    }

    void invokeCallback(LifecycleOwner source, Lifecycle.Event event, Object target) {
        //noinspection TryWithIdenticalCatches
        try {
            switch (mCallType) {
                case CALL_TYPE_NO_ARG:
                    mMethod.invoke(target);
                    break;
                case CALL_TYPE_PROVIDER:
                    mMethod.invoke(target, source);
                    break;
                case CALL_TYPE_PROVIDER_WITH_EVENT:
                    mMethod.invoke(target, source, event);
                    break;
            }
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to call observer method", e.getCause());
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
```

由前面分析可以知道mCallType = CALL_TYPE_PROVIDER_WITH_EVENT，mMethod就是当时遍历时当前的方法

由于之前通过Map存储过，所以invokeCallback会被遍历调用，最终会反射调用对方法和注解。

当然其他mCallType的值也会被反射调用

### 5.总结来在回顾当初抛出的问题

**当初抛出问题：**

- Lifecycle是怎样感知生命周期的？
  * 通过ReportFragment方法在其自己的生命周期方法中调用dispatch(Lifecycle.Event.XXX);方法
- Lifecycle是如何处理生命周期的？
  * 通过LifecycleRegistry的handleLifecycleEvent方法

- LifecycleObserver的方法是怎么回调是的呢？
  - LifecycleRegistry的handleLifecycleEvent方法，然后会通过层层调用到LifecycleObserver方法上的@OnLifecycleEvent(Lifecycle.Event.XXX)注解

- 为什么LifecycleObserver可以感知到Activity的生命周期
  - LifecycleRegistry调用handleLifecycleEvent方法时会传递Event类型，然后会通过层层调用到LifecycleObserver方法上的@OnLifecycleEvent(Lifecycle.Event.XXX)注解上对应的Event的方法，这就和Activity/Fragment的生命周期的方法对应上了





