package pl.java.scalatech;

import java.math.BigDecimal;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import pl.java.scalatech.domain.Customer;
import pl.java.scalatech.repository.CustomerRepository;

import com.google.common.collect.Lists;

@SpringBootApplication
@Import(CxfConfig.class)
@Slf4j
public class CxfApplication implements CommandLineRunner {
    @Autowired
    private CustomerRepository customerRepository;

    public static void main(String[] args) {
        SpringApplication.run(CxfApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        List<Customer> customers = Lists.newArrayList(Customer.builder().name("slawek").salary(new BigDecimal("34")).build(),
                Customer.builder().name("przodownik").salary(new BigDecimal("54")).build(), Customer.builder().name("agnieszka").salary(new BigDecimal("7"))
                        .build(), Customer.builder().name("kalina").salary(new BigDecimal("88")).build(),
                Customer.builder().name("danuta").salary(new BigDecimal("54")).build());
        customers.stream().forEach(c -> customerRepository.save(c));

        log.info("+++ {}", customerRepository.count());

    }
}
