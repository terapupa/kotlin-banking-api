package com.jupiteropt.assessment.domain

import jakarta.persistence.*

/**
 * The data persistence entity.
 * The class represents the Transfer History data model used by JPA and Hibernate ORM to represent the MySQL schema.
 */
@Entity
@Table(name = "transfer_history_entity")
data class TransferHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name = "amount", nullable = false)
    var amount: Double = 0.0,

    @Column(name = "account_from", nullable = false)
    var accountFrom: Long = 0,

    @Column(name = "account_to", nullable = false)
    var accountTo: Long = 0,
) {

    override fun toString(): String {
        return "${this::class.simpleName}(" +
                "id = $id, " +
                "amount = $amount, " +
                "accountFrom = $accountFrom, " +
                "accountTo = $accountTo)"
    }
}

