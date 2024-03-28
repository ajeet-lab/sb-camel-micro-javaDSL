package org.agrregate;

import org.apache.camel.ExchangePattern;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

//@Component
public class MyRoute extends RouteBuilder {

    // Uncomment when run the code
//    @Autowired
//    private HttpAggregateStretegy httpAggregateStretegy;

    @Override
    public void configure() throws Exception {


        rest("/api").consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .post("/posts")
                    .route()
                    .removeHeaders("*")
                    .unmarshal().json()
                    .log("values ar:: ${body['values']}")
                    .split().simple("${body['values']}")
                        .aggregationStrategyRef("httpAggregateStretegy")
                        .setBody().simple("${body}")
                        .log("single data ${body}")
                        .setHeader("CamelHttpMethod",constant("GET"))
                        .toD("https://jsonplaceholder.typicode.com/posts/${body}?bridgeEndpoint=true&throwExceptionOnFailure=false")
                        .convertBodyTo(String.class)
                        .log("${body}");

    }
}
