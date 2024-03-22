package com.company.velogkotlinchannel.`1-ChannelBasic`

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// 채널은 일종의 파이프 라인 같은 것으로 보면 된다. 한쪽에서 데이터를 넣으면 다른 한쪽에서 데이터를 받는것을 의미한다.
// 채널은 두개 이상의 코루틴에서 안정적이고 효율적으로 데이터를 교환 할 수 있는 방법이야.

// Channel : 채널은 일종의 파이프이다. 송신측에서 채널에 send로 데이터를 전달하고 수신 측에서 채널을 통해 receive 받습니다.

// 채널을 통해서 데이터를 주고 받을때는 각각 channel.send()와 channel.receive()를 사용하며, send()와 receive()는 모두 suspend function이다.

fun main() = runBlocking { 
    val channel = Channel<Int>()
    launch { 
        for (x in 1..10) {
            channel.send(x) // 수신측이 없다면, 잠이 들었다가 받은 이후에 깨어나서 다음 데이터를 보낸다.
        }
    }
    repeat(10) {
        println(channel.receive()) // 채널에 데이터가 없다면, 잠이 들었다가 채널에 데이터가 들어온 이후에 깨어나서 수행한다.
    }
    println("완료")
}