package com.peakscircle.legalinfo

import com.peakscircle.legalinfo.domain.NetworkResult
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class LegalInfoTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun registerWrongId() = runTest {
        val result = LegalInfo.getInstance().register("")

        assertTrue(result is NetworkResult.Error && result.exception is LegalInfo.WrongIdException)
    }

    @Test
    fun registerNotConfigured() = runTest {
        val result = LegalInfo.getInstance().register("test")

        assertTrue(result is NetworkResult.Error && result.exception is LegalInfo.NotConfiguredException)
    }

    @Test
    fun getDocumentsWrongId() = runTest {
        val result = LegalInfo.getInstance().getDocuments("", DocumentType.ALL)

        assertTrue(result is NetworkResult.Error && result.exception is LegalInfo.WrongIdException)
    }

    @Test
    fun getDocumentsNotConfigured() = runTest {
        val result = LegalInfo.getInstance().getDocuments("test", DocumentType.ALL)

        assertTrue(result is NetworkResult.Error && result.exception is LegalInfo.NotConfiguredException)
    }


}