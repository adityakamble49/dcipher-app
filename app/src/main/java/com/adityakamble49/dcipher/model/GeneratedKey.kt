package com.adityakamble49.dcipher.model

import java.security.PrivateKey
import java.security.PublicKey

/**
 * Generated Key to Store Encrypted AES Session key and RSA KeyPair
 *
 * @author Aditya Kamble
 * @since 25/12/2017
 */
class GeneratedKey(
        var encryptedSessionKey: String,
        var publicKey: PublicKey,
        var privateKey: PrivateKey)