# LiveData

### 1.定义

LiveData 是一个可感知生命周期的可观察数据的持有类 (Observable)。

### 2.LiveData的优点

- 生命周期感知性：LiveData 与 Android 生命周期结合运行的效果良好。它仅会当 UI 被显示时才把数据往前端传递。
- 与 Room 无缝整合：LiveData 可被设为 Room 的回调类。
- 可与 ViewModel 和 Data Binding 混合使用，建立反应式 UI 。
- 提供基本数据转换方法，例如 switchMap 和 MediatorLiveData。

**注意**：LiveData 通常在ViewModel中使用

### 3.如何使用

举一个LiveData、ViewModel、Databinding配合使用的例子

需求：点击个按钮来更新TextView上的显示内容







