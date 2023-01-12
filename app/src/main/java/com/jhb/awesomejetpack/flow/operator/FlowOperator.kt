package com.jhb.awesomejetpack.flow.operator

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * @author jianghaibo
 * @date 2023/1/11
 */

fun main() {

    "start".log()

    runBlocking(Dispatchers.Default) {
        "this = $this".log()
        val nums = flowOf(1, 2, 3, 4, 5)
        // map(nums)
        // filter(nums)
        // onEach(nums)
        // debounce()
        // sample()
        // reduce()
        // fold()
    }

    "end".log()
}

private suspend fun fold() {
    flow {
        for (i in 'A'..'Z') {
            emit(i.toString())
        }
    }.fold("字母表:") { accumulator, value ->
        "fold $accumulator $value = ${accumulator + value}".log()
        accumulator + value
    }.log()
}

private suspend fun reduce() {
    flow {
        for (i in 1..100) {
            emit(i)
        }
    }.reduce { accumulator, value ->
        "reduce $accumulator $value = ${accumulator + value}".log()
        accumulator + value
    }.toString()
        .log()
}

private fun printThreadName(tag: CharSequence? = null) {
    println("$tag ${Thread.currentThread().name}")
}

private fun CharSequence.log() {
    println("$this")
}

private suspend fun sample() {
    flow {
        var count = 0
        while (true) {
            emit("无用弹幕....${count++}")
        }
    }.sample(1000)
        .onEach {
            printThreadName("onEach")
        }
        .flowOn(Dispatchers.IO)
        .collect {
            printThreadName("collect")
            println("result = $it")
        }
}

private suspend fun debounce() {
    flow {
        emit(1)
        delay(90)
        emit(2)
        delay(90)
        emit(3)
        delay(1010)
        emit(4)
        delay(1010)
        emit(5)
    }.debounce(1000)
        .collect {
            println("$it")
        }
}

private suspend fun onEach(nums: Flow<Int>) {
    nums.filter {
        it % 2 == 0
    }.onEach {
        println("onEach = $it")
    }.map {
        it + 1
    }.collect {
        println("result = $it")
    }
}

private suspend fun map(nums: Flow<Int>) {
    nums.map {
        it + 1
    }.collect {
        println("result = $it")
    }
    println("----------------------------")
}

private suspend fun filter(nums: Flow<Int>) {
    nums.map {
        it + 1
    }.filter {
        it % 2 == 0
    }.collect {
        println("result = $it")
    }
    println("----------------------------")
}