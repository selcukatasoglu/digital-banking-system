package com.bankapp.digitalbankingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Para transferi işlemlerini tutan entity sınıfı.
 * Her işlem için gönderen ve alıcı hesap, miktar, işlem ücreti ve işlem tipi bilgilerini içerir.
 * İşlemlerin tarihçesini tutmak için kullanılır.
 */
@Data
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column
    private BigDecimal transactionFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(nullable = false)
    private LocalDateTime transactionDate;
} 