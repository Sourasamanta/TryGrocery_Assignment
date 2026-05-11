package com.example.trygrocery.data

import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GroceryRepository(context: Context) {

    private val dao = AppDatabase.getDatabase(context).groceryDao()
    private val cartDao = AppDatabase.getDatabase(context).cartDao()
    private val orderDao = AppDatabase.getDatabase(context).orderDao()

    suspend fun seedIfEmpty() {
        withContext(Dispatchers.IO) {
            if (dao.getCategoryCount() == 0) {
                dao.insertCategories(DatabaseSeeder.getCategories())
                dao.insertItems(DatabaseSeeder.getItems())
            }
        }
    }

    suspend fun getAllItems(): List<ItemEntity> = withContext(Dispatchers.IO) {
        dao.getAllItems()
    }

    fun getCartItems(): LiveData<List<CartItemEntity>> = cartDao.getAllCartItems()

    fun getTotalItemCount(): LiveData<Int> = cartDao.getTotalCount()

    fun getTotalCost(): LiveData<Double> = cartDao.getTotalCost()

    suspend fun getCartItem(itemId: Int): CartItemEntity? = cartDao.getCartItem(itemId)

    suspend fun upsertCartItem(item: CartItemEntity) = cartDao.upsert(item)

    suspend fun removeCartItem(item: CartItemEntity) = cartDao.delete(item)

    suspend fun clearCart() = cartDao.clearAll()

    suspend fun placeOrder(
        name: String,
        address: String,
        phone: String,
        payment: String,
        items: List<CartItemEntity>,
        total: Double
    ): Long {
        val order = OrderEntity(
            orderDate = System.currentTimeMillis(),
            totalAmount = total,
            customerName = name,
            deliveryAddress = address,
            phoneNumber = phone,
            paymentMethod = payment
        )
        val orderId = orderDao.insertOrder(order)
        val orderItems = items.map {
            OrderItemEntity(
                orderId = orderId.toInt(),
                itemId = it.itemId,
                itemName = it.itemName,
                price = it.price,
                quantity = it.quantity,
                unit = it.unit
            )
        }
        orderDao.insertOrderItems(orderItems)
        return orderId
    }
}
