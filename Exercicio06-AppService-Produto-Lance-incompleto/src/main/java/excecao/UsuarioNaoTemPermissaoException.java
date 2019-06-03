package excecao;

public class UsuarioNaoTemPermissaoException extends Throwable {
    private final static long serialVersionUID = 1;

    public UsuarioNaoTemPermissaoException() {
    	super();
    }

    public UsuarioNaoTemPermissaoException(String msg) {
    	super(msg);
    }
}