package com.adityakamble49.mcrypt.model

import android.arch.persistence.room.Entity

/**
 * RSA Key Model - contains pubic and private key to encrypt and decrypt data
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Entity(tableName = "rsa_key_pair")
data class RSAKeyPair(
        var id: Int,
        var name: String,
        var publicKey: String,
        var privateKey: String
)