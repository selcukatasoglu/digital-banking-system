package com.bankapp.digitalbankingsystem.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bankapp.digitalbankingsystem.model.ExchangeRates;

/**
 * Döviz kurları ve altın fiyatlarını yöneten servis sınıfı.
 * Harici API'lerden güncel kurları çeker ve işler.
 * Hata durumunda son başarılı sonuçları kullanır.
 */
@Service
public class ExchangeRateService {

    private final RestTemplate restTemplate = new RestTemplate();
    private ExchangeRates lastSuccessfulRates;

    /**
     * Güncel döviz kurlarını ve altın fiyatlarını getirir
     * @return Güncel kurlar ve fiyatlar
     */
    public ExchangeRates getExchangeRates() {
        try {
            String response = restTemplate.getForObject("https://finans.truncgil.com/today.json", String.class);
            JSONObject json = new JSONObject(response);
            
            String updateDate = json.getString("Update_Date");
            
            // USD detayları
            JSONObject usdData = json.getJSONObject("USD");
            String usdBuy = usdData.getString("Alış");
            String usdSell = usdData.getString("Satış");
            String usdChange = usdData.getString("Değişim");
            
            // EUR detayları
            JSONObject eurData = json.getJSONObject("EUR");
            String eurBuy = eurData.getString("Alış");
            String eurSell = eurData.getString("Satış");
            String eurChange = eurData.getString("Değişim");
            
            // GBP detayları
            JSONObject gbpData = json.getJSONObject("GBP");
            String gbpBuy = gbpData.getString("Alış");
            String gbpSell = gbpData.getString("Satış");
            String gbpChange = gbpData.getString("Değişim");

            // CHF detayları
            JSONObject chfData = json.getJSONObject("CHF");
            String chfBuy = chfData.getString("Alış");
            String chfSell = chfData.getString("Satış");
            String chfChange = chfData.getString("Değişim");

            // CAD detayları
            JSONObject cadData = json.getJSONObject("CAD");
            String cadBuy = cadData.getString("Alış");
            String cadSell = cadData.getString("Satış");
            String cadChange = cadData.getString("Değişim");

            // RUB detayları
            JSONObject rubData = json.getJSONObject("RUB");
            String rubBuy = rubData.getString("Alış");
            String rubSell = rubData.getString("Satış");
            String rubChange = rubData.getString("Değişim");

            // AED detayları
            JSONObject aedData = json.getJSONObject("AED");
            String aedBuy = aedData.getString("Alış");
            String aedSell = aedData.getString("Satış");
            String aedChange = aedData.getString("Değişim");

            // AUD detayları
            JSONObject audData = json.getJSONObject("AUD");
            String audBuy = audData.getString("Alış");
            String audSell = audData.getString("Satış");
            String audChange = audData.getString("Değişim");

            // Gram Altın detayları
            JSONObject gramAltinData = json.getJSONObject("gram-altin");
            String gramAltinBuy = gramAltinData.getString("Alış");
            String gramAltinSell = gramAltinData.getString("Satış");
            String gramAltinChange = gramAltinData.getString("Değişim");

            // Cumhuriyet Altını detayları
            JSONObject cumhuriyetAltiniData = json.getJSONObject("cumhuriyet-altini");
            String cumhuriyetAltiniBuy = cumhuriyetAltiniData.getString("Alış");
            String cumhuriyetAltiniSell = cumhuriyetAltiniData.getString("Satış");
            String cumhuriyetAltiniChange = cumhuriyetAltiniData.getString("Değişim");

            // Gümüş detayları
            JSONObject gumusData = json.getJSONObject("gumus");
            String gumusBuy = gumusData.getString("Alış");
            String gumusSell = gumusData.getString("Satış");
            String gumusChange = gumusData.getString("Değişim");

            // Gram Platin detayları
            JSONObject gramPlatinData = json.getJSONObject("gram-platin");
            String gramPlatinBuy = gramPlatinData.getString("Alış");
            String gramPlatinSell = gramPlatinData.getString("Satış");
            String gramPlatinChange = gramPlatinData.getString("Değişim");

            // Gram Paladyum detayları
            JSONObject gramPaladyumData = json.getJSONObject("gram-paladyum");
            String gramPaladyumBuy = gramPaladyumData.getString("Alış");
            String gramPaladyumSell = gramPaladyumData.getString("Satış");
            String gramPaladyumChange = gramPaladyumData.getString("Değişim");
            
            double btc = fetchBTC();

            if (btc > 0) {
                lastSuccessfulRates = new ExchangeRates(
                    usdSell,
                    eurSell,
                    gbpSell,
                    String.format("%.2f", btc),
                    updateDate,
                    usdBuy,
                    usdSell,
                    usdChange,
                    eurBuy,
                    eurSell,
                    eurChange,
                    gbpBuy,
                    gbpSell,
                    gbpChange,
                    chfBuy,
                    chfSell,
                    chfChange,
                    cadBuy,
                    cadSell,
                    cadChange,
                    rubBuy,
                    rubSell,
                    rubChange,
                    aedBuy,
                    aedSell,
                    aedChange,
                    audBuy,
                    audSell,
                    audChange,
                    gramAltinBuy,
                    gramAltinSell,
                    gramAltinChange,
                    cumhuriyetAltiniBuy,
                    cumhuriyetAltiniSell,
                    cumhuriyetAltiniChange,
                    gumusBuy,
                    gumusSell,
                    gumusChange,
                    gramPlatinBuy,
                    gramPlatinSell,
                    gramPlatinChange,
                    gramPaladyumBuy,
                    gramPaladyumSell,
                    gramPaladyumChange
                );
                return lastSuccessfulRates;
            }
        } catch (Exception e) {
            // Hata durumunda son başarılı sonucu döndür
        }

        return lastSuccessfulRates != null ? lastSuccessfulRates :
            new ExchangeRates(
                "0.00", // usd
                "0.00", // eur
                "0.00", // gbp
                "0.00", // btc
                "Veri alınamadı", // updateDate
                "0.00", // usdBuy
                "0.00", // usdSell
                "%0.00", // usdChange
                "0.00", // eurBuy
                "0.00", // eurSell
                "%0.00", // eurChange
                "0.00", // gbpBuy
                "0.00", // gbpSell
                "%0.00", // gbpChange
                "0.00", // chfBuy
                "0.00", // chfSell
                "%0.00", // chfChange
                "0.00", // cadBuy
                "0.00", // cadSell
                "%0.00", // cadChange
                "0.00", // rubBuy
                "0.00", // rubSell
                "%0.00", // rubChange
                "0.00", // aedBuy
                "0.00", // aedSell
                "%0.00", // aedChange
                "0.00", // audBuy
                "0.00", // audSell
                "%0.00", // audChange
                "0.00", // gramAltinBuy
                "0.00", // gramAltinSell
                "%0.00", // gramAltinChange
                "0.00", // cumhuriyetAltiniBuy
                "0.00", // cumhuriyetAltiniSell
                "%0.00", // cumhuriyetAltiniChange
                "0.00", // gumusBuy
                "0.00", // gumusSell
                "%0.00", // gumusChange
                "0.00", // gramPlatinBuy
                "0.00", // gramPlatinSell
                "%0.00", // gramPlatinChange
                "0.00", // gramPaladyumBuy
                "0.00", // gramPaladyumSell
                "%0.00"  // gramPaladyumChange
            );
    }

    /**
     * Bitcoin fiyatını Coinbase API'sinden çeker
     * @return Bitcoin'in USD cinsinden değeri
     */
    private double fetchBTC() {
        try {
            String response = restTemplate.getForObject("https://api.coinbase.com/v2/exchange-rates?currency=BTC", String.class);
            JSONObject json = new JSONObject(response);
            JSONObject rates = json.getJSONObject("data").getJSONObject("rates");
            return Double.parseDouble(rates.getString("USD"));
        } catch (Exception e) {
            return 0;
        }
    }
} 