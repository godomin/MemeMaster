package com.ykim.mememaster.domain

import com.ykim.mememaster.domain.model.MemeData

interface MemeRepository {
    suspend fun upsertMeme(memeData: MemeData)
    suspend fun deleteMemes(memeData: List<MemeData>)
    suspend fun getMemes(): List<MemeData>
}