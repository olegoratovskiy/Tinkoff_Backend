package com.example.demo.service;

import com.example.demo.config.PasswordEncoderConfiguration;
import com.example.demo.dto.request.RegistrationUserDto;
import com.example.demo.dto.request.UserChangeNameDto;
import com.example.demo.dto.request.UserGenderDto;
import com.example.demo.dto.response.UserAccountResponseDto;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.exceptions.handlers.UserNotFoundError;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoderConfiguration passwordEncoder;
    private final RoleService roleService;

    public Optional<User> findByUserName(String name) {
        return userRepository.findByName(name);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Transactional
    public void deleteUser(Long id) {
        var user = findById(id);
        user.setBanned(true);
        userRepository.save(user);
    }

    public boolean checkForBan(String username) {
        var user = findByUserName(username).get();
        return user.isBanned();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден"));
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }

    @Transactional
    public void unbanUser(Long id) {
        var user = findById(id);
        user.setBanned(false);
        userRepository.save(user);
    }

    @Transactional
    public void setModer(Long id) {
        var user = findById(id);
        user.setRole("MODER");
        user.setRoles(new ArrayList<>(List.of(roleService.getModerRole())));
        userRepository.save(user);
    }

    public User createUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setName(registrationUserDto.getUsername());
        user.setPassword(passwordEncoder.passwordEncoder().encode(registrationUserDto.getPassword()));
        user.setRoles(List.of(roleService.getUserRole()));
        user.setGender(registrationUserDto.getGender());
        user.setRole("ADMIN");
        user.setEmail(registrationUserDto.getEmail());
        return userRepository.save(user);

    }

    public User addGender(UserGenderDto userGenderDto) {
        var person = findById(userGenderDto.getUserId());
        person.setGender(userGenderDto.getGender());
        return userRepository.save(person);
    }

    public User changeName(UserChangeNameDto userChangeNameDto) {
        var person = findById(userChangeNameDto.getUserId());
        person.setName(userChangeNameDto.getName());
        return userRepository.save(person);
    }

    public UserAccountResponseDto getUserAccountById(long id) {
        var user = findById(id);
        UserAccountResponseDto res = new UserAccountResponseDto();
        res.setId(user.getId());
        res.setName(user.getName());
        res.setRole(user.getRole());
        res.setGender(user.getGender());
        res.setEmail(user.getEmail());

        return res;
    }

    public Long getUserByName(String username) throws UserNotFoundError {
        var user = userRepository.findByName(username).orElseThrow(
                () -> new UserNotFoundError("No user with username: " + username)
        );
        return user.getId();
    }
}
