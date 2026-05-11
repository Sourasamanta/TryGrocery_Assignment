package com.example.trygrocery.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trygrocery.R
import com.example.trygrocery.ui.checkout.CheckoutFragment

class CartFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<ImageButton>(R.id.cartBackButton)
        val cartRecyclerView = view.findViewById<RecyclerView>(R.id.cartRecyclerView)
        val emptyView = view.findViewById<TextView>(R.id.cartEmptyText)
        val totalText = view.findViewById<TextView>(R.id.cartTotalText)
        val continueButton = view.findViewById<Button>(R.id.cartContinueButton)

        val adapter = CartAdapter(
            items = emptyList(),
            onIncrement = { cartViewModel.incrementCartItem(it) },
            onDecrement = { cartViewModel.decrementCartItem(it) },
            onDelete = { cartViewModel.deleteCartItem(it) }
        )
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartRecyclerView.adapter = adapter

        backButton.setOnClickListener { parentFragmentManager.popBackStack() }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
            if (items.isEmpty()) {
                emptyView.visibility = View.VISIBLE
                cartRecyclerView.visibility = View.GONE
                continueButton.isEnabled = false
            } else {
                emptyView.visibility = View.GONE
                cartRecyclerView.visibility = View.VISIBLE
                continueButton.isEnabled = true
            }
        }

        cartViewModel.totalCost.observe(viewLifecycleOwner) { total ->
            totalText.text = "Total: ₹${total.toInt()}"
        }

        continueButton.setOnClickListener {
            if (cartViewModel.cartItems.value.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Your cart is empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginFragmentContainer, CheckoutFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
