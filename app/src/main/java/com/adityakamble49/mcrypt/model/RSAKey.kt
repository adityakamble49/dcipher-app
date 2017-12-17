package com.adityakamble49.mcrypt.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.security.PrivateKey
import java.security.PublicKey

/**
 * RSA Key Model - contains pubic and private key to encrypt and decrypt data
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Entity(tableName = "rsa_key_pair",
        indices = [(Index(value = arrayOf("name"), unique = true))])
data class RSAKeyPair(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var publicKey: PublicKey,
        var privateKey: PrivateKey
)