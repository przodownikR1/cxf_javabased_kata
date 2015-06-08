package pl.java.scalatech;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories(basePackages = "pl.java.scalatech.repository")
@EnableTransactionManagement
public class TransactionConfig {

}
