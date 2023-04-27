package com.example.codingzestfirebaseapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactModel(
    var contactId: String?= null,
    var contactName: String ?= null,
    var contactLastName: String ?= null,
    var contactPhoneNumber: String ?= null,
): Parcelable
