package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;
import com.bankapp.digitalbankingsystem.service.ExchangeRateService;
import com.bankapp.digitalbankingsystem.service.UserService;

import java.security.Principal;

/**
 * Kimlik doğrulama ve yetkilendirme işlemlerini yöneten controller sınıfı.
 * Kullanıcı kaydı, girişi ve ana sayfa yönlendirmelerini gerçekleştirir.
 * Dashboard sayfası için gerekli verileri hazırlar.
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    /**
     * Ana sayfayı login sayfasına yönlendirir
     * @return Login sayfasına yönlendirme
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    /**
     * Kayıt formunu gösterir
     * @param model View'a aktarılacak veriler
     * @return Kayıt sayfası
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    /**
     * Yeni kullanıcı kaydını gerçekleştirir
     * @param user Kaydedilecek kullanıcı bilgileri
     * @param redirectAttributes Yönlendirme sonrası gösterilecek mesajlar
     * @return Login sayfasına yönlendirme
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("success", "Kayıt başarıyla tamamlandı! Giriş yapabilirsiniz.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/register";
        }
    }

    /**
     * Login formunu gösterir
     * @return Login sayfası
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    /**
     * Dashboard sayfasını gösterir
     * @param model View'a aktarılacak veriler
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return Dashboard sayfası
     */
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        User user = userService.findByTcNo(principal.getName());
        var account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);
        model.addAttribute("account", account);
        model.addAttribute("user", user);
        model.addAttribute("exchangeRates", exchangeRateService.getExchangeRates());
        return "dashboard";
    }
} 