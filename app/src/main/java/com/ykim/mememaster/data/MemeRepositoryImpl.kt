package com.ykim.mememaster.data

import com.ykim.mememaster.data.database.MemeDao
import com.ykim.mememaster.data.mapper.toMemeData
import com.ykim.mememaster.data.mapper.toMemeEntity
import com.ykim.mememaster.domain.MemeRepository
import com.ykim.mememaster.domain.model.MemeData
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val memeDao: MemeDao
) : MemeRepository {
    override suspend fun upsertMeme(memeData: MemeData) {
        memeDao.upsertMeme(memeData.toMemeEntity())
    }

    override suspend fun deleteMemes(memeData: List<MemeData>) {
        memeDao.deleteMemes(memeData.map { it.toMemeEntity() })
    }

    override suspend fun getMemes(): List<MemeData> {
        return memeDao.getMemes().map { it.toMemeData() }
    }
}