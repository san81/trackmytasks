package com.san.my.web.interceptors;

import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.san.my.common.util.springs.BeanLocatorFactory;

public class TransactionInterceptor extends AbstractInterceptor 
{
	static Logger logger = Logger.getLogger(TransactionInterceptor.class);
	
	@Override
	public String intercept(ActionInvocation invoker) throws Exception {
		final ActionInvocation finalInvoker = invoker; 
		try
		{			
			TransactionTemplate txTemplate =(TransactionTemplate) BeanLocatorFactory.getBean("transactionTemplate");
			
			txTemplate.execute(new TransactionCallbackWithoutResult(){
				public void doInTransactionWithoutResult(TransactionStatus status) {
					try {
                        logger.debug("Begin transaction interceptor");
						finalInvoker.invoke();
                        logger.debug("End transaction interceptor");
					} 
					catch(Throwable ex)
					{
						status.setRollbackOnly();
						ex.printStackTrace();
						logger.error("Exception: " + ex.getMessage()  );
                        //rethrowing exception to be handled by web.xml error-page entry
                        throw new RuntimeException(ex);
					}
				}				
			});
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			logger.error("Exception: " + ex.getMessage() );
            throw new RuntimeException(ex);
		}
		return null;
	}
	
	
}
