package com.erpmarcenaria.SistemaGestao.service.impl;

import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoCadastro;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoLogin;
import com.erpmarcenaria.SistemaGestao.dto.UserDTO;
import com.erpmarcenaria.SistemaGestao.entity.User;
import com.erpmarcenaria.SistemaGestao.enums.UserRole;
import com.erpmarcenaria.SistemaGestao.exceptions.InvalidCredentialsException;
import com.erpmarcenaria.SistemaGestao.exceptions.NotFoundException;
import com.erpmarcenaria.SistemaGestao.repository.UserRepository;
import com.erpmarcenaria.SistemaGestao.security.JwtUtils;
import com.erpmarcenaria.SistemaGestao.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtUtils jwtUtils;

    @Override
    public Response registerUser(SolicitacaoCadastro solicitacaoCadastro) {

        UserRole role = UserRole.GERENTE;

        if (solicitacaoCadastro.getRole() != null) {
            role=solicitacaoCadastro.getRole();
        }

        User userToSave = User.builder()
                .nome(solicitacaoCadastro.getNome())
                .email(solicitacaoCadastro.getEmail())
                .senha(passwordEncoder.encode(solicitacaoCadastro.getSenha()))
                .numeroTelefone(solicitacaoCadastro.getNumeroTelefone())
                .role(role)
                .build();

        userRepository.save(userToSave);

        return Response.builder()
                .status(200)
                .message("usuário criado com sucesso")
                .build();
    }

    @Override
    public Response loginUser(SolicitacaoLogin solicitacaoLogin) {
        User user = userRepository.findByEmail(solicitacaoLogin.getEmail())
                .orElseThrow(()-> new NotFoundException("Email não encontrado"));

        if (!passwordEncoder.matches(solicitacaoLogin.getSenha(), user.getSenha())) {
            throw new InvalidCredentialsException("senha não coincide");
        }
        String token = jwtUtils.generateToken(user.getEmail());

        return Response.builder()
                .status(200)
                .message("Usuário logado com sucesso")
                .role(user.getRole())
                .token(token)
                .expirationTime("6 meses")
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<User> users = userRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        List<UserDTO> userDTOS = modelMapper.map(users, new TypeToken<List<UserDTO>>() {}.getType());

        userDTOS.forEach(userDTO -> userDTO.setOrcamentos(null));

        return Response.builder()
                .status(200)
                .message("sucesso")
                .users(userDTOS)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        user.setOrcamentos(null);

        return user;
    }

    @Override
    public Response updateUser(Long id, UserDTO userDTO) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        if (userDTO.getEmail() != null) existingUser.setEmail(userDTO.getEmail());
        if (userDTO.getNome() != null) existingUser.setNome(userDTO.getNome());
        if (userDTO.getNumeroTelefone() != null) existingUser.setNumeroTelefone(userDTO.getNumeroTelefone());
        if (userDTO.getRole() != null) existingUser.setRole(userDTO.getRole());

        if (userDTO.getSenha() != null && !userDTO.getSenha().isEmpty()) {
            existingUser.setNumeroTelefone(passwordEncoder.encode(userDTO.getSenha()));
        }

        userRepository.save(existingUser);

        return Response.builder()
                .status(200)
                .message("Usuário atualizado com sucesso")
                .build();
    }

    @Override
    public Response deleteUser(Long id) {

        userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Uuário não encontrado"));
        userRepository.deleteById(id);

        return Response.builder()
                .status(200)
                .message("Usuário deletado com sucesso")
                .build();
    }

    @Override
    public Response getUserAndOrcamentos(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        userDTO.getOrcamentos().forEach(orcamentoDTO -> {
            orcamentoDTO.setUser(null);
            orcamentoDTO.setFornecedor(null);
        });

        return Response.builder()
                .status(200)
                .message("sucesso")
                .user(userDTO)
                .build();
    }
}
