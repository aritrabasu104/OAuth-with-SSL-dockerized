package myapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.model.Customer;
import myapp.repository.CustomerRepository;
import myapp.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;
	
	@Override
	public List<Customer> getCustomers() {
		List<Customer> list = new ArrayList<>();
		customerRepository.findAll().forEach(customer->list.add(customer));
		return list;
	}

}
