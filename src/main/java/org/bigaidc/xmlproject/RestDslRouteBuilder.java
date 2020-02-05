package org.bigaidc.xmlproject;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import org.bigaidc.xmlproject.beans.AddBookBean;
import org.bigaidc.xmlproject.beans.ListAllBooksBean;
import org.bigaidc.xmlproject.beans.SearchBooksBean;
import org.bigaidc.xmlproject.beans.UpdateBookBean;

@Component
public class RestDslRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        /**
         * Use 'restlet', which is a very simple component for providing REST
         * services. Ensure that camel-restlet or camel-restlet-starter is
         * included as a Maven dependency first.
         */
        restConfiguration()
                .component("restlet")
                .host("localhost").port("8080")
                .enableCORS(true) // <-- Important
                .corsAllowCredentials(true) // <-- Important
                .corsHeaderProperty("Access-Control-Allow-Origin","*")
                .corsHeaderProperty("Access-Control-Allow-Headers","Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers, Authorization")
                .bindingMode(RestBindingMode.auto);

        /**
         * Configure the REST API (POST, GET, etc.)
         */
        rest("/books").consumes("application/x-www-form-urlencoded").produces("application/json")
                .post("new").route().bean(AddBookBean.class, "addBook(${body.rating}, ${body.title}, ${body.author}, ${body.date}, ${body.domain})").endRest()
                .get().route().bean(ListAllBooksBean.class, "list").endRest()
                .post("author").route().bean(SearchBooksBean.class, "searchByAuthor(${body.author})").setHeader("Origin",constant("http://localhost:8080")).endRest()
                .get("author/{name}").route().bean(SearchBooksBean.class, "searchByAuthor(${header.name})").setHeader("Origin",constant("http://localhost:8080")).endRest()
                .post("title").route().bean(SearchBooksBean.class, "searchByTitle(${body.title})").endRest()
                .get("title/{title}").route().bean(SearchBooksBean.class, "searchByTitle(${header.title})").endRest()
                .get("authorCount/{minCount}").route().bean(SearchBooksBean.class, "searchByAuthorsCount(${header.minCount})").endRest()
                .get("titleRegex/{regex}").route().bean(SearchBooksBean.class, "searchByTitleRegex(${header.regex})").endRest()
                .post("domain").route().bean(SearchBooksBean.class, "searchByDomain(${body.domain})").endRest()
                .get("domain/{domain}").route().bean(SearchBooksBean.class, "searchByDomain(${header.domain})").endRest()
                .post("year").route().bean(SearchBooksBean.class, "searchByYear(${body.year})").endRest()
                .get("year/{year}").route().bean(SearchBooksBean.class, "searchByYear(${header.year})").endRest()
                .put("update").route().bean(UpdateBookBean.class, "updateByTitle(${body.title}, ${body.field}, ${body.field-value})").endRest();
    }
}
