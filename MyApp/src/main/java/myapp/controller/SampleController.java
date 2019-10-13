package myapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myapp.model.Customer;
import myapp.service.CustomerService;

@RestController
public class SampleController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestParam(value="name", defaultValue="World") String name) {
        return ResponseEntity.ok().body(customerService.getCustomers());
    }
}
