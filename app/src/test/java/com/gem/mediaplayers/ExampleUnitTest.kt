package com.gem.mediaplayers

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun kidLevel_isCorrect() {
        val levels = mutableListOf(0, 20000, 100000, 500000, 1000000)
        val input1 = 22000
        val expected = 1
        var output1 = -1
        while (output1 < levels.size && input1 > levels[output1]) output1 ++
        assertEquals(expected, output1)
    }
}
