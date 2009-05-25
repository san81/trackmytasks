package com.san.my.web.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.san.my.common.global.AppConstants;
import com.san.my.common.util.springs.BeanLocatorFactory;
import com.san.my.dao.ObjectDAO;
import com.san.my.dataobj.AccountsDO;
import com.san.my.dataobj.SlipDO;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final  String[] defaultConfigPaths = {AppConstants.APP_DAO_CONFIG,AppConstants.APP_BOTransaction_CONFIG,AppConstants.APP_CONFIG};		    	
		    	
			ApplicationContext appContext = new ClassPathXmlApplicationContext(defaultConfigPaths);
			ObjectDAO objectDAO = (ObjectDAO)appContext.getBean("objectDAO");
			Session session = objectDAO.getHibSession();
	       	 Transaction tx = session.beginTransaction();
	       	 	AccountsDO accountsDO = (AccountsDO)session.load(AccountsDO.class, 2);
	       	 	
	       	 tx.commit();
	       	 session.close();
	       	 //accountsDO.setAccountId(2);
	       	session = objectDAO.getHibSession();
	       	 tx = session.beginTransaction();
	       	 accountsDO.setName("one4");
	       session.update(accountsDO);
	       	 tx.commit();
		}
	
	public static void trMethod(){
		final  String[] defaultConfigPaths = {AppConstants.APP_DAO_CONFIG,AppConstants.APP_BOTransaction_CONFIG,AppConstants.APP_CONFIG};
		ApplicationContext appContext = null;			 
        try {
        		appContext = new ClassPathXmlApplicationContext(defaultConfigPaths);
        		final ApplicationContext fappContext = appContext;
        		BeanLocatorFactory.setApplicationContext(appContext);
        		 TransactionTemplate txTemplate = (TransactionTemplate) BeanLocatorFactory.getBean("transactionTemplate");

                 txTemplate.execute(new TransactionCallbackWithoutResult() {
                     public void doInTransactionWithoutResult(TransactionStatus status)
                     {
                         try {
                         	
                        	 ObjectDAO objectDAO = (ObjectDAO)fappContext.getBean("objectDAO");
                        	 Session session = objectDAO.getHibSession();
                        	 Transaction tx = session.beginTransaction();
                        	 	AccountsDO accountsDO = (AccountsDO)session.load(AccountsDO.class, 2);
                        	 tx.commit();
                        	 //accountsDO.setAccountId(2);
                        	 accountsDO.setName("gfeabcaaaaad");
                        	 accountsDO.setLoginName("gfeaaaaaaa");
                        	 session.merge(accountsDO);
                        	
                        	 SlipDO slip  = (SlipDO)objectDAO.load(SlipDO.class,new Integer(21070));
                        	 
                         }
                         catch (Exception ex) {
                             status.setRollbackOnly();
                             ex.printStackTrace();                        
                         }
                     }
                 });
        		System.out.print("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}

