package com.ykim.mememaster.data

import com.ykim.mememaster.domain.HistoryManager
import javax.inject.Inject

class HistoryManagerImpl<T> @Inject constructor(
    private val maxSize: Int
) : HistoryManager<T> {
    private val undoStack = ArrayDeque<T>()
    private val redoStack = ArrayDeque<T>()

    override fun add(state: T) {
        redoStack.clear()
        undoStack.addLast(state)
        if (undoStack.size > maxSize) {
            undoStack.removeFirst()
        }
    }

    override fun undo(): T {
        if (canUndo()) {
            redoStack.addLast(undoStack.removeLast())
            return undoStack.last()
        } else {
            throw IllegalStateException("Undo stack is empty")
        }
    }

    override fun redo(): T {
        if (canRedo()) {
            undoStack.addLast(redoStack.removeLast())
            return undoStack.last()
        } else {
            throw IllegalStateException("Redo stack is empty")
        }
    }

    override fun canUndo(): Boolean {
        return undoStack.size >= 2
    }

    override fun canRedo(): Boolean {
        return redoStack.isNotEmpty()
    }
}