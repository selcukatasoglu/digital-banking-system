package com.bankapp.digitalbankingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Hesap bilgilerini tutan entity sınıfı.
 * Her kullanıcının bir veya birden fazla hesabı olabilir.
 * Hesap numarası, bakiye ve oluşturulma tarihi bilgilerini içerir.
 */
@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_number", unique = true, nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Hesap oluşturulduğunda otomatik olarak çalışan metod.
     * Oluşturulma tarihini ve varsayılan bakiyeyi ayarlar.
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (balance == null) {
            balance = BigDecimal.ZERO;
        }
    }
} 