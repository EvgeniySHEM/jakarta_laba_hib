package ru.sanctio.jakarta_laba_hib.api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("api")
public class RestConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        resources.add(RestClientService.class);
        resources.add(RestAddressService.class);
        return resources;
    }
}
