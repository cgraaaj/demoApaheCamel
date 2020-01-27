package com.produce_consumer;

import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class ProducerConsumer {
    public static void main(String[] args) throws Exception {

        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .process(new Processor() {
                            public void process(Exchange exchange) throws Exception {
                                System.out.println(exchange.getIn().getBody(String.class));
                                exchange.getOut().setBody("wassup");
                            }
                        })
                        .to("seda:end");
            }
        });

        context.start();
        ProducerTemplate producerTemplate = context.createProducerTemplate();
        producerTemplate.sendBody("direct:start", "hai");
        ConsumerTemplate consumerTemplate = context.createConsumerTemplate();
        System.out.println(consumerTemplate.receiveBody("seda:end", String.class));
    }
}
