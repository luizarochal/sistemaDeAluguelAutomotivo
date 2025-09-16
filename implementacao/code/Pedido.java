package sistemaDeAluguelAutomotivo.implementacao.code;

public class Pedido {

    private Automovel automovel;

    public Pedido(Automovel automovel) {
        this.automovel = automovel;
    }

    public void avaliar() {
    }

    public Automovel getAutomovel() {
        return automovel;
    }

    public void setAutomovel(Automovel automovel) {
        this.automovel = automovel;
    }

}
