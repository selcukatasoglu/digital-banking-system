package com.bankapp.digitalbankingsystem.model;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Kayıtlı alıcıları tutan entity sınıfı.
 * Kullanıcıların sık kullandıkları alıcıları kaydetmelerini sağlar.
 * Her kayıtlı alıcı bir kullanıcıya ait olmalıdır.
 */
@Data
@Entity
@Table(name = "saved_recipients")
public class SavedRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String recipientName;
} 