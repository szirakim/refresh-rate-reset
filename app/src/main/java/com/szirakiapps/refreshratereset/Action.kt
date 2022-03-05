package com.szirakiapps.refreshratereset

import android.content.Context

data class Action(
    val title: String,
    val percentage: Double,
    val function: (context: Context) -> Unit,
)