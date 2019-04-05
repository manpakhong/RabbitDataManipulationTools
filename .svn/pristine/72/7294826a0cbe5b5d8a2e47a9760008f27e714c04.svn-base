package hksarg.swd.csss.csa.flowtest.utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtilStub {
	private final static Logger logger = LoggerFactory.getLogger(HibernateUtilStub.class);
	private final static String className = HibernateUtilStub.class.getName();
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory buildSessionFactory() { 
		try { 
			// Create the SessionFactory from hibernate.cfg.xml 
				return new Configuration().configure("test.hibernate.cfg.xml").buildSessionFactory(); 
			} catch (Throwable ex) { 
				// Make sure you log the exception, as it might be swallowed 
				logger.error(className + ".buildSessionFactory() - Initial SessionFactory creation failed.", ex);
				throw new ExceptionInInitializerError(ex); 
			} 
		}
	
	public static SessionFactory getSessionFactory() { 
		return sessionFactory; 
	}
}
