package sistemaDeAluguelAutomotivo.implementacao.code;

import java.util.List;

public class Clientes extends Usuario {

    private String RG;
    private String CPF;
    private String nome;
    private String endereco;
    private String profissao;
    private List<String> entidadesEmpregadoras;
    private static final int MAX_EMPRESAS = 3;

    public Clientes(String RG, String CPF, String nome, String endereco, String profissao,
            List<String> entidadesEmpregadoras) {
        this.RG = RG;
        this.CPF = CPF;
        this.nome = nome;
        this.endereco = endereco;
        this.profissao = profissao;
        this.entidadesEmpregadoras = entidadesEmpregadoras;
    }

    public void introduzirPedido() {
    }

    public void modificarPedido() {
    }

    public void consultarPedido() {
    }

    public void cancelarPedido() {
    }

    public String getRG() {
        return RG;
    }

    public void setRG(String rG) {
        RG = rG;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getProfissao() {
        return profissao;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public List<String> getEntidadesEmpregadoras() {
        return entidadesEmpregadoras;
    }

    public void setEntidadesEmpregadoras(List<String> entidadesEmpregadoras) {
        this.entidadesEmpregadoras = entidadesEmpregadoras;
    }

}
