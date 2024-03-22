package com.company.velogkotlinchannel.`4-ChannelBuffering`

// 채널 버퍼링 : 기본적으로 채널은 send를 하면 receive를 할 때까지 잠이 들고 , receive를 하면 send를 할 때까지 잠에 든다.
// 그러나 버퍼링을 하면 자유롭게 보낼 수 있고 자유롭게 받을 수 있고 , 중단되지 않으면서 채널을 사용 할 수 있다.

// 채널 생성자는 인자로 버퍼의 사이즈를 받을 수 있다.
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*

fun main() = runBlocking<Unit> {
    val channel = Channel<Int>(10)
    launch {
        for (x in 1..20) {
            println("${x} 전송중")
            channel.send(x) // 받든 안 받든 채널로 계속 보낸다.
        }
        channel.close()
    }

    for (x in channel) {
        println("${x} 수신")
        delay(100L)
    }
    println("완료")
}