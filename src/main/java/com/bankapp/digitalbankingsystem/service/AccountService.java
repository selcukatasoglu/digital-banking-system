package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.math.BigDecimal;

/**
 * Hesap işlemlerini yöneten servis sınıfı.
 * Hesap oluşturma, para yatırma ve para çekme işlemlerini gerçekleştirir.
 * Her işlem için gerekli kontrolleri yapar ve işlem kayıtlarını tutar.
 */
@Service
public class AccountService {
    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * Yeni bir hesap oluşturur
     * @param user Hesap sahibi kullanıcı
     * @return Oluşturulan hesap
     */
    @Transactional
    public Account createAccount(User user) {
        logger.info("Hesap oluşturma başladı - Kullanıcı ID: {}", user.getId());
        
        String accountNumber = generateAccountNumber();
        logger.info("Oluşturulan hesap numarası: {}", accountNumber);
        
        // Hesap numarası benzersiz olmalı
        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = generateAccountNumber();
            logger.info("Hesap numarası tekrar oluşturuldu: {}", accountNumber);
        }

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setUser(user);
        
        Account savedAccount = accountRepository.save(account);
        logger.info("Hesap başarıyla kaydedildi - ID: {}, Hesap No: {}", savedAccount.getId(), savedAccount.getAccountNumber());
        
        return savedAccount;
    }

    /**
     * Benzersiz 9 haneli hesap numarası oluşturur
     * @return Oluşturulan hesap numarası
     */
    private String generateAccountNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    /**
     * Hesaba para yatırma işlemi gerçekleştirir
     * @param account Para yatırılacak hesap
     * @param amount Yatırılacak miktar
     * @return İşlem sonucu mesajı
     */
    public String deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Yatırılacak tutar 0'dan büyük olmalıdır.";
        }
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        transactionService.createDeposit(account, amount);
        return "success";
    }

    /**
     * Hesaptan para çekme işlemi gerçekleştirir
     * @param account Para çekilecek hesap
     * @param amount Çekilecek miktar
     * @return İşlem sonucu mesajı
     */
    public String withdraw(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Çekilecek tutar 0'dan büyük olmalıdır.";
        }
        if (account.getBalance().compareTo(amount) < 0) {
            return "Yetersiz bakiye!";
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        transactionService.createWithdrawal(account, amount);
        return "success";
    }
} 