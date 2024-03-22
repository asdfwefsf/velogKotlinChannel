package com.company.velogkotlinchannel.`2-ChannelPipeLine`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

// 파이프 라인 : 하나의 코루틴(프로듀서)이 데이터 스트릠을 생성하고 , 다른 코루틴(소비자)가 받아서 처리하거나 변형해서 새로운 스트림을 생성하는 패턴
// - 파이프 라인을 이용하면, 두 가지 이상의 채널을 연결해서 사용 할 수 있다.
// - 간단히 말하면 파이프 라인은 채널을 연속으로 붙힐 수 있는 것을 의미한다.

// 아래 코드에서는 첫번째 채널이 1~5를 만들고 , 두번째 채널이 첫번째 채널을 이용해서 1!~5!를 만들어 내고, 마지막으로 그것을 활용하는 형태를 만들 수 있다.
// 이렇게 여러개의 채널이 순차적으로 무언가를 하는 것을, 마치 파이프와 연결된 파이프라인 같다고 한다.
@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceNumbers() = produce<Int> {
 // CoroutineScope의 확장함수로 만들었으므로 , produce를 만들때 별도의 코루틴 없이 사용 할 수 있다. (코드를 한 번만 사용하고자 할 때 유용하다.)
    var x = 1
    while (true) {
        this.send(x++)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun CoroutineScope.produceStringNumbers(numbers : ReceiveChannel<Int>) : ReceiveChannel<String> = produce {
    for (i in numbers) {
        send("${i}!")
    }
}
fun main() = runBlocking { 
    val numbers = produceNumbers()
    // 'numbers'는 [receive 채널이야. : receive 메서드 이용 가능] , [send 채널 아니야. : send 메서드 이용 불가능]
    // 따라서 numbers는 값을 확인 할 수 만 있고 , 따로 값을 보낼 수 는 없다.
    // cf. channel은 receive 채널과 send 채널 둘 다 사용 가능.
    val stringNumbers = produceStringNumbers(numbers)
    repeat(5) {
        println(stringNumbers.receive())
    } // 위의 두개의 채널 모두 close가 없기 때문에 for 문을 사용 할 수 없다. 그래서 명시적으로 횟수를 정해두고, receive 함수를 명시적으로 호출을 해야한다.
    println("완료")
    coroutineContext.cancelChildren() // 명시적으로 CoroutineContext에게 취소를 명령해서 produceNumbers()와 produceStringNumbers()를 취소를 시킨다.
}