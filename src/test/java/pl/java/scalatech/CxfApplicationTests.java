package pl.java.scalatech;

import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CxfApplication.class)
@WebAppConfiguration
@Slf4j
public class CxfApplicationTests {
    @Value("${server.port}")
    private String serverPort;

    @Test
    public void contextLoads() {

        log.info("+++      serverPort  : {}", serverPort);

    }

}
