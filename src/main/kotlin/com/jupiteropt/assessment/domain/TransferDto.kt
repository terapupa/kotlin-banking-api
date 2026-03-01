package com.jupiteropt.assessment.domain

data class TransferDto(
    val accountFrom: Long,
    val accountTo: Long,
    val amount: Double
) {
    override fun toString(): String {
        return "${this::class.simpleName}(" +
                "accountFrom = $accountFrom, " +
                "accountTo = $accountTo, " +
                "amount = $amount)"
    }
}

