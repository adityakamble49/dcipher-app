package com.adityakamble49.dcipher.utils

import com.adityakamble49.dcipher.AppExecutors
import java.util.regex.Pattern

/**
 * Common Extensions
 * @author Aditya Kamble
 * @since 12/12/2017
 */

val specialCharPattern: Pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE)


/**
 * Update database using DiskIO thread from AppExecutors
 */
fun AppExecutors.updateDB(operation: () -> Unit) {
    diskIO().execute { operation() }
}

/**
 * Update UI using Main thread from AppExecutors
 */
fun AppExecutors.updateUI(operation: () -> Unit) {
    mainThread().execute { operation() }
}

fun String.hasSpecialChar(): Boolean {
    val matcher = specialCharPattern.matcher(this)
    return matcher.find()
}
