package com.example.trygrocery.ui.login

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.trygrocery.R
import com.example.trygrocery.ui.home.HomeFragment

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputLabel = view.findViewById<TextView>(R.id.inputLabel)
        val loginInput = view.findViewById<EditText>(R.id.loginInput)
        val helperText = view.findViewById<TextView>(R.id.helperText)
        val loginButton = view.findViewById<Button>(R.id.loginButton)

        viewModel.isOtpStep.observe(viewLifecycleOwner) { isOtpStep ->
            if (isOtpStep) {
                inputLabel.text = "Enter OTP"
                loginInput.text.clear()
                loginInput.hint = "Enter 4-digit OTP"
                loginInput.inputType = InputType.TYPE_CLASS_NUMBER
                loginInput.filters = arrayOf(InputFilter.LengthFilter(4))
                helperText.text = "OTP has been sent to your mobile number."
                loginButton.text = "Verify OTP"
            }
        }

        viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                viewModel.onToastShown()
            }
        }

        viewModel.clearOtpInput.observe(viewLifecycleOwner) { event ->
            event?.let {
                loginInput.text.clear()
                viewModel.onClearInputHandled()
            }
        }

        viewModel.navigateToHome.observe(viewLifecycleOwner) { navigate ->
            if (navigate) {
                Toast.makeText(requireContext(), "OTP verification successful!", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.loginFragmentContainer, HomeFragment())
                    .addToBackStack(null)
                    .commit()
                viewModel.onNavigatedToHome()
            }
        }

        loginButton.setOnClickListener {
            val input = loginInput.text.toString().trim()
            if (viewModel.isOtpStep.value == true) {
                viewModel.onVerifyOtp(input)
            } else {
                viewModel.onSubmitMobile(input)
            }
        }
    }
}
