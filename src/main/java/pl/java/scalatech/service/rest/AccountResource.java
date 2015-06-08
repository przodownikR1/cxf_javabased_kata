package pl.java.scalatech.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Component;

@Component
@Path("api/{accounts")
public class AccountResource {
    @GET
    @Produces("application/json")
    public String version() {
        return "1.0.0";
    }
}