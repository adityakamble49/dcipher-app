package com.adityakamble49.mcrypt.cache.exception

/**
 * Encryption Key Not Found Exception
 * Throw when key requested with specific id not found
 * @author Aditya Kamble
 * @since 23/12/2017
 */
class EncryptionKeyNotFoundException(
        override val message: String) : Exception()