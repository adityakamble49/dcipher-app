package com.adityakamble49.mcrypt.di.scope

import javax.inject.Scope

/**
 * Dagger scope for per application
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication
