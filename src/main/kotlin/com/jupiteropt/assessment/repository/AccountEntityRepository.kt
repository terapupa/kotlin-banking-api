package com.jupiteropt.assessment.repository

import com.jupiteropt.assessment.domain.AccountEntity
import java.util.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountEntityRepository : CrudRepository<AccountEntity, Long> {
    fun findByAccountId(accountId: Long): Optional<AccountEntity>
}

