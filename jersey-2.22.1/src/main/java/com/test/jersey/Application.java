package com.test.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.ext.ParamConverterProvider;

@ApplicationPath("/*")
public class Application extends ResourceConfig {
    public Application() {
        register(new org.glassfish.hk2.utilities.binding.AbstractBinder() {
            @Override
            protected void configure() {
                bind(DateParamProvider.class).to(ParamConverterProvider.class).ranked(10);
            }
        });
        register(DateController.class);
    }
}