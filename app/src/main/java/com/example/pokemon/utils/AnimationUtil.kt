package com.example.pokemon.utils

import android.content.Context
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils

object AnimationUtil {
    fun goneAnimations(context: Context, id: List<Int>): AnimationSet {
        val animationSet = AnimationSet(true)
        animationSet.interpolator = AccelerateInterpolator()
        id.forEach {
            animationSet.addAnimation(AnimationUtils.loadAnimation(context, it))
        }
        return animationSet
    }

    fun visibleAnimations(context: Context, id: List<Int>): AnimationSet {
        val animationSet = AnimationSet(true)
        animationSet.interpolator = AccelerateInterpolator()
        id.forEach {
            animationSet.addAnimation(AnimationUtils.loadAnimation(context, it))
        }
        return animationSet
    }

    fun fadeOut(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, android.R.anim.fade_out)
    }

    fun fadeIn(context: Context): Animation {
        return AnimationUtils.loadAnimation(context, android.R.anim.fade_in)
    }
}