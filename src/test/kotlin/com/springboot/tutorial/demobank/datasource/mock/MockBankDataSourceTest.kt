package com.springboot.tutorial.demobank.datasource.mock

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {

    private val mockBankDatasource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks` () {
        //given

        //when

        val banks = mockBankDatasource.retrieveBanks()
        //then
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data` () {
        //given

        //when

        val banks = mockBankDatasource.retrieveBanks()
        //then
        assertThat(banks).allMatch {it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch {it.trust != 0.0}
        assertThat(banks).anyMatch {it.transactionFee != 1}
    }


}