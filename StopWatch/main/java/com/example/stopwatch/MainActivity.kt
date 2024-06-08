package com.example.stopwatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mainView: ActivityMainBinding? = null

    private var isRunning = false // 스톱워치가 현재 실행되고 있는가?
    private var timer:Timer? = null
    private var time = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainView = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainView!!.root)

        mainView!!.btnStart.setOnClickListener(this)
        mainView!!.btnRefresh.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            mainView!!.btnStart.id -> {
                if(isRunning) {
                    pause() //일시정지
                } else {
                    start() //시작
                }
            }
            mainView!!.btnRefresh.id -> {
                refresh() //초기화
            }
        }
    }

    private fun start(){
        mainView!!.btnStart.text = "일시정지"
        mainView!!.btnStart.setBackgroundColor(getColor(R.color.red))
        isRunning = true

        timer = timer(period = 10){
            // 0.01초마다 실행
            time++

            val milliSecond = time % 100
            val second = (time % 6000) / 100
            val minute = time / 6000

            runOnUiThread {
                if(isRunning) {
                    mainView!!.tvMillisecond.text = if(milliSecond<10) ".0${milliSecond}" else ".${milliSecond}"
                    mainView!!.tvSecond.text = if(second<10) ":0${second}" else ":${second}"
                    mainView!!.tvMinute.text = if(minute<10) "0$minute" else ":${minute}"
                }
            }
        }
    }

    private fun pause(){
        mainView!!.btnStart.text = "시작"
        mainView!!.btnStart.setBackgroundColor(getColor(R.color.blue))

        isRunning = false
        timer?.cancel()
    }

    private fun refresh(){
        timer?.cancel()

        mainView!!.btnStart.text = "시작"
        mainView!!.btnStart.setBackgroundColor(getColor(R.color.blue))
        isRunning = false

        time = 0
        mainView!!.tvMillisecond.text = ".00"
        mainView!!.tvSecond.text = ":00"
        mainView!!.tvMinute.text = "00"
    }
}