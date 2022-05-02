package com.springboot.tutorial.demobank.controller

import com.springboot.tutorial.demobank.model.Bank
import com.springboot.tutorial.demobank.service.BankService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val bankService: BankService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e: NoSuchElementException) : ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleAlreadyExist(e: IllegalArgumentException) : ResponseEntity<String> =
        ResponseEntity(e.message, HttpStatus.BAD_REQUEST)


    @GetMapping
    fun getAllBanks() : Collection<Bank> {
        return bankService.getBanks()
    }

    @GetMapping("/{accountNumber}")
    fun getBankWithAccountNumber(@PathVariable accountNumber: String): Bank = bankService
        .getBankWithAccountNumber(accountNumber)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createNewBank(@RequestBody bank: Bank): Bank = bankService.createNewBank(bank)


    @PatchMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun updateBank(@RequestBody bank: Bank): Bank = bankService.updateBank(bank)


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBank(@RequestBody bank: Bank): Unit = bankService.deleteBank(bank)

}