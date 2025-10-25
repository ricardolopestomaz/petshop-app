package com.petshop.api.service.usuario;

import com.petshop.api.model.usuario.UsuarioModel;
import com.petshop.api.repository.usuario.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder senhaCrip;
    private static final String SENHA_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$";


    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder senhaCrip){
        this.usuarioRepository = usuarioRepository;
        this.senhaCrip = senhaCrip;
    }



    // SALVAR
    public UsuarioModel salva(UsuarioModel usuario){
        if (!validarSenha(usuario.getSenha())) {
            // Tratar isso no Controller mais tarde!
            throw new IllegalArgumentException("A senha não atende aos requisitos de segurança.");
        }

        // Criptografa a senha
        String senhaCriptografada = senhaCrip.encode(usuario.getSenha());

        // Salva o usuario com a senha já criptografada
        usuario.setSenha((senhaCriptografada));

        return usuarioRepository.save(usuario);
    }

    // ATUALIZAR PARCIAL (PATCH)
    /**
     * Atualiza parcialmente um usuário.
     * Apenas os campos não-nulos dos dadosAtualizados serão aplicados.
     * @param id O ID do usuário a ser atualizado.
     * @param dadosAtualizados Um objeto UsuarioModel contendo os novos dados.
     * @return O usuário atualizado e salvo.
     */
    public UsuarioModel atualizarParcial(Long id, UsuarioModel dadosAtualizados) {

        // 1. Busca o usuário existente no banco de dados
        UsuarioModel usuarioDoBanco = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + id));

        // 2. Atualiza o Email (se foi fornecido)
        if (dadosAtualizados.getEmail() != null && !dadosAtualizados.getEmail().isEmpty()) {
            String novoEmail = dadosAtualizados.getEmail();

            // Verifica se o email novo é diferente do antigo E se já existe no banco
            if (!novoEmail.equals(usuarioDoBanco.getEmail()) && emailExiste(novoEmail)) {
                throw new IllegalArgumentException("O email fornecido já está em uso por outra conta.");
            }
            usuarioDoBanco.setEmail(novoEmail);
        }

        // 3. Atualiza a Senha (se foi fornecida)
        if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
            String novaSenha = dadosAtualizados.getSenha();

            // Valida a senha nova
            if (!validarSenha(novaSenha)) {
                throw new IllegalArgumentException("A nova senha não atende aos requisitos de segurança.");
            }

            // Criptografa a senha nova
            usuarioDoBanco.setSenha(senhaCrip.encode(novaSenha));
        }

        // 4. Atualiza outros campos (Ex: Nome)
        if (dadosAtualizados.getNome() != null && !dadosAtualizados.getNome().isEmpty()) {
            usuarioDoBanco.setNome(dadosAtualizados.getNome());
        }

        // (Adicione aqui outros campos que podem ser atualizados, como 'telefone', 'cpf', etc.)
        // if (dadosAtualizados.getTelefone() != null && !dadosAtualizados.getTelefone().isEmpty()) {
        //     usuarioDoBanco.setTelefone(dadosAtualizados.getTelefone());
        // }


        // 5. Salva o usuário com os campos atualizados
        return usuarioRepository.save(usuarioDoBanco);
    }


    // DELETAR
    public void deletar(Long id){
        usuarioRepository.deleteById(id);
    }

    // LISTA TODOS OS USUARIOS
    public List<UsuarioModel> buscarUsuariosTodos(){
        return usuarioRepository.findAll();
    }

    // BUSCA USUARIO POR ID
    public Optional<UsuarioModel> buscarUsuarioId(Long id){
        return usuarioRepository.findById(id);
    }

    // BUSCA USUARIO POR NOME
    public List<UsuarioModel> buscarUsuarioNome(String nome){
        return usuarioRepository.findByNomeContaining(nome);
    }

    // VERIFICAR SE O EMAIL JA EXISTE
    public boolean emailExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // VALIDAR SENHA
    /**
     * Valida uma senha conforme as regras de negócio.
     * Mínimo 8 caracteres, 1 minúscula, 1 maiúscula, 1 especial, 1 número.
     * @param senha A senha para validar.
     * @return true se a senha for válida, false caso contrário.
     */
    public boolean validarSenha(String senha){
        if (senha == null){
            return false;
        }
        return senha.matches(SENHA_REGEX);
    }

    // MÉTODO VERIFICAR LOGIN
    /**
     * Verifica se o email e senha correspondem a um usuário.
     * @param email O email do usuário.
     * @param senhaPura A senha em texto puro digitada no login
     * @return true se o login for válido, false caso contrário.
     */

    public boolean verificarLogin(String email, String senhaPura){
        // Busca usuario pelo email
        Optional<UsuarioModel> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isEmpty()){
            return false;
        }

        UsuarioModel usuario = usuarioOpt.get();

        String senhaDoBanco = usuario.getSenha();
        return senhaCrip.matches(senhaPura, senhaDoBanco);
    }
}