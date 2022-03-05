package com.szirakiapps.refreshratereset

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

fun delayed(millis: Long, function: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(function, millis)
}

fun <T> List<T>.forEachIndexedDelayed(millis: Long, function: (index: Int, item: T) -> Unit) {
    forEachIndexed { index, item ->
        delayed(index * millis) { function(index, item) }
    }
}

fun animateWidth(view: View, current: Int, new: Int) {

    val slideAnimator = ValueAnimator
        .ofInt(current, new)
        .setDuration(500)

    slideAnimator.addUpdateListener { anim ->
        view.layoutParams.width = anim.animatedValue.toString().toInt()
        view.requestLayout();
    }

    val animationSet = AnimatorSet()
    animationSet.interpolator = AccelerateDecelerateInterpolator()
    animationSet.play(slideAnimator)
    animationSet.start()
}
