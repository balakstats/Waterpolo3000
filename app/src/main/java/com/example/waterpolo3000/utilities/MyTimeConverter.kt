package com.example.waterpolo3000.utilities

class MyTimeConverter {
    companion object{
        fun getMinutesFromLong(value: Long): Int {
            return ((value/1000)/60).toInt()
        }

        fun getSecondsFromLong(value: Long): Int {
            return ((value/1000)%60).toInt()
        }

        fun getSecondsSmallFromLong(value: Long): Int {
            return ((value/100)%10).toInt()
        }
    }
}