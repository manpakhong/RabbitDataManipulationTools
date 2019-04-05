package com.rabbitforever.datamanipulation.daos;

import org.hibernate.SessionFactory;

import com.rabbitforever.datamanipulation.utils.HibernateUtilStub;

public abstract class HibernateBaseDao {

	protected SessionFactory sessionFactory = HibernateUtilStub.getSessionFactory();
	
	

}
