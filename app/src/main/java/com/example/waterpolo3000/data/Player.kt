package com.example.waterpolo3000.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player")
data class Player(
    @PrimaryKey val guid: String,
): BaseEntity(){
    var playerFirstName = "Vorname"
    var playerLastName = "Nachname"
    var playerLicense = 0
}