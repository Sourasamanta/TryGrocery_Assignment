package com.example.trygrocery.ui.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trygrocery.data.CartItemEntity
import com.example.trygrocery.data.GroceryRepository
import com.example.trygrocery.data.ItemEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GroceryRepository(application)

    val cartItems: LiveData<List<CartItemEntity>> = repo.getCartItems()
    val totalItemCount: LiveData<Int> = repo.getTotalItemCount()
    val totalCost: LiveData<Double> = repo.getTotalCost()

    private val _lastOrderId = MutableLiveData<Int?>()
    val lastOrderId: LiveData<Int?> = _lastOrderId

    fun addItem(item: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repo.getCartItem(item.itemId)
            repo.upsertCartItem(
                CartItemEntity(
                    itemId = item.itemId,
                    itemName = item.itemName,
                    price = item.price,
                    unit = item.unit,
                    itemImage = item.itemImage,
                    categoryId = item.categoryId,
                    quantity = (existing?.quantity ?: 0) + 1
                )
            )
        }
    }

    fun removeOne(item: ItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val existing = repo.getCartItem(item.itemId) ?: return@launch
            if (existing.quantity > 1) {
                repo.upsertCartItem(existing.copy(quantity = existing.quantity - 1))
            } else {
                repo.removeCartItem(existing)
            }
        }
    }

    fun incrementCartItem(cart: CartItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.upsertCartItem(cart.copy(quantity = cart.quantity + 1))
        }
    }

    fun decrementCartItem(cart: CartItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cart.quantity > 1) repo.upsertCartItem(cart.copy(quantity = cart.quantity - 1))
            else repo.removeCartItem(cart)
        }
    }

    fun deleteCartItem(cart: CartItemEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.removeCartItem(cart)
        }
    }

    fun placeOrder(name: String, address: String, phone: String, payment: String) {
        val items = cartItems.value ?: return
        val total = totalCost.value ?: 0.0
        viewModelScope.launch(Dispatchers.IO) {
            val orderId = repo.placeOrder(name, address, phone, payment, items, total)
            repo.clearCart()
            _lastOrderId.postValue(orderId.toInt())
        }
    }

    fun clearLastOrderId() {
        _lastOrderId.value = null
    }
}
