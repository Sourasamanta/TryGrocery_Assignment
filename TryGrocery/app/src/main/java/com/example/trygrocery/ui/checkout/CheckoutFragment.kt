package com.example.trygrocery.ui.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.trygrocery.R
import com.example.trygrocery.ui.cart.CartViewModel
import com.example.trygrocery.ui.order.OrderSuccessFragment

class CheckoutFragment : Fragment() {

    private val cartViewModel: CartViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val backButton = view.findViewById<ImageButton>(R.id.checkoutBackButton)
        val nameInput = view.findViewById<EditText>(R.id.inputName)
        val addressInput = view.findViewById<EditText>(R.id.inputAddress)
        val phoneInput = view.findViewById<EditText>(R.id.inputPhone)
        val paymentGroup = view.findViewById<RadioGroup>(R.id.paymentGroup)
        val orderTotalText = view.findViewById<TextView>(R.id.checkoutTotalText)
        val placeOrderButton = view.findViewById<Button>(R.id.placeOrderButton)

        backButton.setOnClickListener { parentFragmentManager.popBackStack() }

        cartViewModel.totalCost.observe(viewLifecycleOwner) { total ->
            orderTotalText.text = "Order Total: ₹${total.toInt()}"
        }

        cartViewModel.lastOrderId.observe(viewLifecycleOwner) { orderId ->
            orderId ?: return@observe
            cartViewModel.clearLastOrderId()
            val fragment = OrderSuccessFragment().apply {
                arguments = Bundle().apply { putInt("orderId", orderId) }
            }
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginFragmentContainer, fragment)
                .commit()
        }

        placeOrderButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val address = addressInput.text.toString().trim()
            val phone = phoneInput.text.toString().trim()

            if (name.isEmpty()) { nameInput.error = "Required"; return@setOnClickListener }
            if (address.isEmpty()) { addressInput.error = "Required"; return@setOnClickListener }
            if (phone.length != 10) { phoneInput.error = "Enter valid 10-digit number"; return@setOnClickListener }
            if (paymentGroup.checkedRadioButtonId == -1) {
                Toast.makeText(requireContext(), "Select a payment method", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val payment = when (paymentGroup.checkedRadioButtonId) {
                R.id.radioCash -> "Cash on Delivery"
                R.id.radioCard -> "Credit/Debit Card"
                R.id.radioUpi -> "UPI"
                R.id.radioNet -> "Net Banking"
                else -> "Cash on Delivery"
            }

            placeOrderButton.isEnabled = false
            cartViewModel.placeOrder(name, address, phone, payment)
        }
    }
}
