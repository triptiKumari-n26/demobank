package com.springboot.tutorial.demobank.datasource

import com.springboot.tutorial.demobank.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
    fun retrieveOneBank(accountNumber: String): Bank
    fun createNewBank(bank: Bank): Bank
    fun updateBank(bank: Bank): Bank
    fun deleteBank(bank: Bank): Unit

}