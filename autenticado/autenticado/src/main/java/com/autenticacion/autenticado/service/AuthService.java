
package com.autenticacion.autenticado.service;

import com.autenticacion.autenticado.model.Usuario;


public interface AuthService {
    Usuario autenticar(String nickname, String password);
    Usuario buscarPorNickname(String nickname);
    

}
