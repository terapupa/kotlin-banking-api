package com.jupiteropt.assessment.repository

import com.jupiteropt.assessment.domain.CustomerEntity
import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerEntityRepository : CrudRepository<CustomerEntity, Long> {
    fun findByFirstNameAndLastName(firstName: String, lastName: String): Optional<CustomerEntity>
}

