package com.bankapp.digitalbankingsystem.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Kullanıcı bilgilerini tutan entity sınıfı.
 * TC Kimlik numarası, ad, soyad, e-posta, telefon ve şifre bilgilerini içerir.
 * Tüm alanlar için validasyon kuralları tanımlanmıştır.
 */
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "TC Kimlik Numarası boş olamaz")
    @Size(min = 11, max = 11, message = "TC Kimlik Numarası 11 haneli olmalıdır")
    @Pattern(regexp = "^[0-9]*$", message = "TC Kimlik Numarası sadece rakamlardan oluşmalıdır")
    @Column(name = "tc_no", unique = true, nullable = false)
    private String tcNo;

    @NotBlank(message = "Ad boş olamaz")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Soyad boş olamaz")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "E-posta boş olamaz")
    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^[0-9\\-\\+\\s]*$", message = "Geçerli bir telefon numarası giriniz")
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Şifre boş olamaz")
    @Size(min = 6, message = "Şifre en az 6 karakter olmalıdır")
    @Column(nullable = false)
    private String password;
} 