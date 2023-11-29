package com.example.demo.service;

import com.example.demo.config.PasswordEncoderConfiguration;
import com.example.demo.dto.request.RegistrationUserDto;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @PersistenceContext
    private EntityManager em;
    private final UserRepository userRepository;
    private final PasswordEncoderConfiguration passwordEncoder;
    private final RoleService roleService;

    public Optional<User> findByUserName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден"));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority
                        (role.getName())).collect(Collectors.toList())
        );
    }

    public User createUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setName(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.passwordEncoder().encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        return userRepository.save(user);

    }
}
