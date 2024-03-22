package com.company.velogkotlinchannel.`1-ChannelBasic`

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 같은 코루틴에서 채널을 읽고 쓴다면? : send||receive가 suspension point이고 서로에게 의존적이기 때문에 같은 코루틴에서 사용하는 것은 위험 할 수 있다.

// 아래의 코드를 실행하면 무한으로 대기하는 것처럼 send와 receive를 같은 코루틴에서 사용한다면 해당 코루틴이 영원히 잠들게 된다.
// 이는 send와 receive가 반대쪽이 준비가 되지 않으면 잠드는 suspend function이기 때문이다.
fun main() = runBlocking<Unit> {
    val channel = Channel<Int>()
    launch {
        for (x in 1..10) {
            channel.send(x)
        }
        repeat(10) {
            println(channel.receive())
        }
        println("완료")
    }
}

