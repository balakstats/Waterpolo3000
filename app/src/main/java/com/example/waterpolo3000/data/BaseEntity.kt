package com.example.waterpolo3000.data

import androidx.room.PrimaryKey

abstract class BaseEntity(
    var deleted    : Boolean = false,
    var created    : Long    = System.currentTimeMillis(),
    var lastUpdated: Long    = 0
)