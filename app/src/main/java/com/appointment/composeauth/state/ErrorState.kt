package com.appointment.composeauth.state

import androidx.annotation.StringRes
import com.appointment.composeauth.R

/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
)