package com.example.trygrocery.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): LiveData<List<CartItemEntity>>

    @Query("SELECT * FROM cart_items WHERE itemId = :itemId")
    suspend fun getCartItem(itemId: Int): CartItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: CartItemEntity)

    @Delete
    suspend fun delete(item: CartItemEntity)

    @Query("DELETE FROM cart_items")
    suspend fun clearAll()

    @Query("SELECT COALESCE(SUM(quantity), 0) FROM cart_items")
    fun getTotalCount(): LiveData<Int>

    @Query("SELECT COALESCE(SUM(price * quantity), 0.0) FROM cart_items")
    fun getTotalCost(): LiveData<Double>
}
