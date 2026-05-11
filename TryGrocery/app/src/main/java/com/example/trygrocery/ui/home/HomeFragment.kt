package com.example.trygrocery.ui.home

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trygrocery.R
import com.example.trygrocery.ui.cart.CartFragment
import com.example.trygrocery.ui.cart.CartViewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: ProductAdapter

    private val categoryViews = mutableMapOf<Int?, TextView>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        drawerLayout = view.findViewById(R.id.drawerLayout)

        val menuButton = view.findViewById<ImageButton>(R.id.menuButton)
        val cartButton = view.findViewById<ImageButton>(R.id.cartButton)
        val cartBadge = view.findViewById<TextView>(R.id.cartBadge)
        val productList = view.findViewById<RecyclerView>(R.id.productList)
        val darkModeSwitch = view.findViewById<Switch>(R.id.darkModeSwitch)

        adapter = ProductAdapter(
            products = emptyList(),
            quantities = emptyMap(),
            onAdd = { cartViewModel.addItem(it) },
            onIncrement = { cartViewModel.addItem(it) },
            onDecrement = { cartViewModel.removeOne(it) }
        )
        productList.layoutManager = GridLayoutManager(requireContext(), 2)
        productList.adapter = adapter

        menuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        cartButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginFragmentContainer, CartFragment())
                .addToBackStack(null)
                .commit()
        }

        homeViewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.updateItems(items)
        }

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            val qtyMap = cartItems.associate { it.itemId to it.quantity }
            adapter.updateQuantities(qtyMap)
        }

        cartViewModel.totalItemCount.observe(viewLifecycleOwner) { count ->
            if (count > 0) {
                cartBadge.visibility = View.VISIBLE
                cartBadge.text = count.toString()
            } else {
                cartBadge.visibility = View.GONE
            }
        }

        setupDrawerCategories(view)

        val prefs = requireActivity().getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isDark = prefs.getBoolean("dark_mode", false)
        darkModeSwitch.isChecked = isDark

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        homeViewModel.selectedCategoryId.observe(viewLifecycleOwner) { catId ->
            updateCategoryHighlight(catId)
        }
    }

    private fun setupDrawerCategories(view: View) {
        categoryViews[null] = view.findViewById(R.id.catAll)
        categoryViews[1] = view.findViewById(R.id.catFruits)
        categoryViews[2] = view.findViewById(R.id.catVegetables)
        categoryViews[3] = view.findViewById(R.id.catDairy)
        categoryViews[4] = view.findViewById(R.id.catBeverages)
        categoryViews[5] = view.findViewById(R.id.catSnacks)
        categoryViews[6] = view.findViewById(R.id.catBakery)

        categoryViews.forEach { (catId, textView) ->
            textView.setOnClickListener {
                homeViewModel.filterByCategory(catId)
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
        updateCategoryHighlight(null)
    }

    private fun updateCategoryHighlight(selectedId: Int?) {
        val selectedColor = resources.getColor(R.color.cat_selected_bg, requireActivity().theme)
        val normalColor = resources.getColor(android.R.color.transparent, requireActivity().theme)
        val selectedTextColor = resources.getColor(R.color.green_primary, requireActivity().theme)
        val normalTextColor = resources.getColor(R.color.text_secondary, requireActivity().theme)

        categoryViews.forEach { (catId, textView) ->
            val isSelected = catId == selectedId
            textView.setBackgroundColor(if (isSelected) selectedColor else normalColor)
            textView.setTextColor(if (isSelected) selectedTextColor else normalTextColor)
            textView.typeface = if (isSelected) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        }
    }
}
