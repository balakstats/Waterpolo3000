package com.example.waterpolo3000.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameEventTypeDao {
    @Query("SELECT * FROM gameEventTypes")
    fun getAll(): Flow<List<GameEventType>>

    @Query("SELECT * FROM gameEventTypes WHERE id == :id")
    suspend fun getTypeById(id: Int): GameEventType

    @Query("SELECT COUNT(*) FROM gameEventTypes")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(eventType: List<GameEventType>)
}