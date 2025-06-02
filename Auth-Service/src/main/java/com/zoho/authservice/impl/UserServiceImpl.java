package com.zoho.authservice.impl;

import com.zoho.authservice.dto.request.PromoteRequest;
import com.zoho.authservice.dto.request.UserRegister;
import com.zoho.authservice.enums.Role;
import com.zoho.authservice.model.Users;
import com.zoho.authservice.repository.UserRepository;
import com.zoho.authservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.zoho.authservice.constrain.GlobalConstrain.USER_ALREADY_REGISTER;
import static com.zoho.authservice.constrain.GlobalConstrain.USER_REGISTER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    public ResponseEntity<?> regUser(UserRegister userReq) {

        Optional<Users> existingUser = userRepo.findByName(userReq.getName());

        if(existingUser.isEmpty()){
            String encodePass = passwordEncoder().encode(userReq.getPassword());

            Users user = Users.builder()
                    .name(userReq.getName())
                    .role(Role.PENDING)
                    .email(userReq.getEmail())
                    .organizationId(userReq.getOrganizationId())
                    .password(encodePass)
                    .build();

            userRepo.save(user);
            return new ResponseEntity<>(USER_REGISTER,HttpStatus.OK);
        }
        return new ResponseEntity<>(USER_ALREADY_REGISTER,HttpStatus.CONFLICT);
    }

    @Override
    public ResponseEntity<?> promoteUserRole(PromoteRequest promoteRequest) {

        Optional<Users> userFoundOptional = userRepo.findById(promoteRequest.getUserId());

        if (userFoundOptional.isPresent()) {
            Users userToUpdate = userFoundOptional.get();
            userToUpdate.setRole(promoteRequest.getNewRole());

            userRepo.save(userToUpdate);

            return new ResponseEntity<>("User role updated successfully.", HttpStatus.OK);

        } else {
            return new ResponseEntity<>("User not found with ID: " + promoteRequest.getUserId(), HttpStatus.NOT_FOUND);
        }
    }

    // why we need this validation?
    @Override
    public ResponseEntity<?> validateUserOrganization(UUID userId, UUID orgId) {
        Optional<Users> userOptional = userRepo.findById(userId);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        Users user = userOptional.get();
        if (!user.getOrganizationId().equals(orgId)) {
            return new ResponseEntity<>("Access denied. User does not belong to this organization.", HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>("Access granted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> fetchUser(UUID userId) {
        return new ResponseEntity<>(userRepo.findById(userId),HttpStatus.OK);
    }

}
