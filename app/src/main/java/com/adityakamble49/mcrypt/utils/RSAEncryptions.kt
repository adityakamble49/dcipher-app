package com.adityakamble49.mcrypt.utils

import com.adityakamble49.mcrypt.di.scope.PerApplication
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.inject.Inject

/**
 * RSA Encryption
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@PerApplication
class RSAEncryption @Inject constructor() {

    private val ALGORITHM_RSA = "RSA"
    private val keySize = 2048

    fun buildKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA)
        keyPairGenerator.initialize(keySize)
        return keyPairGenerator.genKeyPair()
    }

    fun encrypt(publicKey: PublicKey, message: String): ByteArray? {
        val cipher = Cipher.getInstance(ALGORITHM_RSA)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(message.toByteArray())
    }

    fun decrypt(privateKey: PrivateKey, encrypted: ByteArray): ByteArray? {
        val cipher = Cipher.getInstance(ALGORITHM_RSA)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(encrypted)
    }
}