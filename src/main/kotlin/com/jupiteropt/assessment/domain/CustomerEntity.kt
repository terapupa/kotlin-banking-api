package com.jupiteropt.assessment.domain

import jakarta.persistence.*

/**
 * The data persistence entity.
 * The class represents the Customer data model used by JPA and Hibernate ORM to represent the MySQL schema.
 */
@Entity
@Table(name = "customer_entity")
data class CustomerEntity(
    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
) {
    constructor(firstName: String, lastName: String) : this(firstName, lastName, null)

    override fun toString(): String {
        return "${this::class.simpleName}(" +
                "id = $id, " +
                "firstName = $firstName, " +
                "lastName = $lastName)"
    }
}

