package com.site.denisalibec.service;

import com.site.denisalibec.dto.RegisterRequest;
import com.site.denisalibec.dto.LoginRequest;
import com.site.denisalibec.dto.UserDTO;
import com.site.denisalibec.enums.Role;
import com.site.denisalibec.model.User;
import com.site.denisalibec.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ================== REGISTER ==================

    /**
     * Inregistreaza un utilizator nou.
     * Folosit de endpoint-ul /api/auth/register
     */
    public UserDTO registerUser(RegisterRequest request) {
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword()); // TODO: adauga criptare in viitor
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setPhone(request.getPhone());
        newUser.setRole(Role.CLIENT); // setare implicita

        User savedUser = userRepository.save(newUser);
        return convertToDTO(savedUser);
    }

    // ================== LOGIN ==================

    /**
     * Autentificare utilizator.
     * Folosit de endpoint-ul /api/auth/login
     */
    public Optional<UserDTO> loginUser(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername())
                .filter(user -> user.getPassword().equals(request.getPassword())) // TODO: compara hash in viitor
                .map(this::convertToDTO);
    }

    // ================== CHECKS ==================

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // ================== READ ==================

    // TODO: momentan nefolosit - poate fi folosit pentru dashboard admin
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(this::convertToDTO);
    }

    // ================== CONVERSIONS ==================

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole()
        );
    }

    public User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        // TODO: parola se seteaza din login sau register
        return user;
    }

    // ================== TODO ==================

    // TODO: de adaugat functii pentru editare profil, stergere cont, schimbare parola etc.
}

