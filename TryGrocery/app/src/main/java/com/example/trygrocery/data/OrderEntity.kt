package com.example.trygrocery.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val orderDate: Long,
    val totalAmount: Double,
    val customerName: String,
    val deliveryAddress: String,
    val phoneNumber: String,
    val paymentMethod: String,
    val status: String = "Placed"
)
