package com.example.waterpolo3000.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team")
data class Team(
    @PrimaryKey val guid: String,
): BaseEntity(){
    var teamName: String = ""
    var teamLocation: String = ""
}