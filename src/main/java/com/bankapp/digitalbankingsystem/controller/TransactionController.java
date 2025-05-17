package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bankapp.digitalbankingsystem.model.Account;
import com.bankapp.digitalbankingsystem.model.Transaction;
import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.AccountRepository;
import com.bankapp.digitalbankingsystem.repository.TransactionRepository;
import com.bankapp.digitalbankingsystem.service.UserService;

import java.security.Principal;
import java.util.List;

/**
 * İşlem geçmişini yöneten controller sınıfı.
 * Kullanıcının hesabındaki tüm para transferi, yatırma ve çekme işlemlerini listeler.
 * İşlem detaylarını tarih sırasına göre gösterir.
 */
@Controller
public class TransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * İşlem geçmişi sayfasını gösterir
     * @param model View'a aktarılacak veriler
     * @param principal Giriş yapmış kullanıcı bilgisi
     * @return İşlem geçmişi sayfası
     */
    @GetMapping("/transactions")
    public String showTransactions(Model model, Principal principal) {
        User user = userService.findByTcNo(principal.getName());
        Account account = accountRepository.findByUserId(user.getId()).stream().findFirst().orElse(null);
        List<Transaction> transactions = transactionRepository.findByFromAccountOrToAccountOrderByTransactionDateDesc(account, account);
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
} 