package pl.java.scalatech.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import pl.java.scalatech.service.PingPongService;

//@Component
public class PingPongRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("cxf:/PingPong?serviceClass=" + PingPongService.class.getName()).to("log:" + getClass().getName()).convertBodyTo(String.class)
                .process(new Processor() {

                    @Override
                    public void process(Exchange e) throws Exception {
                        String pingRequest = e.getIn().getBody(String.class);
                        e.getIn().setBody(new Object[] { "Pong: " + pingRequest });

                    }
                });
    }

}