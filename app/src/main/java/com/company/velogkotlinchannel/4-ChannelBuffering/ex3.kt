package com.company.velogkotlinchannel.`4-ChannelBuffering`

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

// 버퍼의 오버 플로우

// DROP_OLDEST : 오래된 것 버림 , SUSPEND : send 측이 잠 들었다가 깨어나 , DROP_LATEST : 처리하지 못한 새로운 데이터를 지워

fun main() = runBlocking<Unit> {
    val channel = Channel<Int>(2, BufferOverflow.DROP_LATEST)
    launch {
        for (x in 1..50) {
            channel.send(x)
        }
        channel.close()
    }

    delay(500L)

    for (x in channel) {
        println("${x} 수신")
        delay(100L)
    }
    println("완료")
}

