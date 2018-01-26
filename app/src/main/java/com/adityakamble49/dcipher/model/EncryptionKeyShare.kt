package com.adityakamble49.dcipher.model

import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec

/**
 * Encryption Key Share
 *
 * @author Aditya Kamble
 * @since 26/1/2018
 */
data class EncryptionKeyShare(
        var id: Int = 0,
        var name: String,
        var encryptedSessionKey: String,
        var publicKey: RSAPublicKeySpec,
        var privateKey: RSAPrivateKeySpec)