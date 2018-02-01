package com.adityakamble49.dcipher.model

import java.io.Serializable

/**
 * Encrypted Text Model
 *
 * @author Aditya Kamble
 * @since 02/02/2018
 */
data class EncryptedText(
        val encryptedTextValue: String) : Serializable