package com.example.trygrocery.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trygrocery.R
import com.example.trygrocery.data.CartItemEntity

class CartAdapter(
    private var items: List<CartItemEntity>,
    private val onIncrement: (CartItemEntity) -> Unit,
    private val onDecrement: (CartItemEntity) -> Unit,
    private val onDelete: (CartItemEntity) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    fun updateItems(newItems: List<CartItemEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.cartItemImage)
        val productName: TextView = itemView.findViewById(R.id.cartItemName)
        val productUnit: TextView = itemView.findViewById(R.id.cartItemUnit)
        val productPrice: TextView = itemView.findViewById(R.id.cartItemPrice)
        val quantityText: TextView = itemView.findViewById(R.id.cartItemQty)
        val decrementBtn: ImageButton = itemView.findViewById(R.id.cartDecrement)
        val incrementBtn: ImageButton = itemView.findViewById(R.id.cartIncrement)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.cartDelete)
        val subtotalText: TextView = itemView.findViewById(R.id.cartItemSubtotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.productName.text = item.itemName
        holder.productUnit.text = item.unit
        holder.productPrice.text = "₹${item.price.toInt()} each"
        holder.quantityText.text = item.quantity.toString()
        holder.subtotalText.text = "₹${(item.price * item.quantity).toInt()}"
        holder.productImage.setImageResource(getImageRes(item.itemImage, holder.itemView))
        holder.incrementBtn.setOnClickListener { onIncrement(item) }
        holder.decrementBtn.setOnClickListener { onDecrement(item) }
        holder.deleteBtn.setOnClickListener { onDelete(item) }
    }

    override fun getItemCount(): Int = items.size

    private fun getImageRes(imageName: String, view: View): Int {
        val ctx = view.context
        val id = ctx.resources.getIdentifier(imageName, "drawable", ctx.packageName)
        return if (id != 0) id else R.drawable.ic_launcher_background
    }
}
