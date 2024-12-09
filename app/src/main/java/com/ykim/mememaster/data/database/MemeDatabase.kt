package com.ykim.mememaster.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MemeEntity::class], version = 1)
abstract class MemeDatabase : RoomDatabase() {
    abstract val dao: MemeDao

    companion object {
        const val DATABASE_NAME = "meme_db"
    }
}