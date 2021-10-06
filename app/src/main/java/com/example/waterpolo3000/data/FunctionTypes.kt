package com.example.waterpolo3000.data

import androidx.room.*

@Entity(tableName = "functionTypes")
data class FunctionTypes(
    @PrimaryKey val id: Int,
    val name: String
)