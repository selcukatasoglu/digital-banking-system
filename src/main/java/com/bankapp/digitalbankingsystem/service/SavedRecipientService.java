package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.digitalbankingsystem.model.SavedRecipient;
import com.bankapp.digitalbankingsystem.repository.SavedRecipientRepository;

import java.util.List;

/**
 * Kayıtlı alıcıları yöneten servis sınıfı.
 * Kullanıcıların sık kullandıkları alıcıları kaydetme, listeleme ve silme işlemlerini gerçekleştirir.
 */
@Service
public class SavedRecipientService {
    @Autowired
    private SavedRecipientRepository savedRecipientRepository;

    /**
     * Kullanıcıya ait kayıtlı alıcıları getirir
     * @param userId Kullanıcı ID'si
     * @return Kayıtlı alıcı listesi
     */
    public List<SavedRecipient> getRecipientsForUser(Long userId) {
        return savedRecipientRepository.findByUserId(userId);
    }

    /**
     * Yeni bir kayıtlı alıcı ekler
     * @param recipient Eklenecek kayıtlı alıcı
     * @return Kaydedilen alıcı
     */
    @Transactional
    public SavedRecipient saveRecipient(SavedRecipient recipient) {
        return savedRecipientRepository.save(recipient);
    }

    /**
     * Kayıtlı alıcıyı siler
     * @param id Silinecek alıcının ID'si
     * @param userId Kullanıcı ID'si
     */
    @Transactional
    public void deleteRecipient(Long id, Long userId) {
        savedRecipientRepository.deleteByIdAndUserId(id, userId);
    }
} 