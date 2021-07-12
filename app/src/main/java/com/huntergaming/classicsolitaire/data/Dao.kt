package com.huntergaming.classicsolitaire.data

interface Dao<T> {
    fun create(data: T)
    fun update(data: T)
    fun get(): T
    fun delete()
}