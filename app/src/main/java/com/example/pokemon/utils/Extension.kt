package com.example.pokemon.utils

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.example.pokemon.R
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable

import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat


inline fun <reified T> Activity.startNewActivity(finish: Boolean) {
    startActivity(Intent(this, T::class.java))
    if (finish) {
        finish()
    }
}

inline fun <reified T> Activity.startNewActivity(
    finish: Boolean,
    values: (Intent) -> Unit
) {
    startActivity(Intent(this, T::class.java).also {
        values(it)
    })
    if (finish) {
        finish()
    }
}

fun ImageView.setImage(imageId: Any?, error: Any = android.R.drawable.ic_menu_report_image) {
    animation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in).also {
        it.duration = 200L
        it.start()
    }
    val shimmer =
        Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()
    val shimmerDrawable = ShimmerDrawable().apply {
        setShimmer(shimmer)
    }
    Glide.with(this).load(imageId).placeholder(shimmerDrawable).error(error).into(this)
}


fun Context.showToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }
}

fun View.beGone() {
    this.startAnimation(AnimationUtil.fadeOut(this.context))
    this.visibility = View.GONE
}

fun View.beInvisible() {
    this.startAnimation(AnimationUtil.fadeOut(this.context))
    this.visibility = View.INVISIBLE
}

fun View.beGoneWithAnimation(id: List<Int>) {
    this.startAnimation(AnimationUtil.goneAnimations(this.context, id))
    this.visibility = View.GONE
}

fun View.beVisibleWithAnimation(id: List<Int>) {
    this.startAnimation(AnimationUtil.visibleAnimations(this.context, id))
    this.visibility = View.VISIBLE
}

fun View.beVisible() {
    this.startAnimation(AnimationUtil.fadeIn(this.context))
    this.visibility = View.VISIBLE
}

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}


fun Activity.openFile(file: File) {
    val map = MimeTypeMap.getSingleton()
    val ext = MimeTypeMap.getFileExtensionFromUrl(file.name)
    var type = map.getMimeTypeFromExtension(ext)

    if (type == null) type = "*/*"


    val intent = Intent(Intent.ACTION_VIEW)
    val data = FileProvider.getUriForFile(this, "$packageName.provider", file)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.setDataAndType(data, type)
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, "Application not found to open this file.", Toast.LENGTH_LONG).show()
    }

}

fun String.showLog(message: String?) {
    message?.let {
        Log.d(this, it)
    }
}

fun String.getMimeType(): String? {
    val extension = MimeTypeMap.getFileExtensionFromUrl(this)
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
}

fun Context.getColorFromResource(colorId: Int): Int {
    return ContextCompat.getColor(this, colorId)
}

fun Context.getDateFormat(date: Long, pattern: String = "yyyy-MM-dd"): String {
    return SimpleDateFormat(pattern).format(date).toString()
}


fun getTimeFormat(hour: Int, minutes: Int, amPm: Int): String {
    val mhour = if (hour > 12) {
        hour - 12
    } else {
        hour
    }
    val amP = if (amPm == 0) {
        "am"
    } else {
        "pm"
    }
    return String.format("%d : %d %s", mhour, minutes, amP)
}


fun initDisableButtons(children: Sequence<View>) {
    children.forEach {
        it.disableButton()
    }
}

fun View.disableButton() {
    this.tag = 0
    if (this is MaterialCardView) {
        this.setCardBackgroundColor(context.getColorFromResource(android.R.color.transparent))
    } else {
        this.setBackgroundColor(context.getColorFromResource(android.R.color.transparent))
    }
    changeChildTextColor(R.color.black)
}

fun View.changeChildTextColor(color: Int) {
    if (this is MaterialCardView) {
        if (childCount != 0) {
            children.forEach {
                if (it is TextView) {
                    it.setTextColor(context.getColorFromResource(color))
                }
            }
        }
    } else if (this is ViewGroup) {
        if (childCount != 0) {
            children.forEach {
                if (it is TextView) {
                    it.setTextColor(context.getColorFromResource(color))
                }
            }
        }
    }
}





