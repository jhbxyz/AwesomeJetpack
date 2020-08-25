# ViewModel

1.定义

用来保存UI数据的类，并且在应用配置发送改变后（例如横竖屏切换）依然存在，生命周期比Activity长

必要时配合Repository（中有Dao和Network）使用，分离责任，使ViewModel不那么臃肿

2.和onSaveInstanceState的关系

相辅相成，不是替代

进程关闭是onSaveInstanceState的数据会保留，而ViewModel销毁

ViewModel存储大量数据

onSaveInstanceState存储少量数据







