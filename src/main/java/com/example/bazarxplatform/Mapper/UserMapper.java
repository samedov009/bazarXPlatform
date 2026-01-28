package com.example.bazarxplatform.Mapper;

import com.example.bazarxplatform.Dto.Request.RegistrRequest;
import com.example.bazarxplatform.Dto.Response.UserResponse;
import com.example.bazarxplatform.Entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // Entity -> Response DTO
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setBalance(user.getBalance());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    // Request DTO -> Entity (for registration)
    public User toEntity(RegistrRequest request) {
        if (request == null) {
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword()); // Should be encrypted in service
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        return user;
    }
}
