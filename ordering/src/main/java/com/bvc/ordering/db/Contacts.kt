package com.bvc.ordering.db

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    var id: Int,
)