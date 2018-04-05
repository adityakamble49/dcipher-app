package com.adityakamble49.dcipher.interactor

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.logging.Logger

/**
 * @author Aditya Kamble
 * @since 11/2/2018
 */
@RunWith(MockitoJUnitRunner::class)
class BuildEncryptionKeyUseCaseTest {

    private val logger = Logger.getLogger(javaClass.name)

    private lateinit var buildEncryptionKeyUseCase: BuildEncryptionKeyUseCase

    @Before
    fun setUp() {
        buildEncryptionKeyUseCase = mock(BuildEncryptionKeyUseCase::class.java)
    }

    @Test
    fun buildEncryptionKey() {
        val encryptionKeyName = "test key name"
        buildEncryptionKeyUseCase.execute(encryptionKeyName)
    }
}