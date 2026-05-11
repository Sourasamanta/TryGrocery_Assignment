package com.example.trygrocery.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trygrocery.R
import com.example.trygrocery.data.ItemEntity

class ProductAdapter(
    private var products: List<ItemEntity>,
    private var quantities: Map<Int, Int> = emptyMap(),
    private val onAdd: (ItemEntity) -> Unit,
    private val onIncrement: (ItemEntity) -> Unit,
    private val onDecrement: (ItemEntity) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateItems(newProducts: List<ItemEntity>) {
        products = newProducts
        notifyDataSetChanged()
    }

    fun updateQuantities(newQuantities: Map<Int, Int>) {
        quantities = newQuantities
        notifyDataSetChanged()
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.productImage)
        val productName: TextView = itemView.findViewById(R.id.productName)
        val productCategory: TextView = itemView.findViewById(R.id.productCategory)
        val productUnit: TextView = itemView.findViewById(R.id.productUnit)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val quantityContainer: LinearLayout = itemView.findViewById(R.id.quantityContainer)
        val decrementBtn: ImageButton = itemView.findViewById(R.id.decrementButton)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        val incrementBtn: ImageButton = itemView.findViewById(R.id.incrementButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        val qty = quantities[product.itemId] ?: 0

        holder.productName.text = product.itemName
        holder.productCategory.text = product.categoryId.let { id ->
            when (id) {
                1 -> "Fruits"; 2 -> "Vegetables"; 3 -> "Dairy"
                4 -> "Beverages"; 5 -> "Snacks"; 6 -> "Bakery"
                else -> "General"
            }
        }
        holder.productUnit.text = product.unit
        holder.productPrice.text = "₹${product.price.toInt()}"

        if (qty == 0) {
            holder.addButton.visibility = View.VISIBLE
            holder.quantityContainer.visibility = View.GONE
        } else {
            holder.addButton.visibility = View.GONE
            holder.quantityContainer.visibility = View.VISIBLE
            holder.quantityText.text = qty.toString()
        }

        holder.addButton.setOnClickListener { onAdd(product) }
        holder.incrementBtn.setOnClickListener { onIncrement(product) }
        holder.decrementBtn.setOnClickListener { onDecrement(product) }

        val imageRes = getImageResource(product.itemImage)
        holder.productImage.setImageResource(imageRes)
    }

    override fun getItemCount(): Int = products.size

    private fun getImageResource(imageName: String): Int = when (imageName) {
        "apple" -> R.drawable.apple
        "banana" -> R.drawable.banana
        "mango" -> R.drawable.mango
        "grapes" -> R.drawable.grapes
        "strawberry" -> R.drawable.strawberry
        "orange" -> R.drawable.orange
        "papaya" -> R.drawable.papaya
        "pineapple" -> R.drawable.pineapple
        "watermelon" -> R.drawable.watermelon
        "kiwi" -> R.drawable.kiwi
        "pomegranate" -> R.drawable.pomegranate
        "guava" -> R.drawable.guava
        "lemon" -> R.drawable.lemon
        "lime" -> R.drawable.lime
        "pear" -> R.drawable.pear
        "peach" -> R.drawable.peach
        "plum" -> R.drawable.plum
        "cherry" -> R.drawable.cherry
        "blueberry" -> R.drawable.blueberry
        "raspberry" -> R.drawable.raspberry
        "avocado" -> R.drawable.avocado
        "coconut" -> R.drawable.coconut
        "jackfruit" -> R.drawable.jackfruit
        "dragon_fruit" -> R.drawable.dragon_fruit
        "lychee" -> R.drawable.lychee
        "custard_apple" -> R.drawable.custard_apple
        "fig" -> R.drawable.fig
        "apricot" -> R.drawable.apricot
        "melon" -> R.drawable.melon
        "passion_fruit" -> R.drawable.passion_fruit
        "tomato" -> R.drawable.tomato
        "potato" -> R.drawable.potato
        "onion" -> R.drawable.onion
        "garlic" -> R.drawable.garlic
        "ginger" -> R.drawable.ginger
        "carrot" -> R.drawable.carrot
        "spinach" -> R.drawable.spinach
        "capsicum" -> R.drawable.capsicum
        "broccoli" -> R.drawable.broccoli
        "cauliflower" -> R.drawable.cauliflower
        "cabbage" -> R.drawable.cabbage
        "peas" -> R.drawable.peas
        "corn" -> R.drawable.corn
        "beetroot" -> R.drawable.beetroot
        "radish" -> R.drawable.radish
        "cucumber" -> R.drawable.cucumber
        "pumpkin" -> R.drawable.pumpkin
        "bottle_gourd" -> R.drawable.bottle_gourd
        "bitter_gourd" -> R.drawable.bitter_gourd
        "ridge_gourd" -> R.drawable.ridge_gourd
        "okra" -> R.drawable.okra
        "eggplant" -> R.drawable.eggplant
        "green_beans" -> R.drawable.green_beans
        "mushroom" -> R.drawable.mushroom
        "sweet_potato" -> R.drawable.sweet_potato
        "zucchini" -> R.drawable.zucchini
        "celery" -> R.drawable.celery
        "leek" -> R.drawable.leek
        "asparagus" -> R.drawable.asparagus
        "kale" -> R.drawable.kale
        "full_cream_milk" -> R.drawable.full_cream_milk
        "toned_milk" -> R.drawable.toned_milk
        "skimmed_milk" -> R.drawable.skimmed_milk
        "almond_milk" -> R.drawable.almond_milk
        "soy_milk" -> R.drawable.soy_milk
        "paneer" -> R.drawable.paneer
        "cheddar_cheese" -> R.drawable.cheddar_cheese
        "mozzarella" -> R.drawable.mozzarella
        "butter" -> R.drawable.butter
        "ghee" -> R.drawable.ghee
        "dahi_curd" -> R.drawable.dahi_curd
        "greek_yogurt" -> R.drawable.greek_yogurt
        "flavoured_yogurt" -> R.drawable.flavoured_yogurt
        "cream_cheese" -> R.drawable.cream_cheese
        "whipping_cream" -> R.drawable.whipping_cream
        "ice_cream_vanilla" -> R.drawable.ice_cream_vanilla
        "ice_cream_chocolate" -> R.drawable.ice_cream_chocolate
        "buttermilk" -> R.drawable.buttermilk
        "condensed_milk" -> R.drawable.condensed_milk
        "khoa" -> R.drawable.khoa
        "amul_cheese_slice" -> R.drawable.amul_cheese_slice
        "parmesan" -> R.drawable.parmesan
        "cottage_cheese" -> R.drawable.cottage_cheese
        "heavy_cream" -> R.drawable.heavy_cream
        "sour_cream" -> R.drawable.sour_cream
        "coca_cola_can" -> R.drawable.coca_cola_can
        "pepsi_can" -> R.drawable.pepsi_can
        "sprite_can" -> R.drawable.sprite_can
        "fanta_orange" -> R.drawable.fanta_orange
        "thums_up" -> R.drawable.thums_up
        "tropicana_orange_juice" -> R.drawable.tropicana_orange_juice
        "real_guava_juice" -> R.drawable.real_guava_juice
        "minute_maid" -> R.drawable.minute_maid
        "paper_boat_aam_panna" -> R.drawable.paper_boat_aam_panna
        "bisleri_water_bottle" -> R.drawable.bisleri_water_bottle
        "kinley_water" -> R.drawable.kinley_water
        "aquafina_water" -> R.drawable.aquafina_water
        "red_bull_energy_drink" -> R.drawable.red_bull_energy_drink
        "monster_energy_drink" -> R.drawable.monster_energy_drink
        "nescafe_classic_coffee" -> R.drawable.nescafe_classic_coffee
        "bru_coffee" -> R.drawable.bru_coffee
        "tata_tea_gold" -> R.drawable.tata_tea_gold
        "green_tea_bag" -> R.drawable.green_tea_bag
        "masala_chai" -> R.drawable.masala_chai
        "horlicks_drink" -> R.drawable.horlicks_drink
        "bournvita" -> R.drawable.bournvita
        "complan" -> R.drawable.complan
        "boost_drink" -> R.drawable.boost_drink
        "amul_kool_milk" -> R.drawable.amul_kool_milk
        "fresh_lime_soda" -> R.drawable.fresh_lime_soda
        "coconut_water_pack" -> R.drawable.coconut_water_pack
        "iced_tea_lemon" -> R.drawable.iced_tea_lemon
        "cold_coffee" -> R.drawable.cold_coffee
        "turmeric_milk" -> R.drawable.turmeric_milk
        "lays_classic_chips" -> R.drawable.lays_classic_chips
        "kurkure_masala" -> R.drawable.kurkure_masala
        "haldirams_bhujia" -> R.drawable.haldirams_bhujia
        "bingo_mad_angles" -> R.drawable.bingo_mad_angles
        "parle_g_biscuit" -> R.drawable.parle_g_biscuit
        "oreo_cookies" -> R.drawable.oreo_cookies
        "bourbon_biscuit" -> R.drawable.bourbon_biscuit
        "digestive_marie_biscuit" -> R.drawable.digestive_marie_biscuit
        "cream_cracker" -> R.drawable.cream_cracker
        "glucose_biscuit" -> R.drawable.glucose_biscuit
        "cadbury_dairy_milk_chocolate" -> R.drawable.cadbury_dairy_milk_chocolate
        "kit_kat_chocolate" -> R.drawable.kit_kat_chocolate
        "__star_chocolate" -> R.drawable.__star_chocolate
        "munch_bar" -> R.drawable.munch_bar
        "milky_bar" -> R.drawable.milky_bar
        "maggi_noodles" -> R.drawable.maggi_noodles
        "yippee_noodles" -> R.drawable.yippee_noodles
        "muesli" -> R.drawable.muesli
        "granola_bar" -> R.drawable.granola_bar
        "salted_peanuts" -> R.drawable.salted_peanuts
        "cashew_nuts" -> R.drawable.cashew_nuts
        "almonds" -> R.drawable.almonds
        "raisins" -> R.drawable.raisins
        "trail_mix" -> R.drawable.trail_mix
        "popcorn_butter" -> R.drawable.popcorn_butter
        "nachos_chips" -> R.drawable.nachos_chips
        "rice_cakes" -> R.drawable.rice_cakes
        "banana_chips" -> R.drawable.banana_chips
        "roasted_makhana" -> R.drawable.roasted_makhana
        "white_bread_loaf" -> R.drawable.white_bread_loaf
        "whole_wheat_bread" -> R.drawable.whole_wheat_bread
        "multigrain_bread" -> R.drawable.multigrain_bread
        "brown_bread" -> R.drawable.brown_bread
        "pav_buns" -> R.drawable.pav_buns
        "burger_buns" -> R.drawable.burger_buns
        "hot_dog_rolls" -> R.drawable.hot_dog_rolls
        "croissant" -> R.drawable.croissant
        "dinner_roll" -> R.drawable.dinner_roll
        "garlic_bread" -> R.drawable.garlic_bread
        "chocolate_cake_slice" -> R.drawable.chocolate_cake_slice
        "vanilla_cake" -> R.drawable.vanilla_cake
        "blueberry_muffin" -> R.drawable.blueberry_muffin
        "brownie" -> R.drawable.brownie
        "glazed_donut" -> R.drawable.glazed_donut
        "khari_biscuit" -> R.drawable.khari_biscuit
        "rusk_toast" -> R.drawable.rusk_toast
        "toast_bread" -> R.drawable.toast_bread
        "pita_bread" -> R.drawable.pita_bread
        "naan_bread" -> R.drawable.naan_bread
        "bagel" -> R.drawable.bagel
        "baguette_bread" -> R.drawable.baguette_bread
        "focaccia" -> R.drawable.focaccia
        "sourdough_bread" -> R.drawable.sourdough_bread
        "rye_bread" -> R.drawable.rye_bread
        "cinnamon_roll" -> R.drawable.cinnamon_roll
        "danish_pastry" -> R.drawable.danish_pastry
        "eclair" -> R.drawable.eclair
        "cream_puff" -> R.drawable.cream_puff
        "apple_pie" -> R.drawable.apple_pie
        else -> R.drawable.ic_launcher_background
    }
}
