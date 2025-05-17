package com.bankapp.digitalbankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.model.Transaction;

import java.util.List;

/**
 * İşlem verilerine erişim sağlayan repository interface'i.
 * JpaRepository'den kalıtım alarak temel CRUD işlemlerini sağlar.
 * Hesap bazlı işlem geçmişi sorgulama ve son işlemleri getirme işlemlerini içerir.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    /**
     * Belirli bir hesabın gönderdiği veya aldığı tüm işlemleri tarih sırasına göre getirir
     * @param from Gönderen hesap
     * @param to Alıcı hesap
     * @return İşlem listesi (en yeniden en eskiye)
     */
    List<Transaction> findByFromAccountOrToAccountOrderByTransactionDateDesc(Account from, Account to);

    /**
     * Belirli bir hesabın gönderdiği veya aldığı son 3 işlemi getirir
     * @param from Gönderen hesap
     * @param to Alıcı hesap
     * @return Son 3 işlem listesi
     */
    List<Transaction> findTop3ByFromAccountOrToAccountOrderByTransactionDateDesc(Account from, Account to);
} 