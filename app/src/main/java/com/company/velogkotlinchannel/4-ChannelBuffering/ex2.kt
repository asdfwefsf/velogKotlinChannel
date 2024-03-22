package com.company.velogkotlinchannel.`4-ChannelBuffering`

// 버퍼의 사이즈를 랑데뷰(Channel.RENDEZVOUS)로 지정 할 수 있다. : 실제로는 0을 지정하는거야. -> 버퍼가 없는 것을 '랑데뷰'라고 부른다.
// 랑데뷰 이외에도 UNLIMITED : 무제한 설정 , CONFLATED : 처리하지 못한 오래된 값이 버림. , BUFFERED : 64개의 버퍼 (오버하면 suspend)
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*


fun main() = runBlocking<Unit> {
    val channel = Channel<Int>(Channel.RENDEZVOUS)
    launch {
        for (x in 1..20) {
            println("${x} 전송중")
            channel.send(x)
        }
        channel.close()
    }

    for (x in channel) {
        println("${x} 수신")
        delay(100L)
    }
    println("완료")
}
