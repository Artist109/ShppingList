package com.example.shoppinglist.data

import androidx.coordinatorlayout.widget.CoordinatorLayout.DefaultBehavior
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shop_items")
class ShopItemDbModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)