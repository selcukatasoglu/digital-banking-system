package com.bankapp.digitalbankingsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bankapp.digitalbankingsystem.model.User;
import com.bankapp.digitalbankingsystem.repository.UserRepository;

import java.util.Collections;

/**
 * Spring Security için özel kullanıcı detay servisi.
 * TC Kimlik numarası ile kullanıcı doğrulama işlemlerini gerçekleştirir.
 * Kullanıcı rollerini ve yetkilerini yönetir.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * TC Kimlik numarasına göre kullanıcı bilgilerini yükler
     * @param tcNo Kullanıcının TC Kimlik numarası
     * @return Spring Security UserDetails nesnesi
     * @throws UsernameNotFoundException Kullanıcı bulunamazsa fırlatılır
     */
    @Override
    public UserDetails loadUserByUsername(String tcNo) throws UsernameNotFoundException {
        User user = userRepository.findByTcNo(tcNo)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + tcNo));

        return new org.springframework.security.core.userdetails.User(
                user.getTcNo(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
} 