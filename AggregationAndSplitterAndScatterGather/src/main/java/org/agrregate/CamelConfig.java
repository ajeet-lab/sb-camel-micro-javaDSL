package org.agrregate;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {
    @Bean()
    public HttpAggregateStretegy myAggregationStrategy() {
        return new HttpAggregateStretegy();
    }
}
