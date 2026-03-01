package com.jupiteropt.assessment.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.proxy.HibernateProxy

/**
 * The data persistence entity.
 * The class represents the Account data model used by JPA and Hibernate ORM to represent the MySQL schema.
 */
@Entity
@Table(name = "account_entity")
data class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    var accountId: Long? = null,

    @Column(name = "balance", nullable = false)
    var balance: Double = 0.0,

    @ManyToOne(cascade = [CascadeType.PERSIST], fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_entity_id")
    var customerEntity: CustomerEntity? = null
)

