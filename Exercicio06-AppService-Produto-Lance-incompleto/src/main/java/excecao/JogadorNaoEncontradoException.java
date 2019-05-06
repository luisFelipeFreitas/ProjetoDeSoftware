package excecao;

public class JogadorNaoEncontradoException extends Exception {
    private final static long serialVersionUID = 1;

    public JogadorNaoEncontradoException() {
	super();
    }

    public JogadorNaoEncontradoException(String msg) {
	super(msg);
    }
}