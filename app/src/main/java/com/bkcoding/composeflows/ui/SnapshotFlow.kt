package com.bkcoding.composeflows.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow




@Composable
fun SnapshotFlow() {
    val counter = remember { mutableStateOf(0) }
    val flow = snapshotFlow {
        counter.value
    }
    val analyticsState = flow.collectAsState(0)
    Button(onClick = { counter.value++ }) {
        Text("Increment counter (${analyticsState.value})")
    }
}


@Composable
fun SnapshotFlow2() {
    val message = remember { mutableStateOf("") }
    val notificationFlow = snapshotFlow {
        message.value
    }
    val notificationState = notificationFlow.collectAsState("")
    Button(onClick = { message.value = "Hello, world!" }) {
        Text("Send notification (${notificationState.value})")
    }
}