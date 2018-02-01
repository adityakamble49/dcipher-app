package com.adityakamble49.dcipher.utils

/**
 * Constants
 *
 * @author Aditya Kamble
 * @since 17/12/2017
 */
class Constants {

    companion object {
        const val ALGORITHM_RSA = "RSA"
        const val AES_KEY = "AES"
        const val AES_CIPHER = "AES/CBC/PKCS5Padding"
        const val TEXT_INTENT = "text/plain"
    }

    object DCipherDir {
        const val DCIPHER_KEYS = "dcipher_keys"
        const val DCIPHER_ENCRYPTED_FILES = "dcipher_encrypted_files"
        const val DCIPHER_FILES_EXTERNAL = "dcipher_files"
        const val DCIPHER_DECRYPTED_FILES = "$DCIPHER_FILES_EXTERNAL/decrypted"
    }

    object DCipherFileFormats {
        const val DCIPHER_KEY = "dkf"
        const val DCIPHER_ENCRYPTED_FILE = "dcf"
        const val DCIPHER_DECRYPTED_FILE = "txt"
    }

    object ShareEncryptionType {
        const val TEXT = 0
        const val FILE = 1
    }

    object ReferenceUrls {
        const val PERSONAL_WEBSITE = "http://adityakamble49.com"
        const val GITHUB_PROFILE = "https://github.com/adityakamble49"
        const val TWITTER_PROFILE = "https://twitter.com/adityakamble49"
    }
}