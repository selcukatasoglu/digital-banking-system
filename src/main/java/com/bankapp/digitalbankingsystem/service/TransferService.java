package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;

import java.math.BigDecimal;

/**
 * Para transferi işlemlerini yöneten servis sınıfı.
 * Hesaptan hesaba para transferi işlemlerini gerçekleştirir.
 * Transfer işlemleri için gerekli kontrolleri yapar ve işlem ücretlerini hesaplar.
 */
@Service
public class TransferService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * Hesaptan hesaba para transferi gerçekleştirir
     * @param senderAccount Gönderen hesap
     * @param toAccount Alıcı hesap numarası
     * @param amount Transfer miktarı
     * @return İşlem sonucu mesajı
     */
    public String transfer(Account senderAccount, String toAccount, BigDecimal amount) {
        if (toAccount.equals(senderAccount.getAccountNumber())) {
            return "Kendi hesabınıza transfer yapamazsınız!";
        }

        Account receiverAccount = accountRepository.findByAccountNumber(toAccount);
        if (receiverAccount == null) {
            return "Alıcı hesap bulunamadı!";
        }

        BigDecimal fee = BigDecimal.valueOf(5.0);
        BigDecimal total = amount.add(fee);

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            return "Transfer tutarı 0'dan büyük olmalıdır.";
        }

        if (senderAccount.getBalance().compareTo(total) < 0) {
            return "Yetersiz bakiye!";
        }

        senderAccount.setBalance(senderAccount.getBalance().subtract(total));
        receiverAccount.setBalance(receiverAccount.getBalance().add(amount));
        accountRepository.save(senderAccount);
        accountRepository.save(receiverAccount);

        transactionService.createTransfer(senderAccount, receiverAccount, amount, fee);

        return "success";
    }
} 