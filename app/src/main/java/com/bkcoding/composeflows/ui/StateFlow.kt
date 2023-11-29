package com.bkcoding.composeflows.ui

import android.util.Log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
fun stateFlowProducer():Flow<Int> {
    val mutableStateFlow = MutableStateFlow<Int>(0)
    GlobalScope.launch {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.forEach {
            mutableStateFlow.emit(it)
            Log.d("BK-Coding", "$it")
            delay(500)
        }
    }
    return mutableStateFlow
}

@OptIn(DelicateCoroutinesApi::class)
fun stateFlowConsumer() {
    GlobalScope.launch {
        val data = stateFlowProducer()
        delay(6000)
         data.collect{
             Log.d("BK-Coding-Consumer", "$it")
         }
    }

}