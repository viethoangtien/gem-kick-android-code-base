package com.gem.mediaplayers.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by taind-201 on 11/16/2020.
 */
class TimeUtilsTest {

    @Test
    fun yearDiffFromToday_returnTrue() {
        val startDay = "2010-07-12"
        val outputStream = TimeUtils.yearDiffFromToday(startDay)
        assertEquals(outputStream, 10)
    }

    @Test
    fun convertLongToIntervalString() {
        //now
        val input1 = 59L
        // 1 minute
        val input2 = 100L
        // 3 minutes
        val input3 = 60 * 3 + 40
        // 1 hour
        val input4 = 60 * 60 + 59
        // 23 hours
        val input5 = 60 * 60 * 23 + 40
        // 1 day
        val input6 = 60 * 60 * 24 + 60 * 60 * 23
        // 6 days
        val input7 = 60 * 60 * 24 * 6 + 60 * 60 * 23
        // 1 month
        val input8 = 60 * 60 * 24 * 30 + 60 * 60 * 23
        // 6 month
        val input9 = 60 * 60 * 24 * 30 * 6 + 60 * 60 * 23
        // 6 month
        val input10 = 60 * 60 * 24 * 365 + 60 * 60 * 23
    }

}