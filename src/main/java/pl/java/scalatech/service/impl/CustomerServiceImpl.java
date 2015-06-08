package pl.java.scalatech.service.impl;

import javax.jws.WebService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.repository.CustomerRepository;
import pl.java.scalatech.service.CustomerService;

@WebService(endpointInterface = "pl.java.scalatech.service.CustomerService")
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer findById(Long i) {
        log.info("findbyID::{}", i);
        Customer result = customerRepository.findOne(i);
        log.info("+++  result {} ", result);
        return result;
    }

}
