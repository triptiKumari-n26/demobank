package com.springboot.tutorial.demobank.service

import com.springboot.tutorial.demobank.datasource.BankDataSource
import com.springboot.tutorial.demobank.model.Bank
import org.springframework.stereotype.Service

@Service
class BankService(private val dataSource: BankDataSource) {

    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBankWithAccountNumber(accountNumber: String): Bank = dataSource.retrieveOneBank(accountNumber)
    fun createNewBank(bank: Bank): Bank = dataSource.createNewBank(bank)
    fun updateBank(bank: Bank): Bank = dataSource.updateBank(bank)
    fun deleteBank(bank: Bank): Unit = dataSource.deleteBank(bank)

}