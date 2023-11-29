package com.bkcoding.composeflows

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bkcoding.composeflows.ui.SnapshotFlow
import com.bkcoding.composeflows.ui.SnapshotFlow2
import com.bkcoding.composeflows.ui.sharedFlow
import com.bkcoding.composeflows.ui.sharedFlow2
import com.bkcoding.composeflows.ui.stateFlowConsumer
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SnapshotFlow2()
        }
        GlobalScope.launch {
//            sharedFlow()
//            sharedFlow2()
//            stateFlowConsumer()
        }
    }
}

//-----Children of supervisor scope/job can fail independently of each other------//
//-----A failure or cancellation of a child does not cause Supervisor Scope or its other
// children to fail----//


//--Supervisor Scope--//
@OptIn(DelicateCoroutinesApi::class)
fun main() {

    val parentJob = GlobalScope.launch(handler) {

        supervisorScope {
            // --------- JOB A ---------
            val jobA = launch {
                val resultA = getResult(1)
                println("ResultA: ${resultA}")
            }

            // --------- JOB B ---------
            val jobB = launch(childExceptionHandler) {
                val resultB = getResult(2)
                println("ResultB: ${resultB}")
            }

            // --------- JOB C ---------
            val jobC = launch {
                val resultC = getResult(3)
                println("ResultC: ${resultC}")
            }
        }
    }

    parentJob.invokeOnCompletion { throwable ->
        if (throwable != null) {
            println("Parent job failed: ${throwable}")
        } else {
            println("Parent job SUCCESS")
        }
    }
}

suspend fun getResult(number: Int): Int {
    return withContext(Dispatchers.IO) {
        delay(number * 500L)
        if (number == 2) {
            throw Exception("Error getting result for number: ${number}")
        }
        number * 2
    }
}

private fun println(message: String) {
    Log.d(TAG, message)
}

val handler = CoroutineExceptionHandler { _, exception ->
    println("Exception thrown somewhere within parent or child: $exception.")
}

val childExceptionHandler = CoroutineExceptionHandler { _, exception ->
    println("Exception thrown in one of the children: $exception.")
}

//----------End-----------//


private fun producer(): Flow<Int> {
    return flow {
        val list = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        list.forEach {
            delay(1500)
            emit(it)
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun consumer1() {
    val data: Flow<Int> = producer()
    GlobalScope.launch {
        data.collect {
            Log.d("BK-Coding", "${it}")
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
fun consumer2() {
    val data: Flow<Int> = producer()
    GlobalScope.launch {
        data.collect {
            delay(1000)
            Log.d("BK-Coding1", "${it}")
        }
    }
}































