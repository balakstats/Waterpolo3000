package com.example.waterpolo3000.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "game")
class Game (
    @PrimaryKey val guid: String
): BaseEntity() {
    var competition    : String = ""
    var competitionType: Int    = 0
    var gameStart      : Long   = 0
    var gameEnd        : Long   = 0
    var gameLocation   : String = ""
    var settings       : String = ""
}