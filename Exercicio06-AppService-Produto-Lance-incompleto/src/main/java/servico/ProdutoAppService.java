package servico;

import java.util.List;

import dao.ProdutoDAO;
import excecao.InfraestruturaException;
import excecao.ObjetoNaoEncontradoException;
import excecao.ProdutoNaoEncontradoException;
import modelo.Produto;
import util.FabricaDeDAOs;
import util.JPAUtil;

public class ProdutoAppService {
    private static ProdutoDAO produtoDAO = FabricaDeDAOs.getDAO(ProdutoDAO.class);

    public long inclui(Produto umProduto) {
	try {
	    // NENHUMA VALIDAÇÃO ESTÁ SENDO REALIZADA AQUI!!!

	    JPAUtil.beginTransaction();

	    long numero = produtoDAO.inclui(umProduto);

	    JPAUtil.commitTransaction();

	    return numero;
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void altera(Produto umProduto) throws ProdutoNaoEncontradoException {
	try {
	    JPAUtil.beginTransaction();

	    produtoDAO.altera(umProduto);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ProdutoNaoEncontradoException("Produto não encontrado");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public void exclui(long numero) throws ProdutoNaoEncontradoException {
	try {
	    JPAUtil.beginTransaction();

	    produtoDAO.exclui(numero);

	    JPAUtil.commitTransaction();
	} catch (ObjetoNaoEncontradoException e) {
	    JPAUtil.rollbackTransaction();

	    throw new ProdutoNaoEncontradoException("Produto não encontrado");
	} catch (InfraestruturaException e) {
	    try {
		JPAUtil.rollbackTransaction();
	    } catch (InfraestruturaException ie) {
	    }

	    throw e;
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Produto recuperaUmProduto(long numero) throws ProdutoNaoEncontradoException {
	try {
	    Produto umProduto = produtoDAO.recuperaUmProduto(numero);

	    return umProduto;
	} catch (ObjetoNaoEncontradoException e) {
	    throw new ProdutoNaoEncontradoException("Produto não encontrado");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public Produto recuperaUmProdutoELances(long numero) throws ProdutoNaoEncontradoException {
	try {
	    return produtoDAO.recuperaUmProdutoELances(numero);
	} catch (ObjetoNaoEncontradoException e) {
	    throw new ProdutoNaoEncontradoException("Produto não encontrado");
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }

    public List<Produto> recuperaProdutosELances() {
	try {
	    return produtoDAO.recuperaProdutosELances();
	} finally {
	    JPAUtil.closeEntityManager();
	}
    }
}