package aspects;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import annotations.Perfil;
import excecao.UsuarioNaoTemPermissaoException;
import modelo.SingletonPerfis;

@Aspect
public class AspectoParaLogDeMetodoAntesDaSuaExecucao {
	@Before("call(* servico..*.*(..)) && @annotation(Perfil)")
    	public void verificaPermissao(ProceedingJoinPoint joinPoint) throws Throwable 
	{	
		String methodName = joinPoint.getSignature().getName();
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		Perfil anotacao = method.getAnnotation(Perfil.class);
		if(anotacao!=null) {
			String[] perfis = anotacao.nomes();
			SingletonPerfis perfil = SingletonPerfis.getSingletonPerfis();
			String[] perfisDoUsuario = perfil.getPerfis();
			boolean temPermissao = false;
			for(int i=0;i< perfis.length;i++) {
				for(int j=0;j<perfisDoUsuario.length;j++) {
					if(perfis[i].equals(perfisDoUsuario[j])) {
						temPermissao=true;
					}
				}
			}
			if(temPermissao) {
				try {
					Object resultado = joinPoint.proceed();
				}
				catch(Throwable e) {
					throw e;
				}
			}
			else {
				throw new UsuarioNaoTemPermissaoException("Usuario Não tem permissão");
			}
			
		}
		else {
			try {
				Object resultado = joinPoint.proceed();
			}
			catch(Throwable e) {
				throw e;
			}
		}
		
		
	}
}