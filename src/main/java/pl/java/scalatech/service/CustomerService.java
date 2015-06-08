package pl.java.scalatech.service;

import javax.jws.WebService;

import pl.java.scalatech.domain.Customer;

@WebService
public interface CustomerService {

    Customer findById(Long i);

}