package com.company.velogkotlinchannel.`1-ChannelBasic`

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 채널 close : 채널에서 더 이상 보낼 자료가 없으면 , close 메서드를 이용해 채널을 닫을 수 있다.
// 채널은 for in 을 이용해서 반복적으로 receive 할 수 있고 close 되면 for in은 자동으로 종료가 된다.

fun main() = runBlocking {
    val channel = Channel<Int>(5)
    launch {
        for (x in 1..10) {
            channel.send(x)
        }
        channel.close()
    }
//    repeat(10) {
//        println(channel.receive())
//    }
    for (x in channel) {
        println(x)
    }
    println("완료")
}

