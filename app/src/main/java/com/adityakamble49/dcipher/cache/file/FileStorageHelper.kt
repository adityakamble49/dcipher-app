package com.adityakamble49.dcipher.cache.file

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import com.adityakamble49.dcipher.di.scope.PerApplication
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

    fun writeObjectToFile(filePath: String, fileName: String, content: Serializable): Uri {
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

    fun readTextFromFile(uri: Uri): String {
        if (!isExternalStorageAvailable()) {
            throw ExternalStorageException("External Storage not Available")
        }
        if (isExternalStorageReadOnly()) {
            throw ExternalStorageException("External Storage read only")
        }

        val fis = appContext.contentResolver.openInputStream(uri)
        val br = BufferedReader(InputStreamReader(fis))
        val sb = StringBuilder()
        br.lineSequence().forEach { sb.append(it).append("\n") }
        br.close()
        fis.close()
        return sb.toString()
    }

    fun getFileName(uri: Uri): String {
        if (!isExternalStorageAvailable()) {
            throw ExternalStorageException("External Storage not Available")
        }
        if (isExternalStorageReadOnly()) {
            throw ExternalStorageException("External Storage read only")
        }

        lateinit var cursor: Cursor
        lateinit var result: String
        try {
            cursor = appContext.contentResolver.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
            return result
        } finally {
            cursor.close()
        }
    }

    fun writeFileToExternalStorage(fileDir: String, fileName: String, content: String) {
        if (!isExternalStorageAvailable()) {
            throw ExternalStorageException("External Storage not Available")
        }
        if (isExternalStorageReadOnly()) {
            throw ExternalStorageException("External Storage read only")
        }

        val file = File("$fileDir/$fileName")
        val fos = FileOutputStream(file)
        val pw = PrintWriter(fos)
        pw.print(content)
        pw.flush()
        pw.close()
        fos.close()
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