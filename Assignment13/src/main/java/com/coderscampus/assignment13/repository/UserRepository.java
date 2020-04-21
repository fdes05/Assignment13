package com.coderscampus.assignment13.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.coderscampus.assignment13.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	// select * from users where username = :username
	List<User> findByUsername(String username);
	
	// select * from users where name = :name
	List<User> findByName(String name);
	
	// select * from users where name = :name and username = :username
	List<User> findByNameAndUsername(String name, String username);
	
	List<User> findByCreatedDateBetween(LocalDate date1, LocalDate date2);
	
	@Query("select u from User u where username = :username")
	List<User> findExactlyOneUserByUsername(String username);
	
	@Query("select u from User u"
		+ " left join fetch u.accounts"
		+ " left join fetch u.address")
	Set<User> findAllUsersWithAccountsAndAddresses();
	
//	@Query (value = "SELECT * FROM users u \n" + 
//			"left join `address` a on u.user_id = a.user_id\n" + 
//			"left join user_account ua on u.user_id = ua.user_id\n" + 
//			"left join accounts acc on acc.account_id = ua.account_id\n" + 
//			"where u.user_id = ?1", nativeQuery = true)
	@Query("select u from User u"
			+ " left join fetch u.accounts"
			+ " left join fetch u.address"
			+ " where u.userId = :userId")
	User findByUserIdWithAccountsAndAddress(Long userId);
	
	@Query("select u from User u"
			+ " left join fetch u.accounts"
			+ " where u.userId = :userId")
	List<User> findByUserIdAllAccounts(Long userId);
}
