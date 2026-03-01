package com.jupiteropt.assessment.repository

import com.jupiteropt.assessment.domain.TransferHistoryEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransferHistoryEntityRepository : CrudRepository<TransferHistoryEntity, Long> {
    fun findByAccountFromOrAccountTo(accountFrom: Long, accountTo: Long): List<TransferHistoryEntity>
}

