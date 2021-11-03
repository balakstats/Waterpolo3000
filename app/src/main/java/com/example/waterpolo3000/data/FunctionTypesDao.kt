package com.example.waterpolo3000.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunctionTypesDao {
    @Query("SELECT * FROM functionTypes")
    fun getAll(): Flow<List<FunctionTypes>>

    @Query("SELECT COUNT(*) FROM functionTypes")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(functionType: List<FunctionTypes>)
}