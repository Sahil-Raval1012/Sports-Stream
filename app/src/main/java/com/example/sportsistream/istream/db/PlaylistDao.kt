package com.example.sportsistream.istream.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaylistDao {

    @Insert
    suspend fun insert(item: PlaylistEntity)

    @Query("SELECT * FROM playlist WHERE userId = :userId ORDER BY id DESC")
    suspend fun getByUserId(userId: Int): List<PlaylistEntity>

    @Query("SELECT COUNT(*) FROM playlist WHERE userId = :userId AND url = :url")
    suspend fun countByUserAndUrl(userId: Int, url: String): Int

    @Delete
    suspend fun delete(item: PlaylistEntity)
}
