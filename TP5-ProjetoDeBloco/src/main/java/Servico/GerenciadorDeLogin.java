package Servico;
import Modelo.Usuario;
import java.io.*;

public class GerenciadorDeLogin {
    public static boolean buscarUsuario(String usuarioLogin, String senhaLogin, String tipo){
        final String ARQUIVO_LOGIN = "login.csv";
        boolean usuarioEncontrado = false;

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOGIN));
            String linha = leitor.readLine();

            while (linha != null) {
                String[] dadosUsuario = linha.split(",");
                if(dadosUsuario.length >= 3){
                    String usuario = dadosUsuario[0].trim();
                    String senha = dadosUsuario[1].trim();
                    String tipoUsuario = dadosUsuario[2].trim();

                    if(usuario.equals(usuarioLogin) && senha.equals(senhaLogin) && tipoUsuario.equals(tipo)){
                        System.out.println("Login realizado com sucesso!");
                        usuarioEncontrado = true;
                        break;
                    }
                }
                linha = leitor.readLine();
            }
            leitor.close();

            if (!usuarioEncontrado) {
                System.out.println("O usuário não foi encontrado no sistema! Verifique os dados e tente novamente ou registre-se.");
            }

        }catch(IOException erro){
            System.out.println("Erro: não foi possível realizar a leitura do arquivo (" + ARQUIVO_LOGIN + ") : " + erro.getMessage());
        }
        return usuarioEncontrado;
    }

    public static void registrarUsuario(Usuario usuario){
        final String ARQUIVO_LOGIN = "login.csv";
        boolean usuarioJaExisteNoArquivo = false;

        try {
            File arquivo = new File(ARQUIVO_LOGIN);
            if (arquivo.exists()) {
                BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOGIN));
                String linha = leitor.readLine();

                while (linha != null) {
                    String[] dadosUsuario = linha.split(",");
                    if (dadosUsuario.length >= 1) {
                        String usuarioExistente = dadosUsuario[0].trim();
                        if (usuarioExistente.equals(usuario.getNomeUsuario())) {
                            usuarioJaExisteNoArquivo = true;
                            break;
                        }
                    }
                    linha = leitor.readLine();
                }
                leitor.close();
            }
        } catch (IOException erro) {
            System.out.println("O arquivo de 'login.csv' ainda não existe, ele será criado.");
        }

        if (usuarioJaExisteNoArquivo) {
            System.out.println("Este usuário já foi registrado no sistema! Registro cancelado.");
            return;
        }

        try {
            BufferedWriter gravador = new BufferedWriter(new FileWriter(ARQUIVO_LOGIN, true));
            gravador.write(usuario.exibirDadosUsuario() + "\n");
            gravador.close();
            System.out.println("O usuário foi registrado com sucesso!");
        } catch (IOException erro) {
            System.out.println("Erro: falha ao criar arquivo (" + ARQUIVO_LOGIN + ") : " + erro.getMessage());
        }
    }
}