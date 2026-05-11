package com.example.trygrocery.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.trygrocery.R
import com.example.trygrocery.ui.home.HomeFragment

class OrderSuccessFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_order_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = arguments?.getInt("orderId") ?: 0
        val successCircle = view.findViewById<View>(R.id.successCircle)
        val checkIcon = view.findViewById<ImageView>(R.id.checkIcon)
        val orderPlacedText = view.findViewById<TextView>(R.id.orderPlacedText)
        val orderIdText = view.findViewById<TextView>(R.id.orderIdText)
        val goHomeButton = view.findViewById<Button>(R.id.goHomeButton)

        orderIdText.text = "Order #${orderId.toString().padStart(6, '0')}"

        goHomeButton.setOnClickListener {
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            parentFragmentManager.beginTransaction()
                .replace(R.id.loginFragmentContainer, HomeFragment())
                .commit()
        }

        playSuccessAnimation(successCircle, checkIcon, orderPlacedText, orderIdText, goHomeButton)
    }

    private fun playSuccessAnimation(
        circle: View,
        checkIcon: ImageView,
        title: TextView,
        orderId: TextView,
        button: Button
    ) {
        listOf(circle, checkIcon, title, orderId, button).forEach {
            it.alpha = 0f
            it.scaleX = 0f
            it.scaleY = 0f
        }

        circle.animate()
            .alpha(1f).scaleX(1f).scaleY(1f)
            .setDuration(600)
            .setInterpolator(OvershootInterpolator(1.5f))
            .start()

        checkIcon.postDelayed({
            checkIcon.animate()
                .alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(400)
                .setInterpolator(OvershootInterpolator())
                .start()
        }, 450)

        title.translationY = 60f
        title.postDelayed({
            title.animate()
                .alpha(1f).scaleX(1f).scaleY(1f).translationY(0f)
                .setDuration(400)
                .start()
        }, 750)

        orderId.postDelayed({
            orderId.animate()
                .alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(350)
                .start()
        }, 1000)

        button.postDelayed({
            button.animate()
                .alpha(1f).scaleX(1f).scaleY(1f)
                .setDuration(350)
                .start()
        }, 1200)
    }
}
