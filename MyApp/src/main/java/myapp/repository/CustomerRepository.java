package myapp.repository;

import org.springframework.data.repository.CrudRepository;

import myapp.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
