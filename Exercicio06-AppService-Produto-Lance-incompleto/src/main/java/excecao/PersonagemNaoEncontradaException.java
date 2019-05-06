package excecao;

public class PersonagemNaoEncontradaException extends Exception {
    private final static long serialVersionUID = 1;

    public PersonagemNaoEncontradaException() {
	super();
    }

    public PersonagemNaoEncontradaException(String msg) {
	super(msg);
    }
}