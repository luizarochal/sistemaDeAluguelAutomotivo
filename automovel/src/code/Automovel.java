package sistemaDeAluguelAutomotivo.implementacao.code;

import java.sql.Date;

public class Automovel {

    private String matricula;
    private String marca;
    private String modelo;
    private String placa;
    private Date ano;

    public Automovel(String matricula, String marca, String modelo, String placa, Date ano) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
    }

    public void registrar() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getAno() {
        return ano;
    }

    public void setAno(Date ano) {
        this.ano = ano;
    }

}
