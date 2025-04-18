package com.bvc.ordering.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactsDao {
    @Query("SELECT * FROM tb_contacts")
    fun getAll(): List<Contacts>

    @Insert
    fun insert(vararg contacts: Contacts)

    @Delete
    fun delete(contacts: Contacts)

    @Query("SELECT * FROM tb_contacts WHERE id Like :id")
    fun findByResult(id: Int): Contacts
}