package org.agrregate;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class HttpAggregateStretegy implements AggregationStrategy {

    Logger log = LoggerFactory.getLogger(AggregationStrategy.class);
    List<Object> list = new ArrayList<>();
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        System.out.println("****** inside the AggregationStrategy ******");
        Object map = newExchange.getIn().getBody();
        log.info("HttpAggregate ::: ===>  "+newExchange.getIn().getBody());
        ArrayList<Object> list = null;

        // the first time we only have the new exchange
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(map);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            // Integer newValue = newExchange.getIn().getBody(Integer.class);
            list = (ArrayList<Object>) oldExchange.getIn().getBody();
            list.add(map);
            return oldExchange;
        }
    }
}
