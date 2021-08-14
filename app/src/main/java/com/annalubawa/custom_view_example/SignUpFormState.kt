package com.annalubawa.custom_view_example

data class SignUpFormState(
    val emailValid: Boolean = false,
    val firstnameValid: Boolean = false,
    val lastnameValid: Boolean = false,
    val passwordsValid: Boolean = false
)