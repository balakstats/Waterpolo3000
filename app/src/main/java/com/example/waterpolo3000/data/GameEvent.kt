package com.example.waterpolo3000.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.util.*

@Entity(tableName = "gameEvent")
data class GameEvent (
    @PrimaryKey var guid         : String,
                val game         : String,
                var gameSection  : Int,
                var time         : Long,
                var participant  : String,
                var gameEventType: Int,
) : BaseEntity()