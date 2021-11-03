package com.example.waterpolo3000.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @PrimaryKey val guid: String
): BaseEntity() {
    var numberOfGameSection    = 0
    var lengthOfGameSection    = 0
    var lengthOfShotclockLong  = 0
    var lengthOfShotclockShort = 0
    var lengthOfMainBreak      = 0
    var lengthOfSecondaryBreak = 0
    var lengthOfTimout         = 0
    var numberOfTimeout        = 0
    var lengthOfField          = 0
    var widthOfField           = 0
    var depthOfField           = 0
}
