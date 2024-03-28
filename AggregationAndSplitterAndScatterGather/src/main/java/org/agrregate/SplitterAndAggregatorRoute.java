package org.agrregate;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class SplitterAndAggregatorRoute extends RouteBuilder {
    @Autowired
    private HttpAggregateStretegy httpAggregateStretegy;

    @Override
    public void configure() throws Exception {
        rest("/api").consumes(MediaType.APPLICATION_JSON_VALUE).produces(MediaType.APPLICATION_JSON_VALUE)
                .post("/posts")
                    .route()
                    .removeHeaders("*")
                    .unmarshal().json()
                    .split().simple("${body['values']}")
                        .aggregationStrategyRef("httpAggregateStretegy")
                        .setBody().simple("${body}")
                        .setHeader("CamelHttpMethod",constant("GET"))
                        .toD("https://jsonplaceholder.typicode.com/posts/${body}?bridgeEndpoint=true&throwExceptionOnFailure=false")
                        .convertBodyTo(String.class);

    }
}
