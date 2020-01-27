package com.calling_method;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;

public class CallMethodUsingClassComponent {

    public static void main(String[] args ) throws Exception {

        Service service = new Service();

        SimpleRegistry registry = new SimpleRegistry();
        registry.put("s",service);

        CamelContext context = new DefaultCamelContext(registry);
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
//                        .to("class:com.calling_method.Service?method=something");
                .to("bean:s?method=something");
            }
        });
        context.start();

        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "Hai ");
    }
}
