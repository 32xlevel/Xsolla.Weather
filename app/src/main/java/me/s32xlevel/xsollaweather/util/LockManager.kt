package me.s32xlevel.xsollaweather.util

import android.os.Handler
import android.os.Looper
import androidx.annotation.UiThread

object LockManager {

    var lockCallback: LockCallback? = null
        set(value) {
            field = value
            if (lockCounter > 0) {
                executeLock()
            } else {
                executeUnlock()
            }
        }

    private val mHandler by lazy { Handler(Looper.getMainLooper()) }

    // Такая имплементация позволяет выполнять сразу несколько асинхронных задач,
    // блокировка экрана будет снята когда завершится последний процесс
    @Volatile
    private var lockCounter: Int = 0
        set(value) {
            synchronized(this@LockManager) {
                // Если не было задач - запускаем блокировку
                if (field == 0 && value > 0) {
                    executeLock()
                }
                // если задачи закончились - блокировку снимаем
                if (field > 0 && value == 0) {
                    executeUnlock()
                }
                field = value
                check(field >= 0) { executeUnlock() }
            }
        }

    private fun executeLock() {
        lockCallback?.let {
            mHandler.post {
                it.lock()
            }
        }
    }

    private fun executeUnlock() {
        lockCallback?.let {
            mHandler.post {
                it.unlock()
            }
        }
    }

    fun changeLockState(lockState: Boolean) {
        if (lockState) lockCounter++
        else lockCounter--
    }
}

interface LockCallback {
    @UiThread
    fun lock()

    @UiThread
    fun unlock()
}