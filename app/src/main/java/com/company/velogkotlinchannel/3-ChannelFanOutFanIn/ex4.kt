package com.company.velogkotlinchannel.`3-ChannelFanOutFanIn`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

// Channel + select : 먼저 끝난 요청을 처리하고자 할 때
// channel에 대해서만 onReceive를 사용하는 것 이외에도
// Job:onJoin , Deffered:onAwait , SendChannel:onSend , ReceiveChannel:onReceive,onReceiveCatching , delay:onTimeout
// 이렇게 사용 할 수 있다.

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.sayFast() = produce<String> {
    while (true) {
        delay(100L)
        send("패스트")
    }
}
@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.sayCampus() = produce<String> {
    while (true) {
        delay(150L)
        send("캠퍼스")
    }
}

fun main() = runBlocking {
    val fasts = sayFast()
    val campus = sayCampus()
    repeat(5) {
        select {// 먼저 끝내는 애만 듣겠다.
            fasts.onReceive {
                println("fast: $it")
            }
            campus.onReceive {
                println("campus: $it")
            }
        }
    }
    coroutineContext.cancelChildren()
}