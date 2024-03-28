package org.agrregate;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ScatterAndGatherRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        rest("/api").consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .post("/posts2")
                .route()
                .removeHeaders("*")
                .multicast(new HttpAggregateStretegy()).parallelProcessing()
                .to("direct:endpoint1", "direct:endpoint2", "direct:endpoint3")
                .end();

        from("direct:endpoint1")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/1?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class)
         ;

        from("direct:endpoint2")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/2?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class);

        from("direct:endpoint3")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/3?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class);
    }

}