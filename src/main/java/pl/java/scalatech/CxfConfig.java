package pl.java.scalatech;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.management.InstrumentationManager;
import org.apache.cxf.management.counters.CounterRepository;
import org.apache.cxf.management.jmx.InstrumentationManagerImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import pl.java.scalatech.service.CustomerService;
import pl.java.scalatech.service.HelloWorld;
import pl.java.scalatech.service.PingPongService;
import pl.java.scalatech.service.rest.AccountResource;
import pl.java.scalatech.service.rest.RestApplication;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@Configurable
@Slf4j
// @ImportResource({ "classpath:META-INF/cxf/cxf.xml", "classpath:META-INF/cxf/cxf-servlet.xml" })
public class CxfConfig extends SpringBootServletInitializer {
    @Autowired
    SpringBus cxf;
    @Autowired
    private AccountResource accountResource;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private HelloWorld helloWorld;
    @Autowired
    private RestApplication restApplication;

    @PostConstruct
    public void init() {
        log.info("+++                      init ");
    }

    @Bean
    public ServletRegistrationBean soapServletRegistrationBean() {
        log.info("+++  soapServletRegistration");
        ServletRegistrationBean registration = new ServletRegistrationBean(new CXFServlet(), "/services/*");
        registration.setLoadOnStartup(1);
        registration.setName("CXFServlet");
        return registration;
    }

    @Bean(name = "loggingFeature")
    LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        loggingFeature.setPrettyLogging(true);
        return loggingFeature;
    }

    @Bean
    public CounterRepository counterRepository(SpringBus cxf) {
        CounterRepository repository = new CounterRepository();
        repository.setBus(cxf);
        return repository;
    }

    @Bean
    public InstrumentationManager instrumentationManager(SpringBus cxf) {
        InstrumentationManagerImpl impl = new InstrumentationManagerImpl();
        impl.setEnabled(true);
        impl.setBus(cxf);
        impl.setUsePlatformMBeanServer(true);
        return impl;
    }

    @Bean(name = "cxf", destroyMethod = "shutdown")
    public SpringBus configureCxfBus() {
        final SpringBus bus = new SpringBus();
        List<Feature> features = new ArrayList<>();
        features.add(loggingFeature());
        bus.setFeatures(features);
        bus.setId("cxf");
        return bus;
    }

    @Bean
    public PingPongService pingPong() {
        return new PingPongService() {
            @Override
            public String ping(String ping) {
                return "pong";
            }
        };
    }

    @Bean
    public EndpointImpl customerServiceEndpoint() {
        log.info("++++ {} jaxwsEndpoint customerService", cxf);
        EndpointImpl endpoint = new EndpointImpl(cxf, customerService);
        endpoint.setAddress("/customerService");
        endpoint.setBus(cxf);
        endpoint.publish();
        return endpoint;
    }

    @Bean(name = "helloWorldProviderBean")
    public EndpointImpl helloWorldEndpoint() {
        log.info("++++ {} jaxwsEndpoint hello", cxf);
        EndpointImpl endpoint = new EndpointImpl(cxf, helloWorld);
        endpoint.setAddress("/helloworld");
        endpoint.setBus(cxf);
        endpoint.publish();
        return endpoint;
    }

    @Bean
    public Server restServer() {
        JAXRSServerFactoryBean serverFactoryBean = new JAXRSServerFactoryBean();
        serverFactoryBean.setAddress("/rest");
        serverFactoryBean.setServiceBean(restApplication);
        serverFactoryBean.setBus(cxf);
        serverFactoryBean.setProvider(jsonProvider());
        return serverFactoryBean.create();
    }

    @Bean
    public JacksonJsonProvider jsonProvider() {
        return new JacksonJsonProvider();
    }
    /*
     * @Bean
     * public Server customerWsServer() {
     * JaxWsServerFactoryBean serverFactoryBean = new JaxWsServerFactoryBean();
     * serverFactoryBean.setAddress("/CustomerService");
     * serverFactoryBean.setServiceBean(customerService);
     * serverFactoryBean.setServiceClass(CustomerService.class);
     * serverFactoryBean.setBus(cxfBus);
     * return serverFactoryBean.create();
     * }
     */

    /*
     * @Bean
     * public Endpoint jaxwsEndpoint() {
     * log.info("++++ {} jaxwsEndpoint", cxf);
     * EndpointImpl endpoint = new EndpointImpl(cxf, pingPong());
     * endpoint.setAddress("/ping");
     * endpoint.publish();
     * return endpoint;
     * }
     * @Bean(destroyMethod = "destroy")
     * public Server server(AccountResource accountResource) {
     * JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
     * factory.setBus(cxf);
     * factory.setServiceBeanObjects(accountResource);
     * factory.setProvider(jsonProvider());
     * return factory.create();
     * }
     */

}
