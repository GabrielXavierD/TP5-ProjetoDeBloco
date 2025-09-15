package API;
import Modelo.Usuario;
import Utilitario.UtilitarioAPI;
import io.javalin.*;
import io.javalin.http.Context;
import java.util.*;
import static Utilitario.UtilitarioAPI.salvarUsuariosNoCSV;

public class APIConfiguracao {
    public static void config(Javalin app) {
        app.get("/", context -> context.result("Servidor online."));
        app.get("/usuarios", APIConfiguracao::getListaUsuarios);
        app.get("/usuarios/{nomeUsuario}", APIConfiguracao::getUsuarioPorNome);
        app.post("/usuarios", APIConfiguracao::inserirUsuario);
        app.put("/usuarios/{nomeUsuario}", APIConfiguracao::atualizarUsuario);
        app.delete("/usuarios/{nomeUsuario}", APIConfiguracao::deletarUsuario);
        app.get("/senhas/{nomeUsuario}", APIConfiguracao::getSenhaPorNome);
    }

    private static void getListaUsuarios(Context contexto) {
        List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVSemSenha();
        contexto.status(200).json(listaUsuarios);
    }

    private static void getUsuarioPorNome(Context contexto) {
        String nome = contexto.pathParam("nomeUsuario");
        List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVSemSenha();
        Usuario usuarioEncontrado = null;

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNomeUsuario().equalsIgnoreCase(nome)) {
                usuarioEncontrado = usuario;
                break;
            }
        }

        if (usuarioEncontrado != null) {
            contexto.status(200).json(usuarioEncontrado);
        } else {
            contexto.status(404).result("Usuário não encontrado.");
        }
    }

    private static void inserirUsuario(Context contexto) {
        try {
            Usuario usuario = contexto.bodyAsClass(Usuario.class);
            List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVComSenha();

            for (Usuario user : listaUsuarios) {
                if (user.getNomeUsuario().equalsIgnoreCase(usuario.getNomeUsuario())) {
                    contexto.status(400).result("Usuário já existe!");
                    return;
                }
            }
            listaUsuarios.add(usuario);
            UtilitarioAPI.salvarUsuariosNoCSV(listaUsuarios);
            contexto.status(201).json(usuario);
        } catch (Exception erro) {
            contexto.status(500).result("Erro ao processar usuário: " + erro.getMessage());
        }
    }

    private static void atualizarUsuario(Context contexto) {
        String nomeAntigo = contexto.pathParam("nomeUsuario");
        Usuario usuarioAtualizado = contexto.bodyAsClass(Usuario.class);
        List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVComSenha();

        boolean encontrado = false;
        for (int i = 0; i < listaUsuarios.size(); i++) {
            Usuario usuario = listaUsuarios.get(i);
            if (usuario.getNomeUsuario().equalsIgnoreCase(nomeAntigo)) {
                usuario.setNomeUsuario(usuarioAtualizado.getNomeUsuario());
                if (!usuarioAtualizado.getSenhaUsuario().equals("MANTER_SENHA_ATUAL")) {
                    usuario.setSenhaUsuario(usuarioAtualizado.getSenhaUsuario());
                }
                usuario.setTipoUsuario(usuarioAtualizado.getTipoUsuario());
                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            salvarUsuariosNoCSV(listaUsuarios);
            contexto.status(200).result("Usuário atualizado com sucesso!");
        } else {
            contexto.status(404).result("Usuário não encontrado para atualizar.");
        }
    }

    private static void deletarUsuario(Context contexto){
        String nome = contexto.pathParam("nomeUsuario");
        List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVComSenha();
        Usuario usuarioEncontrado = null;
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNomeUsuario().equalsIgnoreCase(nome)) {
                usuarioEncontrado = usuario;
                break;
            }
        }
        if (usuarioEncontrado != null) {
            listaUsuarios.remove(usuarioEncontrado);
            UtilitarioAPI.salvarUsuariosNoCSV(listaUsuarios);
            contexto.status(200).result("Usuario deletado com sucesso!").json(usuarioEncontrado);
        } else {
            contexto.status(404).result("Usuário não encontrado.");
        }
    }

    private static void getSenhaPorNome(Context contexto) {
        String nome = contexto.pathParam("nomeUsuario");
        List<Usuario> listaUsuarios = UtilitarioAPI.getListaUsuariosCSVComSenha();
        for (Usuario usuario : listaUsuarios) {
            if (usuario.getNomeUsuario().equalsIgnoreCase(nome)) {
                contexto.status(200).result(usuario.getSenhaUsuario());
                return;
            }
        }
        contexto.status(404).result("Usuário não encontrado.");
    }

}