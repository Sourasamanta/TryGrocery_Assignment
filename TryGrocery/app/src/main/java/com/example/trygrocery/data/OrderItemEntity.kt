package com.example.trygrocery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_items")
data class OrderItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val itemId: Int,
    val itemName: String,
    val price: Double,
    val quantity: Int,
    val unit: String
)
