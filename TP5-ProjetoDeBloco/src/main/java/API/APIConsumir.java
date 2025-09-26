package API;
import java.io.*;
import java.net.*;

public class APIConsumir {
    String url = "http://localhost:7000";

    private HttpURLConnection gerarConnection(String resource, String method) throws URISyntaxException, IOException {
        String sUri = url + resource;
        URI uri = new URI(sUri);
        URL url = uri.toURL();
        HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
        conexao.setRequestMethod(method);
        return conexao;
    }

    private String tratarResposta(HttpURLConnection connection) throws IOException {
        StringBuilder construtorResposta = new StringBuilder();
        InputStreamReader leitorFluxo = new InputStreamReader(connection.getInputStream());
        BufferedReader leitorBuffer = new BufferedReader(leitorFluxo);
        String linhaResposta;
        while ((linhaResposta = leitorBuffer.readLine()) != null) {
            construtorResposta.append(linhaResposta);
        }
        connection.disconnect();
        return construtorResposta.toString();
    }

    public String getTodosUsuarios() throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/usuarios", "GET");
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        }
        return "Erro ao buscar usuários: " + codigoResposta;
    }

    public String getUsuarioPorNome(String nomeUsuario) throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/usuarios/" + nomeUsuario, "GET");
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else if (codigoResposta == HttpURLConnection.HTTP_NOT_FOUND) {
            return "Usuário não encontrado.";
        }
        return "Erro ao buscar usuário: " + codigoResposta;
    }

    public String inserirUsuario(String usuarioJson) throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/usuarios", "POST");
        conexao.setDoOutput(true);
        OutputStream saida = conexao.getOutputStream();
        byte[] conteudoBody = usuarioJson.getBytes();
        saida.write(conteudoBody);
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_CREATED) {
            return tratarResposta(conexao);
        }
        return "Erro ao cadastrar usuário (este usuário já existe ou os dados enviados são inválidos) - Código recebido: " + codigoResposta;
    }

    public String atualizarUsuarioPorNome(String nomeUsuario, String usuarioAtualizadoJson) throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/usuarios/" + nomeUsuario, "PUT");
        conexao.setDoOutput(true);
        OutputStream saida = conexao.getOutputStream();
        byte[] conteudoBody = usuarioAtualizadoJson.getBytes();
        saida.write(conteudoBody);
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else if (codigoResposta == HttpURLConnection.HTTP_NOT_FOUND) {
            return "Usuário não encontrado para atualizar.";
        }
        return "Erro ao atualizar usuário: " + codigoResposta;
    }

    public String deleteUsuarioPorNome(String nomeUsuario) throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/usuarios/" + nomeUsuario, "DELETE");
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_OK) {
            System.out.print("O usuário foi deletado com sucesso: ");
            return tratarResposta(conexao);
        } else if (codigoResposta == HttpURLConnection.HTTP_NOT_FOUND) {
            return "Usuário não encontrado para deletar.";
        }
        return "Erro ao deletar usuário: " + codigoResposta;
    }

    public String getSenhaPorNome(String nomeUsuario) throws IOException, URISyntaxException {
        HttpURLConnection conexao = gerarConnection("/senhas/" + nomeUsuario, "GET");
        int codigoResposta = conexao.getResponseCode();
        System.out.println("O código de resposta recebido foi: " + codigoResposta);
        if (codigoResposta == HttpURLConnection.HTTP_OK) {
            return tratarResposta(conexao);
        } else if (codigoResposta == HttpURLConnection.HTTP_NOT_FOUND) {
            return "Usuário não encontrado.";
        }
        return "Erro ao buscar senha: " + codigoResposta;
    }
}
