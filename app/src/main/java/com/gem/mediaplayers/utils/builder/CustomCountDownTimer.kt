package com.gem.mediaplayers.utils.builder

import android.os.CountDownTimer

open class CustomCountDownTimer private constructor(millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval) {

    data class Builder(
        private var totalTime: Long = 0,
        private var unitTime: Long = 1000,
        private var onTickListener: ((Long) -> Unit)? = null,
        private var onFinish: (() -> Unit)? = null
    ) {
        fun setTotalTime(totalTime: Long) = apply {
            this.totalTime = totalTime
        }

        fun setUnitTime(unitTime: Long) = apply {
            this.unitTime = unitTime
        }

        fun setOnTickListener(func: (Long) -> Unit) = apply {
            this.onTickListener = func
        }

        fun setOnFinishListener(func: () -> Unit) = apply {
            this.onFinish = func
        }

        fun build(): CustomCountDownTimer = object : CustomCountDownTimer(totalTime, unitTime) {
            override fun onFinish() {
                onFinish?.invoke()
            }

            override fun onTick(millisUntilFinished: Long) {
                onTickListener?.invoke(millisUntilFinished / unitTime)
            }
        }
    }

    fun cancelCountDown() {
        cancel()
    }

    override fun onFinish() {

    }

    override fun onTick(p0: Long) {

    }
}