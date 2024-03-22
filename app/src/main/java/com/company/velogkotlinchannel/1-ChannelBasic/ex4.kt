package com.company.velogkotlinchannel.`1-ChannelBasic`

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// Channel Producer
// 생산자와 소비자는 굉장히 일반적인 패턴입니다.
// 따라서 코틀린에는 채널을 이용해서 한 쪽에서 데이터를 만들고 다를 쪽에서 받는 것을 도와주는 확장 함수들이 있습니다.
// 1. produce : Coroutine을 만들고 채널을 제공한다. 2. consumeEach : 채널에서 반복해서 데이터를 받아간다.

// produce 확장 함수를 사용하게 되면 , ProducerScope가 만들어지게 되고 'CoroutineScope 인터페이스'와 'SendChannel 인터페이스'를 함께 상속 받는다.
// 그래서 '코루틴 컨텍스트와 채널'의 몇가지 인터페이스를 사용 할 수 있는 특이한 Scope이다.
// 즉, produce를 사용하면 'ProducerScope'를 상속 받은 'ProducerCoroutine'를 얻게 된다.

// cf. 일반적으로는 CoroutineScope를 상속 받아서 코루틴이 만들어진다.
// 예를 들면 runBlocking은 BlockingCoroutine을 사용하는데 이는 AbstractCoroutine을 상속 받고 , AbstractCotoutine은 CoroutineScope를 상속 받고 있다.

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking { 
    val oneToTen = produce { // produce에서 ProducerScope(= CoroutineScope + SendChannel)를 만들어준다.
        // 즉, this.send도 가능하고 , this.coroutineContext도 가능하다.
        for (x in 1..7) {
            this.send(x) // channel.send와 같은거야.
        }
    }
    oneToTen.consumeEach { 
        println(it)
    }
    println("완료")
}