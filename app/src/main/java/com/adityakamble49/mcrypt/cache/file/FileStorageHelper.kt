package com.adityakamble49.mcrypt.cache.file

import android.content.Context
import android.net.Uri
import android.os.Environment
import com.adityakamble49.mcrypt.di.scope.PerApplication
import com.adityakamble49.mcrypt.model.EncryptionKey
import java.io.*
import javax.inject.Inject

/**
 * Storage Manager to write and read file
 *
 * @author Aditya Kamble
 * @since 24/12/2017
 */
@PerApplication
class FileStorageHelper @Inject constructor(
        private val appContext: Context) {

    private val DIR_MCRYPT_KEYS = "mcrypt_keys"

    private fun writeObjectToFile(filePath: String, fileName: String, content: Serializable): Uri {
        if (!isExternalStorageAvailable()) {
            throw ExternalStorageException("External Storage not Available")
        }
        if (isExternalStorageReadOnly()) {
            throw ExternalStorageException("External Storage read only")
        }

        val file = File(appContext.getExternalFilesDir(filePath), fileName)
        val fileUri = Uri.fromFile(file)
        val fos = FileOutputStream(file)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(content)
        oos.close()
        fos.close()
        return fileUri
    }

    fun readObjectFromFile(uri: Uri): Any {
        if (!isExternalStorageAvailable()) {
            throw ExternalStorageException("External Storage not Available")
        }
        if (isExternalStorageReadOnly()) {
            throw ExternalStorageException("External Storage read only")
        }

        val fis = appContext.contentResolver.openInputStream(uri)
        val ois = ObjectInputStream(fis)
        val fetchedObject = ois.readObject()
        ois.close()
        fis.close()
        return fetchedObject
    }

    fun writeEncryptionKeyToFile(encryptionKey: EncryptionKey): Uri {
        val fileName = "${encryptionKey.name}.mck"
        return writeObjectToFile(DIR_MCRYPT_KEYS, fileName, encryptionKey)
    }

    private fun isExternalStorageReadOnly(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED_READ_ONLY == externalStorageState) return true
        return false
    }

    private fun isExternalStorageAvailable(): Boolean {
        val externalStorageState = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == externalStorageState) return true
        return false
    }

    private inner class ExternalStorageException constructor(
            override val message: String) : Exception()
}