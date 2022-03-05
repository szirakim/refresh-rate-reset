package com.szirakiapps.refreshratereset

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.widget.Toast

private const val MIN_REFRESH_RATE = "min_refresh_rate"
private const val PEAK_REFRESH_RATE = "peak_refresh_rate"

enum class RefreshRate(val refreshRateValue: String) {
    Low("10.0"),
    Sixty("60.0"),
    NinetySix("96.0"),
    HundredTwenty("120.0"),
    Reset("reset"),
}

fun getRefreshRate(context: Context): RefreshRate {
    val currentPeak = getConfig(context)

    RefreshRate.values().forEach {
        if (doubleEquals(it.refreshRateValue.toDouble(), currentPeak)) {
            return it
        }
    }

    return RefreshRate.Sixty
}

fun doubleEquals(a: Double, b: Double): Boolean {
    return Math.abs(a - b) < 0.1
}

fun setConfig(context: Context, key: String, value: String) {
    val contentResolver = context.contentResolver
    try {
        val contentValues = ContentValues(2)
        contentValues.put("name", key)
        contentValues.put("value", value)
        contentResolver.insert(Uri.parse("content://settings/system"), contentValues)
    } catch (th: Exception) {
        Toast.makeText(context, "Failed to setConfig ${th.message}", Toast.LENGTH_SHORT).show()
        th.printStackTrace()
    }
}

fun getConfig(context: Context): Double {

    var peakRefreshRate = 0.0

    val contentResolver = context.contentResolver
    try {
        val cursor = contentResolver.query(
            Uri.parse("content://settings/system"),
            arrayOf("name", "value"),
            null,// "name=$PEAK_REFRESH_RATE",
            null,
            null
        )

        if (cursor?.moveToFirst() == true) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val value = cursor.getString(cursor.getColumnIndex("value"))

                if (name == PEAK_REFRESH_RATE) {
                    peakRefreshRate = value.toDouble()
                    break
                }
            } while (cursor.moveToNext())
        }

        cursor?.close()
    } catch (e: Exception) {
        Toast.makeText(context, e.message ?: "Failed to get refresh rate", Toast.LENGTH_SHORT)
            .show()
        e.printStackTrace()
    }

    return peakRefreshRate
}

fun Context.setMinRefreshRate(rate: RefreshRate) {
    setConfig(this, MIN_REFRESH_RATE, rate.refreshRateValue)
}

fun Context.setPeakRefreshRate(rate: RefreshRate) {
    setConfig(this, PEAK_REFRESH_RATE, rate.refreshRateValue)
}