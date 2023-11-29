package com.bkcoding.composeflows.ui

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@OptIn(DelicateCoroutinesApi::class)
fun sharedFlow() {
    val mutableSharedFlow = MutableSharedFlow<Int>()
    GlobalScope.launch {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.forEach {
            mutableSharedFlow.emit(it)
            delay(1000)
        }
    }
    GlobalScope.launch {
        mutableSharedFlow.collect {
            Log.d("BK-Coding", "${it}")
        }
    }
    GlobalScope.launch {
        delay(3000)
        mutableSharedFlow.collect {
            Log.d("BK-Coding2", "${it}")
        }
    }
}


fun sharedFlow2() = runBlocking {
    val sharedFlow = MutableSharedFlow<Int>()
    launch {
        repeat(5) {
            delay(1000)
            sharedFlow.emit(it)
        }
    }

    launch {
        sharedFlow.collect { value ->
            Log.d("BK-Coding", "Collector 1 received: $value")
        }
    }
    launch {
        delay(4000)
        sharedFlow.collect { value ->
            Log.d("BK-Coding", "Collector 2 received: $value")
        }
    }
    delay(5000)
}






