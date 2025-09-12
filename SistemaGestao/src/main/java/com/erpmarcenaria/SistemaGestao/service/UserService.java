package com.erpmarcenaria.SistemaGestao.service;

import com.erpmarcenaria.SistemaGestao.dto.Response;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoCadastro;
import com.erpmarcenaria.SistemaGestao.dto.SolicitacaoLogin;
import com.erpmarcenaria.SistemaGestao.dto.UserDTO;
import com.erpmarcenaria.SistemaGestao.entity.User;

public interface UserService {
    Response registerUser(SolicitacaoCadastro solicitacaoCadastro);
    Response loginUser(SolicitacaoLogin solicitacaoLogin);
    Response getAllUsers();
    User getCurrentLoggedInUser();
    Response updateUser(Long id, UserDTO userDTO);
    Response deleteUser(Long id);
    Response getUserAndOrcamentos(Long id);
}
