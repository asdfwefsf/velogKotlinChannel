package com.company.velogkotlinchannel.`3-ChannelFanOutFanIn`

import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 공정한 채널 : 두 개의 코루틴에서 채널을 서로 사용할 때 공정하게 기회를 준다.

suspend fun someone(channel : Channel<String> , name : String) {
    for (comment in channel) {
        println("${name}: ${comment} , ${Thread.currentThread().name}")
        channel.send(comment.drop(1) + comment.first())
        delay(100L)
    }
}

fun main() = runBlocking {
    val channel = Channel<String>()
    launch {
        someone(channel , "민준")
    }
    launch {
        someone(channel , "서연")
    }
    channel.send("패스트 캠퍼스")
    delay(1000L)
    coroutineContext.cancelChildren()
}