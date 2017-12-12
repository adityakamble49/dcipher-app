package com.adityakamble49.mcrypt.utils

import com.adityakamble49.mcrypt.AppExecutors

/**
 * Common Extensions
 * @author Aditya Kamble
 * @since 12/12/2017
 */

/**
 * Update database using DiskIO thread from AppExecutors
 */
fun AppExecutors.updateDB(operation: () -> Unit) {
    diskIO().execute {
        operation()
    }
}
