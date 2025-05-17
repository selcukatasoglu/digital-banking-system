package com.bankapp.digitalbankingsystem.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Döviz kurları ve altın fiyatlarını tutan model sınıfı.
 * Tüm para birimleri ve değerli madenler için alış, satış ve değişim bilgilerini içerir.
 * API'den gelen verileri işlemek için kullanılır.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRates {
    private String usd;
    private String eur;
    private String gbp;
    private String btc;
    private String updateDate;
    
    // USD detayları
    private String usdBuy;
    private String usdSell;
    private String usdChange;
    
    // EUR detayları
    private String eurBuy;
    private String eurSell;
    private String eurChange;
    
    // GBP detayları
    private String gbpBuy;
    private String gbpSell;
    private String gbpChange;

    // CHF detayları
    private String chfBuy;
    private String chfSell;
    private String chfChange;

    // CAD detayları
    private String cadBuy;
    private String cadSell;
    private String cadChange;

    // RUB detayları
    private String rubBuy;
    private String rubSell;
    private String rubChange;

    // AED detayları
    private String aedBuy;
    private String aedSell;
    private String aedChange;

    // AUD detayları
    private String audBuy;
    private String audSell;
    private String audChange;

    // Altın ve Değerli Madenler
    private String gramAltinBuy;
    private String gramAltinSell;
    private String gramAltinChange;
    
    private String cumhuriyetAltiniBuy;
    private String cumhuriyetAltiniSell;
    private String cumhuriyetAltiniChange;
    
    private String gumusBuy;
    private String gumusSell;
    private String gumusChange;
    
    private String gramPlatinBuy;
    private String gramPlatinSell;
    private String gramPlatinChange;
    
    private String gramPaladyumBuy;
    private String gramPaladyumSell;
    private String gramPaladyumChange;
} 