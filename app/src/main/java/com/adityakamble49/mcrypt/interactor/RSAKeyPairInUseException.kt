package com.adityakamble49.mcrypt.interactor

/**
 * RSAKey In Use Exception
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class RSAKeyPairInUseException constructor(
        override val message: String) : Exception()