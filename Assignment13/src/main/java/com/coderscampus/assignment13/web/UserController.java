package com.coderscampus.assignment13.web;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.coderscampus.assignment13.domain.Account;
import com.coderscampus.assignment13.domain.User;
import com.coderscampus.assignment13.service.AccountService;
import com.coderscampus.assignment13.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;

	@GetMapping("/register")
	public String getCreateUser(ModelMap model) {
		model.put("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String postCreateUser(User user) {
		System.out.println(user);
		userService.saveUser(user);
		return "redirect:/register";
	}

	@GetMapping("/users")
	public String getAllUsers(ModelMap model) {
		Set<User> users = userService.findAll();

		model.put("users", users);
		if (users.size() == 1) {
			model.put("user", users.iterator().next());
		}

		return "users";
	}

	@GetMapping("/users/{userId}")
	public String getOneUser(ModelMap model, @PathVariable Long userId) {
		User user = userService.findById(userId);
		model.put("user", user);
		return "user";
	}

	@PostMapping("/users/{userId}")
	public String postOneUser(User user) {
		userService.saveUser(user);
		return "redirect:/users/" + user.getUserId();
	}
	
	@GetMapping("/users/{userId}/accounts")
	public String getNewUserAccount(@PathVariable Long userId, ModelMap model) {
		User user = userService.findUserById(userId);
		
		model.put("user", user);
		model.put("account", new Account());
		
		return "accounts";
	}
	
	@PostMapping("/users/{userId}/accounts")
	public String safeNewAccountForUser(Account account, @PathVariable Long userId, ModelMap model) {
		User user = userService.findUserById(userId);
		user.getAccounts().add(account);
		userService.saveUser(user);
		return "redirect:/users/{userId}/accounts";
	}

	@GetMapping("/users/{userId}/accounts/{accountId}")
	public String viewUserAccount (@PathVariable Long userId, @PathVariable Long accountId, ModelMap model) {
		User user = userService.findUserById(userId);
		Account account = accountService.findAccountByAccountId(accountId);
		
		model.put("user", user);
		model.put("account", account);

		return "accounts";
	}
	
	@PostMapping ("/users/{userId}/accounts/{accountId}")
	public String saveUserAccount(User user, Account account) {
		accountService.saveAccount(account);
		return "redirect:/users/{userId}/accounts/{accountId}";
	}

	@PostMapping("/users/{userId}/delete")
	public String deleteOneUser(@PathVariable Long userId) {
		accountService.delete(userId);
		userService.delete(userId);
		return "redirect:/users";
	}
}
