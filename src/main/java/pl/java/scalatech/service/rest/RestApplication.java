package pl.java.scalatech.service.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.repository.CustomerRepository;

@ApplicationPath("/")
@Component
public class RestApplication extends Application {
    @Autowired
    private CustomerRepository customerRepository;

    @Produces("application/json")
    @GET
    @Path("/customers/{customerId}/")
    public Customer getCustomer(@PathParam("customerId") Long id) {
        return customerRepository.findOne(id);

    }
}