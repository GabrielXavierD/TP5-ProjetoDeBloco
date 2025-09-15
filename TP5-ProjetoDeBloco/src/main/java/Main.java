import API.APIConfiguracao;
import InteracaoComUsuario.Interacao;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        var aplicacao = Javalin.create();
        APIConfiguracao.config(aplicacao);
        aplicacao.start(7000);
        Interacao.menu();
    }
}