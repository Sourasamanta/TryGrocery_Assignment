package com.example.trygrocery.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["categoryId"])]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,

    val categoryId: Int,
    val itemName: String,
    val price: Double,
    val unit: String,
    val itemImage: String,
    val description: String = "",
    val isAvailable: Boolean = true
)