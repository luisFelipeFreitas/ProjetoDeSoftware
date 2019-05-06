package excecao;

public class HabilidadeNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    public HabilidadeNaoEncontradaException() {
	super();
    }

    public HabilidadeNaoEncontradaException(String msg) {
	super(msg);
    }
}