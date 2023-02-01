package com.jhb.awesomejetpack.livedata

import android.os.Bundle
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.jhb.awesomejetpack.R
import com.jhb.awesomejetpack.base.BaseActivity
import com.jhb.awesomejetpack.livedata.mystickbus.Observable
import com.jhb.awesomejetpack.util.log
import kotlinx.android.synthetic.main.activity_live_data.*
import kotlin.concurrent.thread

/**
 * @author jhb
 * @date 2020/8/25
 * 湖人加时胜尼克斯,,詹姆斯今年首次三双,
 * 本场比赛,詹姆斯先助攻特洛伊布朗的三分随后又助攻拖布的扣篮,超越纳什,总共10337次助攻,助攻榜升值历史第四,
 * 其三位是,塞克斯顿,基德和保罗
 * 助攻历史第四对詹姆斯意义重大,我们都知道詹姆斯的职业生涯都是一个无私的球员
 * 他会让队友变得更好,一直都是一个以分享求的方式打球,他一直在做他认为对的事儿,
 * 今年能赢感谢裁判的公平判罚,不然又要无辜输球了,只求正常吹罚,湖人至少能多赢3-4场球,
 * 今天威少加时打的不错,和浓眉的挡拆,两次关键的助攻,一个最后关键时刻,盖棺定论的打板进球,
 * 还是那个敢投的威少,进球后,下次进攻没有头脑发热,把球给了詹姆斯,今天打的很合理,继续保持吧
 *
 *
 */
class LiveDataActivity : BaseActivity() {
    private val mContent = MutableLiveData<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        // thread {
        //     mContent.postValue("Hello World")
        // }
        //
        // btnUpdate.setOnClickListener {
        //     mContent.value = "最新值是:Update"
        // }
        //
        // mContent.observe(this, Observer { content ->
        //     tvContent.text = content
        // })
        // //只要在值发生改变时,就能接收到
        // mContent.observeForever { content ->
        //     tvContent.text = content
        // }
    }

    val observable = Observable<String>()

    /**
     * 送黏性事件，就是在发送事件之后再订阅该事件也能收到该事件
     */
    private var count = 0
    fun btnSendStick(v: View) {
        // mContent.value = "粘性事件"
        count++
        observable.send("aaaa ${count}")
        "aaaa  send  $count ".log()

    }

    fun btnRegisterStick(v: View) {
        // mContent.observe(this){
        //     tvContent.text = it
        // }
        observable.register(true) {
            "aaaa  observe  $it ".log()
            tvContent.text = it
        }
    }

    override fun onResume() {
        super.onResume()
        // mContent.observe(this, Observer { content ->
        //     "onResume  observe   ".log()
        //     tvContent.text = content
        // })
    }
}