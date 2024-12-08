package com.ykim.mememaster.domain

interface HistoryManager<T> {
    fun add(state: T)
    fun undo(): T
    fun redo(): T
    fun canUndo(): Boolean
    fun canRedo(): Boolean
}