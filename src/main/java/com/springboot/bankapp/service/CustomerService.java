package com.springboot.bankapp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.bankapp.model.Account;
import com.springboot.bankapp.model.Customer;
import com.springboot.bankapp.model.Role;
import com.springboot.bankapp.model.UserInfo;
import com.springboot.bankapp.repository.CustomerRepository;
import com.springboot.bankapp.repository.RoleRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Customer postCustomer(Customer customer) {
		
		//genrate random number 
		
		Random random = new Random();
		
		int temp = random.nextInt(99999);
		String accountNo = "MSB91" + temp;
		
		Account account = new Account();
		
		// add the values in the account 
		account.setAccountNumber(accountNo);
		account.setBalance(1000);
		account.setDateOfOpening(new Date());
		//set account values to customer
		
		customer.setAccount(account);
		
		//encode password and attach to it
		String encodedPass = passwordEncoder.encode(customer.getUserInfo().getPassword());
		
		UserInfo user = new UserInfo();
		user.setUserName(customer.getUserInfo().getUserName());
		user.setPassword(encodedPass);
		
		//create a role and assign it to user
		
		Role role = new Role();
		role.setName("USER");
		role = roleRepository.save(role);
		List<Role> list = new ArrayList<>();
		list.add(role);
		
		user.setRoles(list);
		
		customer.setUserInfo(user);
		
		return customerRepository.save(customer);
	}
	

}
