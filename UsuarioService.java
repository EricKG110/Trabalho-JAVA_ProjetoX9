import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class UsuarioService {

    private static final String ARQUIVO = "usuarios.txt";

    public String salvarUsuario(Usuario usuario) throws IOException {
        if (!validarUsuario(usuario)) {
            return "Cadastro inválido: Verifique o nome, e-mail e senha e tente novamente.";
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO, true))) {
            writer.write(usuario.toString());
            writer.newLine();
            return "Usuário cadastrado com sucesso!";
        }
    }

    public List<Usuario> listarUsuarios() throws IOException {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                usuarios.add(Usuario.fromString(linha));
            }
        }
        return usuarios;
    }

    private boolean validarUsuario(Usuario usuario) {
        return validarNome(usuario.getNome()) &&
               validarEmail(usuario.getEmail()) &&
               validarSenha(usuario.getSenha());
    }

    private boolean validarNome(String nome) {
        return nome != null && nome.matches("[A-Za-zÀ-ÿ ]{2,}");
    }

    private boolean validarEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-z]{2,}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    private boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 8 && senha.matches(".*[A-Za-z].*") && senha.matches(".*\\d.*");
    }
}
