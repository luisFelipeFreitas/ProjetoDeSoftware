package excecao;

public class EquipamentoNaoEncontradoException extends Exception {
    private final static long serialVersionUID = 1;

    public EquipamentoNaoEncontradoException() {
	super();
    }

    public EquipamentoNaoEncontradoException(String msg) {
	super(msg);
    }
}