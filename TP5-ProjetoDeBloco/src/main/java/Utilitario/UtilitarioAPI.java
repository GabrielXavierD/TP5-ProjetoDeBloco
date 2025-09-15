package Utilitario;
import Modelo.Endereco;
import Modelo.Usuario;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static Utilitario.Utilitario.*;
import static Utilitario.ValidarEndereco.*;

public class UtilitarioAPI {
    final static String ARQUIVO_LOGIN = "login.csv";

    public static List<Usuario> getListaUsuariosCSVSemSenha() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOGIN));
            String linha = leitor.readLine();
            SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            while (linha != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 11) {
                    String nome = dados[0].trim();
                    String tipo = dados[2].trim();
                    String rua = dados[3].trim();
                    String numero = dados[4].trim();
                    String bairro = dados[5].trim();
                    String cidade = dados[6].trim();
                    String estado = dados[7].trim();
                    String cep = dados[8].trim();
                    String complemento = dados[9].trim();
                    String dataStr = dados[10].trim();

                    Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep, complemento);
                    Usuario usuario = new Usuario(nome, "Você não tem autorização para ver a senha", tipo, endereco);
                    try {
                        Date dataCriacao = formatarData.parse(dataStr);
                        usuario.setDataCriacao(dataCriacao);
                    } catch (Exception e) {
                        System.out.println("Erro ao converter data: " + dataStr);
                    }

                    listaUsuarios.add(usuario);
                }
                linha = leitor.readLine();
            }
            leitor.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler arquivo CSV: " + erro.getMessage());
        }
        return listaUsuarios;
    }

    public static List<Usuario> getListaUsuariosCSVComSenha() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        try {
            BufferedReader leitor = new BufferedReader(new FileReader(ARQUIVO_LOGIN));
            String linha = leitor.readLine();
            SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            while (linha != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 11) {
                    String nome = dados[0].trim();
                    String senha = dados[1].trim();
                    String tipo = dados[2].trim();
                    String rua = dados[3].trim();
                    String numero = dados[4].trim();
                    String bairro = dados[5].trim();
                    String cidade = dados[6].trim();
                    String estado = dados[7].trim();
                    String cep = dados[8].trim();
                    String complemento = dados[9].trim();
                    String dataStr = dados[10].trim();

                    Endereco endereco = new Endereco(rua, numero, bairro, cidade, estado, cep, complemento);
                    Usuario usuario = new Usuario(nome, senha, tipo, endereco);
                    try {
                        Date dataCriacao = formatarData.parse(dataStr);
                        usuario.setDataCriacao(dataCriacao);
                    } catch (Exception e) {
                        System.out.println("Erro ao converter data: " + dataStr);
                    }

                    listaUsuarios.add(usuario);
                }
                linha = leitor.readLine();
            }
            leitor.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler arquivo CSV: " + erro.getMessage());
        }
        return listaUsuarios;
    }

    public static void salvarUsuariosNoCSV(List<Usuario> usuarios) {
        final String ARQUIVO_LOGIN = "login.csv";
        try (BufferedWriter escrever = new BufferedWriter(new FileWriter(ARQUIVO_LOGIN))) {
            for (Usuario usuario : usuarios) {
                String linha =
                        usuario.getNomeUsuario() + "," + usuario.getSenhaUsuario() + "," + usuario.getTipoUsuario() + "," + usuario.getEndereco().getRua() + "," + usuario.getEndereco().getNumero() + "," + usuario.getEndereco().getBairro() + "," + usuario.getEndereco().getCidade() + "," + usuario.getEndereco().getEstado() + "," + usuario.getEndereco().getCep() + "," + usuario.getEndereco().getComplemento() + "," + usuario.getDataCriacao();
                escrever.write(linha);
                escrever.newLine();
            }
        } catch (IOException erro) {
            System.out.println("Erro ao salvar usuários: " + erro.getMessage());
        }
    }

    public static String receberUsuarioCompletoJson() {
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

        return "{\"nomeUsuario\":\"" + nome + "\",\"senhaUsuario\":\"" + senha + "\",\"tipoUsuario\":\"" + tipo + "\",\"endereco\":{" + "\"rua\":\"" + rua + "\",\"numero\":\"" + numero + "\",\"bairro\":\"" + bairro + "\",\"cidade\":\"" + cidade + "\",\"estado\":\"" + estado + "\",\"cep\":\"" + cep + "\",\"complemento\":\"" + complemento + "\"}}";
    }

}
