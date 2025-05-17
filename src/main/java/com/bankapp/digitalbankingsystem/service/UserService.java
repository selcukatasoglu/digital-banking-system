package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Kullanıcı işlemlerini yöneten servis sınıfı.
 * Kullanıcı kaydı, girişi ve bilgi sorgulama işlemlerini gerçekleştirir.
 * TC Kimlik doğrulama ve şifre güvenliği kontrollerini yapar.
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    /**
     * Yeni kullanıcı kaydı oluşturur
     * @param user Kaydedilecek kullanıcı bilgileri
     * @return Kaydedilen kullanıcı
     */
    @Transactional
    public User registerUser(User user) {
        // TC Kimlik doğrulama
        String tcNo = user.getTcNo();
        if (tcNo == null || tcNo.length() != 11 || !tcNo.matches("\\d+")) {
            throw new RuntimeException("Geçersiz TC Kimlik numarası! TC Kimlik numarası 11 haneli olmalıdır.");
        }

        // İlk hane 0 olamaz
        if (tcNo.charAt(0) == '0') {
            throw new RuntimeException("Geçersiz TC Kimlik numarası! İlk hane 0 olamaz.");
        }

        // TC Kimlik algoritması kontrolü
        int oddSum = 0; // 1,3,5,7,9. hanelerin toplamı
        int evenSum = 0; // 2,4,6,8. hanelerin toplamı
        int totalSum = 0; // İlk 10 hanenin toplamı

        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(tcNo.charAt(i));
            if (i % 2 == 0) {
                oddSum += digit;
            } else {
                evenSum += digit;
            }
            totalSum += digit;
        }

        // 10. hane kontrolü
        int digit10 = ((oddSum * 7) - evenSum) % 10;
        if (digit10 != Character.getNumericValue(tcNo.charAt(9))) {
            throw new RuntimeException("Geçersiz TC Kimlik numarası! Lütfen kontrol ediniz.");
        }

        // 11. hane kontrolü
        int digit11 = totalSum % 10;
        if (digit11 != Character.getNumericValue(tcNo.charAt(10))) {
            throw new RuntimeException("Geçersiz TC Kimlik numarası! Lütfen kontrol ediniz.");
        }

        // TC Kimlik ve Email kontrolü
        if (userRepository.existsByTcNo(user.getTcNo())) {
            throw new RuntimeException("Bu TC Kimlik numarası zaten kayıtlı!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Bu email adresi zaten kayıtlı!");
        }

        // Şifreyi hashle
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Kullanıcıyı kaydet
        User savedUser = userRepository.save(user);
        logger.info("Kullanıcı kaydedildi: {}", savedUser.getId());
        
        // Otomatik hesap aç
        try {
            var account = accountService.createAccount(savedUser);
            logger.info("Hesap oluşturuldu: {}", account.getAccountNumber());
        } catch (Exception e) {
            logger.error("Hesap oluşturulurken hata: ", e);
            throw e;
        }
        
        return savedUser;
    }

    /**
     * E-posta adresine göre kullanıcı bilgilerini getirir
     * @param email Kullanıcı e-posta adresi
     * @return Kullanıcı bilgileri
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }

    /**
     * TC Kimlik numarasına göre kullanıcı bilgilerini getirir
     * @param tcNo TC Kimlik numarası
     * @return Kullanıcı bilgileri
     */
    public User findByTcNo(String tcNo) {
        return userRepository.findByTcNo(tcNo)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));
    }
} 