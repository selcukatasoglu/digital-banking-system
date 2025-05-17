package com.bankapp.digitalbankingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bankapp.digitalbankingsystem.service.ExchangeRateService;

/**
 * Dashboard işlemlerini yöneten controller sınıfı.
 * Döviz kurları ve altın fiyatları gibi detaylı bilgileri gösterir.
 * Kullanıcıya finansal piyasalardaki güncel durumu sunar.
 */
@Controller
public class DashboardController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    /**
     * Döviz ve altın detayları sayfasını gösterir
     * @param model View'a aktarılacak veriler
     * @return Döviz detayları sayfası
     */
    @GetMapping("/exchange-details")
    public String showExchangeDetails(Model model) {
        model.addAttribute("exchangeRates", exchangeRateService.getExchangeRates());
        return "exchange-details";
    }
} 