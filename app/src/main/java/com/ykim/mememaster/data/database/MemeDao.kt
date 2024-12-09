package com.ykim.mememaster.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MemeDao {

    @Upsert
    suspend fun upsertMeme(memeEntity: MemeEntity)

    @Delete
    suspend fun deleteMeme(memeEntity: MemeEntity)

    @Delete
    suspend fun deleteMemes(memeEntities: List<MemeEntity>)

    @Query("SELECT * FROM MemeEntity ORDER BY timestamp DESC")
    suspend fun getMemes(): List<MemeEntity>

}