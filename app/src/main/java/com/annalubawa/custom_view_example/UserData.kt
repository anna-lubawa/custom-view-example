package com.annalubawa.custom_view_example

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserData(
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val password: String = "",
    val repeatPassword: String = ""
) : Parcelable