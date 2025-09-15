package Modelo;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;
import java.text.SimpleDateFormat;

@JsonPropertyOrder({
        "nomeUsuario",
        "senhaUsuario",
        "tipoUsuario",
        "endereco",
        "dataCriacao"
})
public class Usuario {
    private String nomeUsuario;
    private String senhaUsuario;
    private String tipoUsuario;
    private Date dataCriacao = new Date();
    private Endereco endereco;
    SimpleDateFormat formatarData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public Usuario(){

    }

    public Usuario(String nomeUsuario, String senhaUsuario, String tipoUsuario, Endereco endereco) {
        this.nomeUsuario = nomeUsuario;
        this.senhaUsuario = senhaUsuario;
        this.tipoUsuario = tipoUsuario;
        this.endereco = endereco;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public String getTipoUsuario() { return tipoUsuario; }

    public void setTipoUsuario(String tipoUsuario) {this.tipoUsuario = tipoUsuario;}

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataCriacao() {
        return formatarData.format(dataCriacao);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String exibirDadosUsuario() {
        String dataFormatada = formatarData.format(dataCriacao);
        return nomeUsuario + "," + senhaUsuario + "," + tipoUsuario + "," + endereco.exibirDadosEndereco() + "," + dataFormatada;
    }

}
