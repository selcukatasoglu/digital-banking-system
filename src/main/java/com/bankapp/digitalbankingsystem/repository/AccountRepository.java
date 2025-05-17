package com.bankapp.digitalbankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.digitalbankingsystem.model.Account;

import java.util.List;

/**
 * Hesap verilerine erişim sağlayan repository interface'i.
 * JpaRepository'den kalıtım alarak temel CRUD işlemlerini sağlar.
 * Kullanıcıya ait hesapları listeleme, hesap numarası kontrolü ve hesap arama işlemlerini içerir.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Belirli bir kullanıcıya ait tüm hesapları getirir
     * @param userId Kullanıcı ID'si
     * @return Kullanıcıya ait hesap listesi
     */
    List<Account> findByUserId(Long userId);

    /**
     * Verilen hesap numarasının sistemde kayıtlı olup olmadığını kontrol eder
     * @param accountNumber Kontrol edilecek hesap numarası
     * @return Hesap numarası kayıtlı ise true, değilse false
     */
    boolean existsByAccountNumber(String accountNumber);

    /**
     * Hesap numarasına göre hesap bilgilerini getirir
     * @param accountNumber Hesap numarası
     * @return Hesap bilgileri
     */
    Account findByAccountNumber(String accountNumber);
} 