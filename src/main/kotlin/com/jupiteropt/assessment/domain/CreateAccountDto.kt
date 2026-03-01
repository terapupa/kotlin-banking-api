package com.jupiteropt.assessment.domain

data class CreateAccountDto(
    val firstName: String,
    val lastName: String,
    val account: Long,
    val initialDeposit: Double
)

