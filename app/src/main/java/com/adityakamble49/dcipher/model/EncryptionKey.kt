package com.adityakamble49.dcipher.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.security.PrivateKey
import java.security.PublicKey

/**
 * Encryption Keys - contains pubic and private key to encryptSessionKey and decryptSessionKey data
 *
 * @author Aditya Kamble
 * @since 10/12/2017
 */
@Entity(tableName = "encryption_keys",
        indices = [(Index(value = *arrayOf("name"), unique = true))])
data class EncryptionKey(
        @PrimaryKey(autoGenerate = true) var id: Int,
        var name: String,
        var encryptedSessionKey: String,
        var publicKey: PublicKey,
        var privateKey: PrivateKey) : Serializable