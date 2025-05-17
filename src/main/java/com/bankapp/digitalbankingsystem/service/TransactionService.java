package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.digitalbankingsystem.model.*;
import com.bankapp.digitalbankingsystem.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * İşlem kayıtlarını yöneten servis sınıfı.
 * Para transferi, yatırma ve çekme işlemlerinin kayıtlarını tutar.
 * Her işlem için tarih, miktar ve işlem tipi bilgilerini saklar.
 */
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Hesaptan hesaba para transferi kaydı oluşturur
     * @param from Gönderen hesap
     * @param to Alıcı hesap
     * @param amount Transfer miktarı
     * @param fee İşlem ücreti
     */
    public void createTransfer(Account from, Account to, BigDecimal amount, BigDecimal fee) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(from);
        transaction.setToAccount(to);
        transaction.setAmount(amount);
        transaction.setTransactionFee(fee);
        transaction.setTransactionType(TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    /**
     * Hesaba para yatırma kaydı oluşturur
     * @param to Para yatırılacak hesap
     * @param amount Yatırılan miktar
     */
    public void createDeposit(Account to, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(null);
        transaction.setToAccount(to);
        transaction.setAmount(amount);
        transaction.setTransactionFee(BigDecimal.ZERO);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    /**
     * Hesaptan para çekme kaydı oluşturur
     * @param from Para çekilecek hesap
     * @param amount Çekilen miktar
     */
    public void createWithdrawal(Account from, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(from);
        transaction.setToAccount(null);
        transaction.setAmount(amount);
        transaction.setTransactionFee(BigDecimal.ZERO);
        transaction.setTransactionType(TransactionType.WITHDRAWAL);
        transaction.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
} 