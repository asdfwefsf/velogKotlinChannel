package com.company.velogkotlinchannel.`2-ChannelPipeLine`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// 실용성 X : 단지, 파이프라인을 이런식으로도 확장 할 수 있다는 것이 중요한거야.
@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.numbersFrom(start : Int) = produce<Int> {
    var x = start
    while (true) {
        send(x++)
    }
}

fun CoroutineScope.filter(numbers : ReceiveChannel<Int> , prime : Int) : ReceiveChannel<Int> = produce {
    for (i in numbers) {
        if (i % prime != 0) {
            send(i)
        }
    }
}

fun main() = runBlocking {
    var numbers = numbersFrom(2) // 대체되는 채널

    repeat(10) {
        val prime = numbers.receive()
        println(prime)
        numbers = filter(numbers , prime)
    }
    println("완료")
    coroutineContext.cancelChildren()
}

