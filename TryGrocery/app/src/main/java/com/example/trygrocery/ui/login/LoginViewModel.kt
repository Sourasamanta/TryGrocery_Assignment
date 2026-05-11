package com.example.trygrocery.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val FAKE_OTP = "1234"

class LoginViewModel : ViewModel() {

    private val _isOtpStep = MutableLiveData(false)
    val isOtpStep: LiveData<Boolean> = _isOtpStep

    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    private val _navigateToHome = MutableLiveData(false)
    val navigateToHome: LiveData<Boolean> = _navigateToHome

    private val _clearOtpInput = MutableLiveData<Unit?>()
    val clearOtpInput: LiveData<Unit?> = _clearOtpInput

    private var mobileNumber: String = ""

    fun onSubmitMobile(mobile: String) {
        if (mobile.length != 10) {
            _toastMessage.value = "Enter a valid 10-digit mobile number"
            return
        }
        mobileNumber = mobile
        _isOtpStep.value = true
    }

    fun onVerifyOtp(otp: String) {
        if (otp.length != 4) {
            _toastMessage.value = "Enter a valid 4-digit OTP"
            return
        }
        if (otp == FAKE_OTP) {
            _navigateToHome.value = true
        } else {
            _toastMessage.value = "Invalid OTP. Please try again."
            _clearOtpInput.value = Unit
        }
    }

    fun onToastShown() {
        _toastMessage.value = null
    }

    fun onNavigatedToHome() {
        _navigateToHome.value = false
    }

    fun onClearInputHandled() {
        _clearOtpInput.value = null
    }
}
