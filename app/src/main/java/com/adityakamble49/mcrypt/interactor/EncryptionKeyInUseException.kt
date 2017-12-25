package com.adityakamble49.mcrypt.interactor

import com.adityakamble49.mcrypt.model.EncryptionKey

/**
 * [[EncryptionKey]] In Use Exception
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
class EncryptionKeyInUseException constructor(
        override val message: String) : Exception()