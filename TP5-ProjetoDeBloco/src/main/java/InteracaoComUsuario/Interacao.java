package InteracaoComUsuario;
import Servico.GerenciadorDeLogin;
import Utilitario.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import API.APIConsumir;

public class Interacao {
    private static String usuarioLogado = null;

    public static void menu() {
        Scanner entrada = new Scanner(System.in);
        APIConsumir api = new APIConsumir();

        while (true) {
            System.out.println("\n-- MENU PRINCIPAL --");
            System.out.println("1 - Manipulação Arquivo CSV (Registro e Login)");
            System.out.println("2 - Usar API REST");
            System.out.println("3 - Sair");
            System.out.print("Digite uma opção: ");
            String opcaoPrincipal = entrada.nextLine();

            try {
                switch (opcaoPrincipal) {
                    case "1":
                        menuCSV(entrada);
                        break;
                    case "2":
                        menuAPI(entrada, api);
                        break;
                    case "3":
                        usuarioLogado = null;
                        System.out.println("Sistema encerrado!");
                        entrada.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Erro: Digite uma opção válida. Escolha de 1 a 3");
                }
            } catch (IOException erro) {
                System.out.println("Erro: " + erro.getMessage());
            } catch (URISyntaxException erro) {
                System.out.println("Erro: " + erro.getMessage());
            }
        }
    }

    private static void menuCSV(Scanner entrada) throws IOException, URISyntaxException {
        while (true) {
            System.out.println("\n-- MANIPULAÇÃO ARQUIVO CSV --");
            System.out.println("1 - Realizar Cadastro");
            System.out.println("2 - Realizar Login");
            System.out.println("3 - Voltar ao Menu Principal");
            System.out.print("Digite uma opção: ");
            String opcao = entrada.nextLine();

            switch (opcao) {
                case "1":
                    Utilitario.receberUsuarioCompleto();
                    break;
                case "2":
                    System.out.println("Bem vindo ao sistema! Para sair digite 'sair'");
                    String nomeLogin = Utilitario.receberNome();
                    String senhaLogin = Utilitario.receberSenha();
                    String tipoLogin = Utilitario.receberTipoUsuario();

                    if (GerenciadorDeLogin.buscarUsuario(nomeLogin, senhaLogin, tipoLogin)) {
                        usuarioLogado = nomeLogin;
                    } else {
                        System.out.println("Login falhou!");
                    }
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Erro: Digite uma opção válida. Escolha de 1 a 3");
            }
        }
    }

    private static void menuAPI(Scanner entrada, APIConsumir api) throws IOException, URISyntaxException {
        while (true) {
            System.out.println("\n-- USAR API --");
            System.out.println("1 - Listar Usuários");
            System.out.println("2 - Buscar Usuário por Nome");
            System.out.println("3 - Inserir Novo Usuário");
            System.out.println("4 - Atualizar Usuário");
            System.out.println("5 - Excluir Usuário");
            System.out.println("6 - Ver Minha Senha");
            System.out.println("7 - Voltar ao Menu Principal");
            System.out.print("Digite uma opção: ");
            String opcao = entrada.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n=== Lista de Usuários ===");
                    System.out.println(api.getTodosUsuarios());
                    break;
                case "2":
                    System.out.print("\nBuscar - ");
                    String nomeBusca = Utilitario.receberNome();
                    System.out.println(api.getUsuarioPorNome(nomeBusca));
                    break;
                case "3":
                    System.out.println("\n=== INSERIR NOVO USUÁRIO VIA API ===");
                    try {
                        String usuarioJson = UtilitarioAPI.receberUsuarioCompletoJson();
                        String resultado = api.inserirUsuario(usuarioJson);
                        System.out.println("Resultado da inserção: " + resultado);
                    } catch (Exception erro) {
                        System.out.println("Erro ao cadastrar usuário: " + erro.getMessage());
                    }
                    break;
                case "4":
                    System.out.println(UtilitarioInteracao.atualizarUsuario(api, entrada));
                    break;
                case "5":
                    System.out.print("Buscar usuário para excluir completamente - ");
                    String nomeExcluir = Utilitario.receberNome();
                    System.out.println(api.deleteUsuarioPorNome(nomeExcluir));
                    break;
                case "6":
                    if (usuarioLogado == null) {
                        System.out.println("Nenhum usuário logado. Faça login primeiro.");
                        break;
                    }
                    String senha = api.getSenhaPorNome(usuarioLogado);
                    if (senha.contains("Erro") || senha.contains("não encontrado")) {
                        System.out.println("Erro: " + senha);
                    } else {
                        System.out.println("Sua senha é: " + senha);
                    }
                    break;
                case "7":
                    return;
                default:
                    System.out.println("Erro: Digite uma opção válida. Escolha de 1 a 7");
            }
        }
    }
}