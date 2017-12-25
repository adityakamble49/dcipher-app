package com.adityakamble49.mcrypt.utils

import android.util.Base64
import com.adityakamble49.mcrypt.di.scope.PerApplication
import com.adityakamble49.mcrypt.model.EncryptionKey
import com.adityakamble49.mcrypt.model.GeneratedKey
import com.adityakamble49.mcrypt.utils.Constants.Companion.AES_CIPHER
import com.adityakamble49.mcrypt.utils.Constants.Companion.AES_KEY
import com.adityakamble49.mcrypt.utils.Constants.Companion.ALGORITHM_RSA
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject

/**
 * AES and RSA Encryption
 *
 * @author Aditya Kamble
 * @since 11/12/2017
 */
@PerApplication
class EncryptionManager @Inject constructor() {

    private val RSA_KEY_SIZE = 2048
    private val AES_KEY_SIZE = 128

    fun buildKeyPair(): GeneratedKey {
        // Generate Session Secret Key
        val keyGenerator = KeyGenerator.getInstance(AES_KEY)
        keyGenerator.init(AES_KEY_SIZE)
        val sessionKey = keyGenerator.generateKey()

        // Generate RSA Key Pair
        val keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM_RSA)
        keyPairGenerator.initialize(RSA_KEY_SIZE)
        val keyPair = keyPairGenerator.genKeyPair()

        // Encrypt Session Secrete Key with RSA Key
        val encryptedSessionKey = encryptSessionKey(keyPair.public, sessionKey.encoded)

        // Build Generated Key Pair
        return GeneratedKey(Base64.encodeToString(encryptedSessionKey, Base64.DEFAULT),
                keyPair.public, keyPair.private)
    }

    private fun encryptSessionKey(publicKey: PublicKey, sessionKey: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM_RSA)
        cipher.init(Cipher.ENCRYPT_MODE, publicKey)
        return cipher.doFinal(sessionKey)
    }

    private fun decryptSessionKey(privateKey: PrivateKey, encrypted: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM_RSA)
        cipher.init(Cipher.DECRYPT_MODE, privateKey)
        return cipher.doFinal(encrypted)
    }

    private fun getSessionKeyCipher(encryptionKey: EncryptionKey, cipherMode: Int): Cipher {
        val encryptedSessionKeyByte = Base64.decode(encryptionKey.encryptedSessionKey,
                Base64.DEFAULT)
        val sessionKeyByte = decryptSessionKey(encryptionKey.privateKey, encryptedSessionKeyByte)
        val sessionKey = SecretKeySpec(sessionKeyByte, 0, sessionKeyByte.size, AES_KEY)
        val cipher = Cipher.getInstance(AES_CIPHER)
        if (cipherMode == Cipher.ENCRYPT_MODE) {
            cipher.init(Cipher.ENCRYPT_MODE, sessionKey)
        } else {
            cipher.init(Cipher.DECRYPT_MODE, sessionKey)
        }
        return cipher
    }

    fun encryptText(encryptionKey: EncryptionKey, textToEncrypt: String): String {
        val cipher = getSessionKeyCipher(encryptionKey, Cipher.ENCRYPT_MODE)
        val encryptedTextByte = cipher.doFinal(textToEncrypt.toByteArray())
        return Base64.encodeToString(encryptedTextByte, Base64.DEFAULT)
    }

    fun decryptText(encryptionKey: EncryptionKey, textToDecrypt: String): String {
        val cipher = getSessionKeyCipher(encryptionKey, Cipher.DECRYPT_MODE)
        val decryptedTextByte = cipher.doFinal(Base64.decode(textToDecrypt, Base64.DEFAULT))
        return String(decryptedTextByte)
    }
}