package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;
import com.bankapp.digitalbankingsystem.repository.TransactionRepository;
import com.bankapp.digitalbankingsystem.service.TransferService;
import com.bankapp.digitalbankingsystem.service.UserService;

import java.math.BigDecimal;
import java.security.Principal;

/**
 * Para transferi işlemlerini yöneten controller sınıfı.
 * Hesaptan hesaba para transferi işlemlerini gerçekleştirir.
 * Transfer sayfası ve alıcı bilgilerini görüntüleme işlemlerini yönetir.
 */
@Controller
public class TransferController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferService transferService;

    /**
     * Transfer sayfasını gösterir
     * @param model View'a aktarılacak veriler
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @param toAccountNumber Alıcı hesap numarası (opsiyonel)
     * @return Transfer sayfası
     */
    @GetMapping("/transfer")
    public String showTransferPage(Model model, Principal principal, @RequestParam(value = "accountNumber", required = false) String toAccountNumber) {
        User user = userService.findByTcNo(principal.getName());
        Account account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);
        model.addAttribute("account", account);
        model.addAttribute("user", user);
        var lastTransactions = transactionRepository.findTop3ByFromAccountOrToAccountOrderByTransactionDateDesc(account, account);
        model.addAttribute("lastTransactions", lastTransactions);
        if (toAccountNumber != null) {
            model.addAttribute("accountNumber", toAccountNumber);
        }
        return "transfer";
    }

    /**
     * Para transferi işlemini gerçekleştirir
     * @param toAccount Alıcı hesap numarası
     * @param amount Transfer miktarı
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @param redirectAttributes Yönlendirme sonrası gösterilecek mesajlar
     * @return Transfer sayfasına yönlendirme
     */
    @PostMapping("/transfer")
    public String transfer(
            @RequestParam("toAccount") String toAccount,
            @RequestParam("amount") BigDecimal amount,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        User sender = userService.findByTcNo(principal.getName());
        Account senderAccount = accountRepository.findByUserId(sender.getId()).stream().findFirst().orElse(null);

        String result = transferService.transfer(senderAccount, toAccount, amount);
        if (result.equals("success")) {
            redirectAttributes.addFlashAttribute("success", "Transfer başarılı!");
        } else {
            redirectAttributes.addFlashAttribute("error", result);
        }
        return "redirect:/transfer";
    }

    /**
     * Hesap numarasına göre kullanıcı adını getirir
     * @param accountNumber Hesap numarası
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return Hesap sahibinin adı
     */
    @GetMapping("/api/account/{accountNumber}/user")
    @ResponseBody
    public String getUserNameByAccountNumber(@PathVariable String accountNumber, Principal principal) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            return "Hesap bulunamadı";
        }
        // Kendi hesabıysa isim gösterme
        User user = userService.findByTcNo(principal.getName());
        if (account.getUser().getId().equals(user.getId())) {
            return "Kendi Hesabınız";
        }
        return account.getUser().getFirstName() + " " + account.getUser().getLastName();
    }
} 