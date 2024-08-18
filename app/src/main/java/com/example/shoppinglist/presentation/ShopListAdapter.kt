package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopListViewHolder>(ShopItemDiffCallback()) {
//class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>() {

//    Можно удалить всю логику работы с адаптером из-за ListAdapter и DiffUtils
//    var shopList = listOf<ShopItem>()
//        set(value) {
//            val callback = ShopListDiffCallback(shopList, value)
//            val diffResult = DiffUtil.calculateDiff(callback)
//            diffResult.dispatchUpdatesTo(this)
//            field = value
//        }
    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClick: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopListViewHolder {
//        Log.d("ShopListAdapter", "viewType: $viewType")
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType: ${viewType}")
        }

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopListViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ShopListViewHolder, position: Int) {
//        Достаем элемент из ListAdapter и DiffUtils
//        val item = shopList[position]
        val item = getItem(position)
        viewHolder.name.text = "${item.name}"
        viewHolder.count.text = item.count.toString()

        viewHolder.view.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(item)
            true
        }

        viewHolder.view.setOnClickListener {
            onShopItemClick?.invoke(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
//        val item = shopList[position]
        val item = getItem(position)
        return if (item.enabled) {
            VIEW_TYPE_ENABLED
        } else {
            VIEW_TYPE_DISABLED
        }
    }

//    Уже реализовано под капотом ListAdapter
//    override fun getItemCount(): Int {
//        return shopList.size
//    }

//    class ShopListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
//        val name = view.findViewById<TextView>(R.id.tv_name)
//        val count = view.findViewById<TextView>(R.id.tv_count)
//    }

    companion object {
        const val VIEW_TYPE_ENABLED = 1
        const val VIEW_TYPE_DISABLED = 0
        const val MAX_POOL_SIZE = 15
    }
}