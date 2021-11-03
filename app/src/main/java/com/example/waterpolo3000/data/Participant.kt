package com.example.waterpolo3000.data

import androidx.room.*

@Entity(tableName = "participant")
data class Participant(
    @PrimaryKey val guid    : String,
                val game    : String,
                val player  : String,
                val cap     : String,
                val number  : Int,
                val team    : String,
                val function: Int
): BaseEntity()