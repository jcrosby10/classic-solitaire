package com.huntergaming.classicsolitaire.data

import com.huntergaming.classicsolitaire.web.RequestState
import kotlinx.coroutines.flow.Flow

interface Dao<T> {
    suspend fun create(data: T): Flow<RequestState>
    fun update(data: T)
    fun get(): T
    fun delete()
}