package com.adityakamble49.mcrypt.db

import android.arch.persistence.room.TypeConverter
import com.adityakamble49.mcrypt.utils.Constants.Companion.ALGORITHM_RSA
import com.google.gson.Gson
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.RSAPrivateKeySpec
import java.security.spec.RSAPublicKeySpec

/**
 * Type Converters for Database
 *
 * @author Aditya Kamble
 * @since 12/12/2017
 */
class MCryptTypeConverters {

    @TypeConverter
    fun privateKeyToString(privateKey: PrivateKey): String {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        val private = keyFactory.getKeySpec(privateKey, RSAPrivateKeySpec::class.java)
        return Gson().toJson(private)
    }

    @TypeConverter
    fun stringToPrivateKey(privateKeyStr: String): PrivateKey {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        val keySpec = Gson().fromJson(privateKeyStr, RSAPrivateKeySpec::class.java)
        return keyFactory.generatePrivate(keySpec)
    }

    @TypeConverter
    fun publicKeyToString(publicKey: PublicKey): String {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        val public = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec::class.java)
        return Gson().toJson(public)
    }

    @TypeConverter
    fun stringToPublicKey(publicKeyStr: String): PublicKey {
        val keyFactory = KeyFactory.getInstance(ALGORITHM_RSA)
        val keySpec = Gson().fromJson(publicKeyStr, RSAPublicKeySpec::class.java)
        return keyFactory.generatePublic(keySpec)
    }


}