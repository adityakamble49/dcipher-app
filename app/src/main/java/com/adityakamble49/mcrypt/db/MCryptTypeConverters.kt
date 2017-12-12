package com.adityakamble49.mcrypt.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import java.security.PrivateKey
import java.security.PublicKey

/**
 * Type Converters for Database
 *
 * @author Aditya Kamble
 * @since 12/12/2017
 */
class MCryptTypeConverters {

    @TypeConverter
    fun privateKeyToString(privateKey: PrivateKey): String = Gson().toJson(privateKey)

    @TypeConverter
    fun stringToPrivateKey(privateKeyStr: String): PrivateKey =
            Gson().fromJson(privateKeyStr, PrivateKey::class.java)

    @TypeConverter
    fun publicKeyToString(publicKey: PublicKey): String = Gson().toJson(publicKey)

    @TypeConverter
    fun stringToPublicKey(publicKeyStr: String): PublicKey =
            Gson().fromJson(publicKeyStr, PublicKey::class.java)


}