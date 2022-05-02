package com.springboot.tutorial.demobank.model

data class Bank (
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int
    )