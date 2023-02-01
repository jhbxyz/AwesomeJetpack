package com.jhb.awesomejetpack.flow

/**
 * @author jianghaibo
 * @date 2023/1/12
 * 直播间同步的接口
 */
interface IKtLiveRoom<T : BaseFlow> {

    /**
     * @param data: 具体要同步的数据
     */
    fun emit(data: T, stick: Boolean = true)

    /**
     * @return 返回要同步的数据
     */
    fun  collect(action: (T) -> Unit, stick: Boolean = true)

}