package com.company.velogkotlinchannel.`3-ChannelFanOutFanIn`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 채널을 '1:N|||N:1'로 다룰 수 있는 FanOut과 FanIn
// FanOut : 여러 코루틴이 동시에 채널을 구독하는 것을 말한다.


@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceNumbers() = produce<Int> {
    var x = 1
    while (true) {
        send(x++)
        delay(100L)
    }
}

fun CoroutineScope.processNumber(id : Int , channel : ReceiveChannel<Int>) = launch {
    channel.consumeEach {
        println("${id}가 ${it}을 받았습니다.")
    }
}

fun main() = runBlocking {
    val producer = produceNumbers()
    repeat(5) {
        processNumber(it, producer) // 5개의 코루틴에서 producer 채널을 구독한다.
    }
    delay(1000L)
    producer.cancel()
}