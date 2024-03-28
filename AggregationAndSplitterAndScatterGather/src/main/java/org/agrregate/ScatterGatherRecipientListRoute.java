package org.agrregate;


import org.apache.camel.builder.RouteBuilder;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ScatterGatherRecipientListRoute extends RouteBuilder {


    @Override
    public void configure() throws Exception {
        rest("/api").consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .post("/posts3")
                .route()
                .removeHeaders("*")
                .recipientList().constant("direct:endpoint4, direct:endpoint5,direct:endpoint6, direct:endpoint7")
                    .aggregationStrategy(new HttpAggregateStretegy()).parallelProcessing()
                .end();

        from("direct:endpoint4")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/4?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class)
        ;

        from("direct:endpoint5")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/5?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class);

        from("direct:endpoint6")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/6?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class);

        from("direct:endpoint7")
                .setHeader("CamelHttpMethod", constant("GET"))
                .toD("https://jsonplaceholder.typicode.com/posts/7?bridgeEndpoint=true&throwExceptionOnFailure=false")
                .convertBodyTo(String.class);
    }
}

