package sistemaDeAluguelAutomotivo.implementacao.code;

public class Contrato {

    private Pedido pedido;

    public Contrato(Pedido pedido) {
        this.pedido = pedido;
    }

    public void registro() {
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

}
