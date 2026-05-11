package com.example.trygrocery.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.trygrocery.data.GroceryRepository
import com.example.trygrocery.data.ItemEntity
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = GroceryRepository(application)

    private val allItems = MutableLiveData<List<ItemEntity>>()
    private val _selectedCategoryId = MutableLiveData<Int?>(null)
    val selectedCategoryId: LiveData<Int?> = _selectedCategoryId

    val items: LiveData<List<ItemEntity>> = MediatorLiveData<List<ItemEntity>>().apply {
        fun refresh() {
            val list = allItems.value ?: return
            val catId = _selectedCategoryId.value
            value = if (catId == null) list else list.filter { it.categoryId == catId }
        }
        addSource(allItems) { refresh() }
        addSource(_selectedCategoryId) { refresh() }
    }

    init {
        loadAll()
    }

    private fun loadAll() {
        viewModelScope.launch {
            allItems.value = repo.getAllItems()
        }
    }

    fun filterByCategory(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
    }
}
