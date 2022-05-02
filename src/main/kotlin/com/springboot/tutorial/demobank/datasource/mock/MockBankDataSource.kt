package com.springboot.tutorial.demobank.datasource.mock

import com.springboot.tutorial.demobank.datasource.BankDataSource
import com.springboot.tutorial.demobank.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("abc", 1.0, 1),
        Bank("abc1", 2.0, 2),
        Bank("abc2", 3.0, 3)
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveOneBank(accountNumber: String): Bank =
        banks
            .firstOrNull(){ it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find bank with accountNumber = $accountNumber")

    override fun createNewBank(bank: Bank): Bank {
        if(banks.any{it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank cannot be created as account number = ${bank.accountNumber} already " +
                "exist")
        }
        banks.add(bank)
        return banks.first() {it.accountNumber == bank.accountNumber}
    }

    override fun updateBank(bank: Bank): Bank {
        val currentBank = banks
            .firstOrNull(){ it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find bank with accountNumber = $bank.accountNumber")

        banks.remove(currentBank)
        banks.add(bank)
        return bank
    }

    override fun deleteBank(bank: Bank): Unit {
        val deleteBank = banks
            .firstOrNull(){ it.accountNumber == bank.accountNumber }
            ?: throw NoSuchElementException("Could not find bank with accountNumber = $bank.accountNumber")

        banks.remove(deleteBank)
    }
}