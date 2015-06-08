package pl.java.scalatech.service;

import javax.jws.WebService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@WebService(endpointInterface = "pl.java.scalatech.service.HelloWorld")
@Slf4j
@Service
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String sayHi(String text) {
        log.info("++++++++++++++++++++++++++++++++++  {}", text);
        return "przodownik " + text;
    }

}