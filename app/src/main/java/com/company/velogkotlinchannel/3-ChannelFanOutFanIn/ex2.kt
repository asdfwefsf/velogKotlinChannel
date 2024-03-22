package com.company.velogkotlinchannel.`3-ChannelFanOutFanIn`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// FanIn : 생산자가 많은 것을 의미한다.

suspend fun produceNumbers(channel : SendChannel<Int> , from : Int , interval : Long) {
    var x = from
    while (true) {
        channel.send(x)
        x += 2
        delay(interval)
    }
}

fun CoroutineScope.processNumber(channel: ReceiveChannel<Int>) = launch {
    channel.consumeEach {
        println("${it}을 받았습니다.")
    }
}

fun main() = runBlocking {
    val channel = Channel<Int>()
    launch {
        produceNumbers(channel , 1 , 100L)
    }
    launch {
        produceNumbers(channel , 2 , 150L)
    }
    processNumber(channel)
    delay(1000L)
    coroutineContext.cancelChildren()
}
