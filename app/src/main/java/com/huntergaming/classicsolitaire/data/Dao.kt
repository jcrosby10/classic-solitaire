package com.huntergaming.classicsolitaire.data

interface Dao<T> {
    fun add(data: T)
    fun update(data: T)
    fun get()
    fun delete()
}