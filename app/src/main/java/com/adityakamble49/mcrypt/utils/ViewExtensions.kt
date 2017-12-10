package com.adityakamble49.mcrypt.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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