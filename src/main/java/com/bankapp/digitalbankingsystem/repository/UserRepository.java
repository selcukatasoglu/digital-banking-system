package com.bankapp.digitalbankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.digitalbankingsystem.model.User;

import java.util.Optional;

/**
 * Kullanıcı verilerine erişim sağlayan repository interface'i.
 * JpaRepository'den kalıtım alarak temel CRUD işlemlerini sağlar.
 * E-posta ve TC Kimlik numarasına göre kullanıcı arama ve kontrol işlemlerini içerir.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * E-posta adresine göre kullanıcı bilgilerini getirir
     * @param email Kullanıcı e-posta adresi
     * @return Kullanıcı bilgileri (varsa)
     */
    Optional<User> findByEmail(String email);

    /**
     * TC Kimlik numarasına göre kullanıcı bilgilerini getirir
     * @param tcNo TC Kimlik numarası
     * @return Kullanıcı bilgileri (varsa)
     */
    Optional<User> findByTcNo(String tcNo);

    /**
     * E-posta adresinin sistemde kayıtlı olup olmadığını kontrol eder
     * @param email Kontrol edilecek e-posta adresi
     * @return E-posta adresi kayıtlı ise true, değilse false
     */
    boolean existsByEmail(String email);

    /**
     * TC Kimlik numarasının sistemde kayıtlı olup olmadığını kontrol eder
     * @param tcNo Kontrol edilecek TC Kimlik numarası
     * @return TC Kimlik numarası kayıtlı ise true, değilse false
     */
    boolean existsByTcNo(String tcNo);
} 