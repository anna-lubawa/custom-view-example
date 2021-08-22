package com.annalubawa.custom_view_example

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpFormState(
    val emailValid: Boolean = false,
    val firstnameValid: Boolean = false,
    val lastnameValid: Boolean = false,
    val passwordsValid: Boolean = false,
    val isButtonVisible: Boolean = false
) : Parcelable