package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;
import com.bankapp.digitalbankingsystem.service.AccountService;
import com.bankapp.digitalbankingsystem.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * Hesap işlemlerini yöneten controller sınıfı.
 * Para yatırma ve para çekme işlemlerini gerçekleştirir.
 * Kullanıcı hesap bilgilerini görüntüleme işlemlerini yönetir.
 */
@Controller
public class AccountOperationsController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    /**
     * Hesap işlemleri sayfasını gösterir
     * @param model View'a aktarılacak veriler
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return Hesap işlemleri sayfası
     */
    @GetMapping("/account-operations")
    public String showAccountOperations(Model model, Principal principal) {
        User user = userService.findByTcNo(principal.getName());
        Account account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);
        model.addAttribute("account", account);
        return "account-operations";
    }

    /**
     * Hesaba para yatırma işlemini gerçekleştirir
     * @param amount Yatırılacak miktar
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @param redirectAttributes Yönlendirme sonrası gösterilecek mesajlar
     * @return Hesap işlemleri sayfasına yönlendirme
     */
    @PostMapping("/deposit")
    public String deposit(@RequestParam("amount") BigDecimal amount, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByTcNo(principal.getName());
        Account account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);

        String result = accountService.deposit(account, amount);
        if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Para yatırma işlemi başarılı!");
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }
        return "redirect:/account-operations";
    }

    /**
     * Hesaptan para çekme işlemini gerçekleştirir
     * @param amount Çekilecek miktar
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @param redirectAttributes Yönlendirme sonrası gösterilecek mesajlar
     * @return Hesap işlemleri sayfasına yönlendirme
     */
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam("amount") BigDecimal amount, Principal principal, RedirectAttributes redirectAttributes) {
        User user = userService.findByTcNo(principal.getName());
        Account account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);

        String result = accountService.withdraw(account, amount);
        if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Para çekme işlemi başarılı!");
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }
        return "redirect:/account-operations";
    }
} 