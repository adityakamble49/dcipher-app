package com.adityakamble49.dcipher.utils

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast

/**
 * @author Aditya Kamble
 * @since 10/12/2017
 */

/**
 * Extension function for View Group to
 * Inflate view layout inflater
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

/**
 * Show plain text with Toast
 */
fun Context.showToast(text: CharSequence) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

/**
 * Show resource text with Toast
 */
fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun View.makeVisible() {
    this.visibility = VISIBLE
}

fun View.makeGone() {
    this.visibility = GONE
}

/**
 * Simplified method for getting color resource using [ResourcesCompat]
 */
fun AppCompatActivity.getColorResource(@ColorRes colorRes: Int): Int {
    return ResourcesCompat.getColor(resources, colorRes, theme)
}