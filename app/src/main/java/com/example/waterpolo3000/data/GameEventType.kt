package com.example.waterpolo3000.data

import androidx.room.*

@Entity(tableName = "gameEventTypes")
data class GameEventType(
    @PrimaryKey val id: Int,
    val name: String,
    val nameShort: String
)