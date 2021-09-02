package filters;

import javax.ws.rs.Priorities;

import java.io.IOException;

import javax.ws.rs.container.ContainerResponseContext;

import javax.ws.rs.container.ContainerRequestContext;

import javax.annotation.Priority;

import javax.ws.rs.ext.Provider;

import javax.ws.rs.container.ContainerResponseFilter;

@Provider
@Priority(Priorities.HEADER_DECORATOR)
public class FilterCors implements ContainerResponseFilter
{

    @Override
    public void filter(ContainerRequestContext requestContext, 
            ContainerResponseContext responseContext) throws IOException 
    {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", "*");
              responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials", "true");
              responseContext.getHeaders().add(
               "Access-Control-Allow-Headers",
               "origin, content-type, accept, authorization, cookie");
              responseContext.getHeaders().add(
                "Access-Control-Allow-Methods", 
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}