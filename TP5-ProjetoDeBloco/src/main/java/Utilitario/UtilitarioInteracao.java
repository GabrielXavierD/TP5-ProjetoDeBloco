package Utilitario;
import API.APIConsumir;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class UtilitarioInteracao {
    public static String atualizarUsuario(APIConsumir api, Scanner entrada) throws IOException, URISyntaxException {
        System.out.print("Busque o usuário que deseja atualizar - ");
        String nomeAntigo = Utilitario.receberNome();
        String usuarioJson = api.getUsuarioPorNome(nomeAntigo);
        if (usuarioJson.contains("Usuário não encontrado")) {
            return "Usuário não encontrado.";
        }

        System.out.println("\n=== ATUALIZAR USUÁRIO ===\nDigite um valor ou deixe em branco (pressione enter) para manter o valor atual.");
        String nomeAtual = extrairCampoJson(usuarioJson, "nomeUsuario");
        String tipoAtual = extrairCampoJson(usuarioJson, "tipoUsuario");

        System.out.print("Atualizando Nome - O nome atual é '" + nomeAtual + "' - Digite o novo nome: ");
        String novoNome;
        do {
            novoNome = entrada.nextLine().trim();
            if (novoNome.isEmpty()) {
                break;
            }

            if (novoNome.length() < 4) {
                System.out.print("Erro: O nome deve ter pelo menos 4 caracteres. Digite novamente: ");
            }
        } while (novoNome.length() < 4);

        if (!novoNome.isEmpty() && nomeJaExiste(novoNome)) {
            return "Erro: Este nome de usuário já existe!";
        }

        if (!novoNome.isEmpty()) {
            nomeAtual = novoNome;
        }

        System.out.print("Atualizando Senha - Digite a nova senha: ");
        String novaSenha = entrada.nextLine().trim();
        String senhaParaJson = novaSenha.isEmpty() ? "MANTER_SENHA_ATUAL" : novaSenha;

        System.out.print("Atualizando Tipo - O tipo atual é '" + tipoAtual + "' - Digite uma das duas opções 'Cliente' ou 'Administrador': ");
        String novoTipoInput = entrada.nextLine().trim();

        if (!novoTipoInput.isEmpty()) {
            if (!novoTipoInput.equalsIgnoreCase("Cliente") && !novoTipoInput.equalsIgnoreCase("Administrador")) {
                return "Erro: Digite uma das duas opções 'Cliente' ou 'Administrador'";
            }
            tipoAtual = novoTipoInput;
        }

        String usuarioAtualizadoJson = "{\"nomeUsuario\":\"" + nomeAtual + "\",\"senhaUsuario\":\"" + senhaParaJson + "\",\"tipoUsuario\":\"" + tipoAtual + "\"}";

        return api.atualizarUsuarioPorNome(nomeAntigo, usuarioAtualizadoJson);
    }

    public static String extrairCampoJson(String json, String campo) {
        try {
            int inicio = json.indexOf("\"" + campo + "\":\"") + campo.length() + 4;
            int fim = json.indexOf("\"", inicio);
            return json.substring(inicio, fim);
        } catch (Exception erro) {
            return "";
        }
    }

    public static boolean nomeJaExiste(String nomeUsuario) {
        final String ARQUIVO_LOGIN = "login.csv";
        try (BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOGIN))) {
            String linha;
            while ((linha = leitor.readLine()) != null) {
                String[] dadosUsuario = linha.split(",");
                if (dadosUsuario.length >= 1) {
                    String usuarioExistente = dadosUsuario[0].trim();
                    if (usuarioExistente.equalsIgnoreCase(nomeUsuario)) {
                        return true;
                    }
                }
            }
        } catch (IOException erro) {
            System.out.println("Erro ao ler arquivo CSV: " + erro.getMessage());
        }
        return false;
    }

}
