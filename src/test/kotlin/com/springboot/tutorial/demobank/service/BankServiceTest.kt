package com.springboot.tutorial.demobank.service

import com.springboot.tutorial.demobank.datasource.BankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {

    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)

    @Test
    fun `should call data source to retrieve bank` () {
        //given
        //every { dataSource.retrieveBanks()  } returns emptyList()

        //when
        bankService.getBanks()

        //then
        verify(exactly = 1) { dataSource.retrieveBanks() }
    }
}