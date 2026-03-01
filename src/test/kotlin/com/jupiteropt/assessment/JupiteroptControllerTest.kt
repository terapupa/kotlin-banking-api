package com.jupiteropt.assessment

import com.jupiteropt.assessment.controller.JupiteroptController
import com.jupiteropt.assessment.domain.AccountEntity
import com.jupiteropt.assessment.domain.CreateAccountDto
import com.jupiteropt.assessment.domain.CustomerEntity
import com.jupiteropt.assessment.domain.TransferHistoryEntity
import com.jupiteropt.assessment.exception.AppException
import com.jupiteropt.assessment.repository.AccountEntityRepository
import com.jupiteropt.assessment.repository.CustomerEntityRepository
import com.jupiteropt.assessment.repository.TransferHistoryEntityRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.Optional
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@ExtendWith(MockitoExtension::class)
class JupiteroptControllerTest {
    @Mock
    private lateinit var accountEntityRepository: AccountEntityRepository

    @Mock
    private lateinit var customerEntityRepository: CustomerEntityRepository

    @Mock
    private lateinit var transferHistoryEntityRepository: TransferHistoryEntityRepository

    private lateinit var controller: JupiteroptController

    @BeforeEach
    fun before() {
        controller = JupiteroptController(
            accountEntityRepository,
            transferHistoryEntityRepository,
            customerEntityRepository
        )
    }

    @Test
    fun creteAccountTest() {
        whenever(accountEntityRepository.save(any())).thenReturn(AccountEntity())
        whenever(
            customerEntityRepository.findByFirstNameAndLastName(
                any(), any()
            )
        ).thenReturn(Optional.of(CustomerEntity("f", "l")))

        val dto = CreateAccountDto("first", "last", 0, 10.0)
        val entity = controller.createAccount(dto).body
        assertNotNull(entity)
    }

    @Test
    fun creteAccountExceptionTest() {
        assertThrows<AppException> {
            controller.createAccount(CreateAccountDto("", "last", 0, 10.0))
        }
        assertThrows<AppException> {
            controller.createAccount(CreateAccountDto("", "last", 0, 10.0))
        }
        assertThrows<AppException> {
            controller.createAccount(CreateAccountDto("first", "", 0, 10.0))
        }
        assertThrows<AppException> {
            controller.createAccount(CreateAccountDto("first", "", 0, 10.0))
        }
        assertThrows<AppException> {
            controller.createAccount(CreateAccountDto("first", "last", 0, -1.0))
        }
    }

    @Test
    fun getAccountByIdTest() {
        whenever(accountEntityRepository.findByAccountId(any())).thenReturn(Optional.of(AccountEntity()))
        val entity = controller.getAccountById(1).body
        assertNotNull(entity)
    }

    @Test
    fun getHistoryByAccountIdTest() {
        whenever(transferHistoryEntityRepository.findByAccountFromOrAccountTo(1, 1))
            .thenReturn(listOf(TransferHistoryEntity(1L, 1.0, 1, 1)))
        val entity = controller.getHistoryByAccountId(1).body
        assertEquals(entity?.size, 1)
    }
}

