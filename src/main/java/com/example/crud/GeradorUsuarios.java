import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

public class GeradorUsuarios {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        for (int i = 1; i <= 50; i++) {
            String id = UUID.randomUUID().toString();
            String nome = "Usuario" + i;
            String email = "usuario" + i + "@teste.com";
            String senhaCriptografada = encoder.encode("12345");

            System.out.println(String.format(
                    "INSERT INTO usuario (id, nome, email, senha, cep, logradouro, bairro, cidade, uf, active, aceitou_termos, role) " +
                            "VALUES ('%s', '%s', '%s', '%s', '10000%02d', 'Rua Exemplo %d', 'Bairro %d', 'Cidade %d', 'SP', true, true, 'USER');",
                    id, nome, email, senhaCriptografada, i, i, i, i
            ));
        }
    }
}