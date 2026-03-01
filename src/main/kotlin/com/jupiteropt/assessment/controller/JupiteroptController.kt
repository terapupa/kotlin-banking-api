package com.jupiteropt.assessment.controller

import com.jupiteropt.assessment.domain.*
import com.jupiteropt.assessment.exception.AppException
import com.jupiteropt.assessment.repository.AccountEntityRepository
import com.jupiteropt.assessment.repository.CustomerEntityRepository
import com.jupiteropt.assessment.repository.TransferHistoryEntityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
class JupiteroptController
@Autowired
constructor(
    private val accountEntityRepository: AccountEntityRepository,
    private val transferHistoryEntityRepository: TransferHistoryEntityRepository,
    private val customerEntityRepository: CustomerEntityRepository
) {

    /**
     * Create customer account and add initial deposit.
     *
     * @param dto - CreateAccountDto.
     * @return - AccountEntity.
     */
    @PutMapping(path = ["/bank/createAccount"])
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun createAccount(@RequestBody dto: CreateAccountDto): ResponseEntity<AccountEntity> {
        val fName = dto.firstName.takeIf { it.isNotEmpty() }
            ?: throw AppException("Customer name is empty")
        val lName = dto.lastName.takeIf { it.isNotEmpty() }
            ?: throw AppException("Customer name is empty")
        val amount = dto.initialDeposit.takeIf { it > 0 }
            ?: throw AppException("Initial deposit shuld be > 0")

        val customerEntity = customerEntityRepository
            .findByFirstNameAndLastName(fName, lName)
            .orElse(CustomerEntity(fName, lName))

        val accountEntity = AccountEntity()
        accountEntity.balance = amount
        accountEntity.customerEntity = customerEntity

        val savedAccount = accountEntityRepository.save(accountEntity)
        return ResponseEntity(savedAccount, HttpStatus.OK)
    }

    /**
     * Transfer from account to account.
     *
     * @param dto - TransferDto data.
     * @return - TransferResultDto.
     */
    @PostMapping(path = ["/bank/transfer"])
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun transfer(@RequestBody dto: TransferDto): ResponseEntity<TransferHistoryEntity> {
        val from = accountEntityRepository.findByAccountId(dto.accountFrom)
            .orElseThrow { AppException("Account not found") }
        val to = accountEntityRepository.findByAccountId(dto.accountTo)
            .orElseThrow { AppException("Account not found") }

        val amount = dto.amount.takeIf { it > 0 }
            ?: throw AppException("Transfer anount should be > 0")
        val balance = from.balance.takeIf { it > amount }
            ?: throw AppException("Not enough fund for transfer")

        from.balance = balance - amount
        to.balance = from.balance + amount

        accountEntityRepository.save(from)
        accountEntityRepository.save(to)

        val transferHistoryEntity = TransferHistoryEntity(
            null,
            dto.amount,
            from.accountId ?: 0,
            to.accountId ?: 0
        )

        val savedTransfer = transferHistoryEntityRepository.save(transferHistoryEntity)
        return ResponseEntity(savedTransfer, HttpStatus.OK)
    }

    /**
     * Retrieve account balance.
     *
     * @param account - bank account.
     * @return - AccountEntity
     */
    @GetMapping("/bank/{account}/balance")
    @ResponseBody
    fun getAccountById(@PathVariable account: Long): ResponseEntity<AccountEntity> {
        val entity = accountEntityRepository.findByAccountId(account)
            .orElseThrow { AppException("Account not found") }
        return ResponseEntity(entity, HttpStatus.OK)
    }

    /**
     * Retrieve account transfer history.
     *
     * @param account - bank account transferred from.
     * @return - List of TransferHistoryEntity.
     */
    @GetMapping("/bank/{account}/history")
    @ResponseBody
    fun getHistoryByAccountId(@PathVariable account: Long): ResponseEntity<List<TransferHistoryEntity>> {
        val history = transferHistoryEntityRepository.findByAccountFromOrAccountTo(account, account)
        return ResponseEntity(history, HttpStatus.OK)
    }
}

