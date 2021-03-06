package com.iHotel.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.iHotel.entity.AdminUser;
import com.iHotel.util.DigestUtil;

@Stateless
public class LoginDaoImpl implements LoginDao {
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Override
	public AdminUser login(String userId, String password) throws Exception {
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		List<AdminUser> userList = new ArrayList<AdminUser>();
		password = DigestUtil.digest(password);
		AdminUser user = null;
		
		userList = entityManager.createQuery("from AdminUser where userId = :userName and password = :password")
		                .setParameter("userId", userId)
		                .setParameter("password", password)
		                .getResultList();
		
		if (userList.size() == 1) {
			user = userList.get(0);
		} else if (userList.size() > 1){
			throw new Exception("Found more than 1 user for username '" + userId + "'");
		}
		
		return user;
	}

}
