package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bankapp.digitalbankingsystem.model.SavedRecipient;
import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.service.SavedRecipientService;
import com.bankapp.digitalbankingsystem.service.UserService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Kayıtlı alıcıları yöneten controller sınıfı.
 * Kullanıcıların sık kullandıkları alıcıları kaydetme, listeleme ve silme işlemlerini gerçekleştirir.
 * REST API endpoint'leri sunar.
 */
@Controller
@RequestMapping("/api/saved-recipients")
public class SavedRecipientController {

    @Autowired
    private SavedRecipientService savedRecipientService;

    @Autowired
    private UserService userService;

    /**
     * Kullanıcının kayıtlı alıcılarını getirir
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return Kayıtlı alıcı listesi
     */
    @GetMapping
    @ResponseBody
    public List<SavedRecipient> getRecipients(Principal principal) {
        User user = userService.findByTcNo(principal.getName());
        return savedRecipientService.getRecipientsForUser(user.getId());
    }

    /**
     * Yeni bir kayıtlı alıcı ekler
     * @param accountNumber Alıcı hesap numarası
     * @param recipientName Alıcı adı
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return İşlem sonucu
     */
    @PostMapping
    @ResponseBody
    public Map<String, Object> addRecipient(@RequestParam String accountNumber, @RequestParam String recipientName, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = userService.findByTcNo(principal.getName());
            SavedRecipient recipient = new SavedRecipient();
            recipient.setUser(user);
            recipient.setAccountNumber(accountNumber);
            recipient.setRecipientName(recipientName);
            savedRecipientService.saveRecipient(recipient);
            response.put("success", true);
            response.put("message", "Alıcı başarıyla kaydedildi!");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Alıcı kaydedilirken bir hata oluştu!");
        }
        return response;
    }

    /**
     * Kayıtlı alıcıyı siler
     * @param id Silinecek alıcının ID'si
     * @param principal Giriş yapmış kullanıcı bilgisi
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public void deleteRecipient(@PathVariable Long id, Principal principal) {
        User user = userService.findByTcNo(principal.getName());
        savedRecipientService.deleteRecipient(id, user.getId());
    }
} 