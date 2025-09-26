package Utilitario;
import Modelo.Endereco;
import Modelo.Usuario;
import Servico.GerenciadorDeLogin;
import static Utilitario.ValidarEndereco.*;
import java.util.Scanner;

public class Utilitario {
    public static String receberNome() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o nome do usuário: ");
            String nome = entrada.nextLine().trim();

            if (nome.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (nome.trim().length() >= 4) {
                    return nome;
            } else {
                System.out.println("Erro: O nome é inválido! O nome deve ter pelo menos 4 caracteres.");
            }
        }
    }

    public static boolean validarTexto(String nome) {
        boolean oNomeEhValido = true;
        for (int i = 0; i < nome.length(); i++) {
            char letra = nome.charAt(i);
            if (letra != ' ' && !Character.isLetter(letra)) {
                return oNomeEhValido = false;
            }
        }
        return oNomeEhValido;
    }

    public static String receberSenha() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite a senha do usuário: ");
            String senha = entrada.nextLine().trim();

            if (senha.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }

            if (senha.length() >= 3) {
                return senha;
            } else {
                System.out.println("Erro: Digite uma senha válida com ao menos 3 caracteres.");
            }
        }
    }

    public static String receberTipoUsuario() {
        Scanner entrada = new Scanner(System.in);
        while (true) {
            System.out.print("Digite o tipo do usuário (Cliente ou Administrador): ");
            String opcao = entrada.nextLine().trim();

            if (opcao.toLowerCase().equals("sair")) {
                System.out.println("Você saiu!");
                entrada.close();
                System.exit(0);
            }
            if (opcao.length() >= 1) {
                if (validarTexto(opcao) && (opcao.equals("Cliente") || opcao.equals("Administrador") )) {
                    return opcao;
                } else {
                    System.out.println("Erro: Digite uma das duas opções corretamente, siga o mesmo formato de escrita.");
                }
            } else {
                System.out.println("Erro: A opção digitada é inválida! Digite 'Cliente' ou 'Administrador'.");
            }
        }
    }

    public static void receberUsuarioCompleto() {
        System.out.println("Bem vindo ao sistema! Para sair digite 'sair'");
        System.out.println("\n=== DADOS DO USUÁRIO ===");
        String nome = receberNome();
        String senha = receberSenha();
        String tipo = receberTipoUsuario();

        System.out.println("\n=== CADASTRO DE ENDEREÇO ===");
        String rua = receberRua();
        String numero = receberNumero();
        String bairro = receberBairro();
        String cidade = receberCidade();
        String estado = receberEstado();
        String cep = receberCEP();
        String complemento = receberComplemento();
        Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep, complemento);

        Usuario usuario = new Usuario(nome, senha, tipo, endereco);
        GerenciadorDeLogin.registrarUsuario(usuario);
    }

}
