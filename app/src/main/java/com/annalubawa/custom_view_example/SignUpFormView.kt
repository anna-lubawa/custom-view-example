package com.annalubawa.custom_view_example

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.*
import com.annalubawa.custom_view_example.databinding.SignupFormViewBinding


class SignUpFormView constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), LifecycleOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    private var state: MutableLiveData<SignUpFormState> = MutableLiveData(SignUpFormState())

    private var binding: SignupFormViewBinding =
        SignupFormViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var progress = 0

    init {
        state.observe(this, Observer {
            progress = 0
                .plus(if (it.emailValid) 20 else 0)
                .plus(if (it.firstnameValid) 20 else 0)
                .plus(if (it.lastnameValid) 20 else 0)
                .plus(if (it.passwordsValid) 40 else 0)

            binding.progressBar.setProgress(progress, true)
        })

        setOnTextChangedListeners()
        setOnFocusChangeListeners()

        binding.repeatPasswordEditText.setOnKeyListener { _, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_ENTER -> {
                    binding.repeatPasswordEditText.showSoftInputOnFocus = false
                    binding.signupForm.requestFocus()
                    hideKeyboard(binding.repeatPasswordEditText)
                    if (progress == 100) {
                        binding.progressBar.isVisible = false
                        binding.button.isVisible = true
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun getLifecycle() = lifecycleRegistry

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    override fun onDetachedFromWindow() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        super.onDetachedFromWindow()
    }

    fun setSignUpListener(listener: View.OnClickListener) {
        binding.button.setOnClickListener(listener)
    }

    fun getEmail() = binding.emailEditText.text.toString()

    fun getFirstname() = binding.firstnameEditText.text.toString()

    fun getLastname() = binding.lastnameEditText.text.toString()

    fun getPassword() = binding.passwordEditText.text.toString()

    fun getRepeatedPassword() = binding.repeatPasswordEditText.text.toString()

    private fun setOnTextChangedListeners() {
        binding.emailEditText.doOnTextChanged { text, _, _, _ ->
            state.value = state.value?.copy(
                emailValid = validateEmail(text.toString())
            )
        }

        binding.firstnameEditText.doOnTextChanged { text, _, _, _ ->
            state.value = state.value?.copy(
                firstnameValid = validateName(text.toString())
            )
        }

        binding.lastnameEditText.doOnTextChanged { text, _, _, _ ->
            state.value = state.value?.copy(
                lastnameValid = validateName(text.toString())
            )
        }

        binding.passwordEditText.doOnTextChanged { text, _, _, _ ->
            state.value = state.value?.copy(
                passwordsValid = validatePasswords(text.toString())
            )
        }

        binding.repeatPasswordEditText.doOnTextChanged { text, _, _, _ ->
            state.value = state.value?.copy(
                passwordsValid = validatePasswords(text.toString())
            )
        }
    }

    private fun setOnFocusChangeListeners() {
        binding.emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.button.isVisible = false
                binding.progressBar.isVisible = true
            }
        }

        binding.firstnameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.button.isVisible = false
                binding.progressBar.isVisible = true
            }
        }

        binding.lastnameEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.button.isVisible = false
                binding.progressBar.isVisible = true
            }
        }

        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.button.isVisible = false
                binding.progressBar.isVisible = true
            }
        }

        binding.repeatPasswordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showKeyboard(binding.repeatPasswordEditText)
                binding.button.isVisible = false
                binding.progressBar.isVisible = true
            }
        }
    }

    private fun validateEmail(text: String): Boolean {
        return text.contains("@")
                && text.contains(".")
                && text.length >= 5
    }

    private fun validateName(text: String): Boolean {
        return text.length >= 2
    }

    private fun validatePasswords(text: String): Boolean {
        return text.length >= 8 && binding.passwordEditText.text.toString() == binding.repeatPasswordEditText.text.toString()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager? =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun showKeyboard(view: View) {
        val inputMethodManager: InputMethodManager? =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
        inputMethodManager?.showSoftInput(view, 0)
    }
}
