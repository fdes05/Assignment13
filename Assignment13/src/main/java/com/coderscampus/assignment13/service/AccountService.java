package com.coderscampus.assignment13.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.repository.AccountRepository;
import com.coderscampus.assignment13.repository.UserRepository;

@Service
public class AccountService {
	
	@Autowired
	AccountRepository accountRepo;
	
	@Autowired
	UserRepository userRepo;

	public Account findAccountByAccountId(Long accountId) {
		Optional<Account> userAccount = accountRepo.findById(accountId);
		return userAccount.orElse(new Account());
	}

	public Account saveAccount(Account account) {
		return accountRepo.save(account);
		
	}

	public void delete(Long userId) {
		User user = userRepo.findByUserIdWithAccountsAndAddress(userId);
		List<Account> userAccounts = user.getAccounts();
		//List<Account> accountsToDelete = new ArrayList<>();
			
		for (Account account : userAccounts) {
			if (account.getUsers().size() == 1) {
				//accountsToDelete.add(account);
				//account.setUsers(null);
				//Account acc = accountRepo.findById(account.getAccountId()).get();
				//accountRepo.delete(account);
				accountRepo.deleteById(account.getAccountId());
			} else {
				user.getAccounts().remove(account);
				userRepo.save(user);
			}
			
		}
//		user.getAccounts().removeAll(accountsToDelete);
//		userRepo.save(user);
	}
}
