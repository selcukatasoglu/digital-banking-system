package com.bankapp.digitalbankingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.digitalbankingsystem.model.SavedRecipient;

import java.util.List;
 
/**
 * Kayıtlı alıcı verilerine erişim sağlayan repository interface'i.
 * JpaRepository'den kalıtım alarak temel CRUD işlemlerini sağlar.
 * Kullanıcıya ait kayıtlı alıcıları listeleme ve silme işlemlerini içerir.
 */
public interface SavedRecipientRepository extends JpaRepository<SavedRecipient, Long> {
    /**
     * Belirli bir kullanıcıya ait tüm kayıtlı alıcıları getirir
     * @param userId Kullanıcı ID'si
     * @return Kullanıcıya ait kayıtlı alıcı listesi
     */
    List<SavedRecipient> findByUserId(Long userId);

    /**
     * Belirli bir kullanıcıya ait kayıtlı alıcıyı siler
     * @param id Silinecek kayıtlı alıcının ID'si
     * @param userId Kullanıcı ID'si
     */
    void deleteByIdAndUserId(Long id, Long userId);
} 