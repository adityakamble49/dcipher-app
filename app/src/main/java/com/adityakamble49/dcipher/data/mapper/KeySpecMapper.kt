package com.adityakamble49.dcipher.data.mapper

import com.adityakamble49.dcipher.di.scope.PerApplication
import com.adityakamble49.dcipher.model.EncryptionKey
import com.adityakamble49.dcipher.model.EncryptionKeyShare
import com.adityakamble49.dcipher.utils.Constants.Companion.ALGORITHM_RSA
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec
import javax.inject.Inject

/**
 * KeySpecMapper
 *
 * @author Aditya Kamble
 * @since 26/1/2018
 */
@PerApplication
class KeySpecMapper @Inject constructor() {

    private fun privateKeyToRSAPrivateKeySpec(privateKey: PrivateKey): RSAPrivateKeySpec {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        return keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec::class.java)
    }

    private fun publicKeyToRSAPublicKeySpec(publicKey: PublicKey): RSAPublicKeySpec {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        return keyFactory.getKeySpec(publicKey, RSAPublicKeySpec::class.java)
    }


    private fun rSAPrivateKeySpecToPrivateKey(rsaPrivateKeySpec: RSAPrivateKeySpec): PrivateKey {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        return keyFactory.generatePrivate(rsaPrivateKeySpec)
    }

    private fun rsaPublicKeySpecToPublicKey(rsaPublicKeySpec: RSAPublicKeySpec): PublicKey {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        return keyFactory.generatePublic(rsaPublicKeySpec)
    }

    fun encryptionKeyToShare(encryptionKey: EncryptionKey): EncryptionKeyShare {
        return EncryptionKeyShare(
                encryptionKey.id,
                encryptionKey.name,
                encryptionKey.encryptedSessionKey,
                publicKeyToRSAPublicKeySpec(encryptionKey.publicKey),
                privateKeyToRSAPrivateKeySpec(encryptionKey.privateKey)
        )
    }

    fun encryptionShareToKey(encryptionKeyShare: EncryptionKeyShare): EncryptionKey {
        return EncryptionKey(
                encryptionKeyShare.id,
                encryptionKeyShare.name,
                encryptionKeyShare.encryptedSessionKey,
                rsaPublicKeySpecToPublicKey(encryptionKeyShare.publicKey),
                rSAPrivateKeySpecToPrivateKey(encryptionKeyShare.privateKey)
        )
    }
}