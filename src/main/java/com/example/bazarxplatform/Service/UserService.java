package com.example.bazarxplatform.Service;

import com.example.bazarxplatform.Dto.Request.LoginRequest;
import com.example.bazarxplatform.Dto.Request.RegistrRequest;
import com.example.bazarxplatform.Dto.Response.UserResponse;
import com.example.bazarxplatform.Entity.User;
import com.example.bazarxplatform.Exception.ResourceNotFoundException;
import com.example.bazarxplatform.Mapper.UserMapper;
import com.example.bazarxplatform.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    // Register
    @Transactional
    public UserResponse register(RegistrRequest request) {
        // Check if username exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Bu istifadəçi adı artıq mövcuddur");
        }

        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Bu email artıq qeydiyyatdadır");
        }

        // Create user
        User user = userMapper.toEntity(request);
        // TODO: Encrypt password (BCryptPasswordEncoder)

        User savedUser = userRepository.save(user);
        return userMapper.toResponse(savedUser);
    }

    // Login
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));

        // TODO: Verify password with BCrypt
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Şifrə səhvdir");
        }

        return userMapper.toResponse(user);
    }

    // Get user by ID
    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));
        return userMapper.toResponse(user);
    }

    // Get user entity (for internal use)
    public User getUserEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("İstifadəçi tapılmadı"));
    }

    // Update balance
    @Transactional
    public void updateBalance(Long userId, BigDecimal amount) {
        User user = getUserEntityById(userId);
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);
    }

    // Check if user has sufficient balance
    public boolean hasSufficientBalance(Long userId, BigDecimal amount) {
        User user = getUserEntityById(userId);
        return user.getBalance().compareTo(amount) >= 0;
    }
}