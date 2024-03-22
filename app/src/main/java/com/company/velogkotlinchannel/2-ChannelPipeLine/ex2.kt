package com.company.velogkotlinchannel.`2-ChannelPipeLine`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun CoroutineScope.produceNumbers2() = produce<Int> {// 1, 2, 3, ... , 반환하는 것은 리시브 채널
    var x = 1
    while (true) {
        send(x++)
    }
}

fun CoroutineScope.filterOdd(numbers : ReceiveChannel<Int>) : ReceiveChannel<String> = produce {
    for (i in numbers) {
        if (i % 2 == 1) {
            send("${i}!")
        }
    }
}

fun main() = runBlocking {
    val numbers = produceNumbers2() // 리시브 채널이야 : send 불가.
    val oddNumbers = filterOdd(numbers)
    repeat(10) {
        println(oddNumbers.receive())
    }
    coroutineContext.cancelChildren()
}

