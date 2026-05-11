package com.example.trygrocery.data

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithItems(
    @Embedded
    val category: CategoryEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "categoryId"
    )
    val items: List<ItemEntity>
)