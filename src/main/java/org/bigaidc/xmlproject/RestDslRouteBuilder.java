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
                .bindingMode(RestBindingMode.auto);

        /**
         * Configure the REST API (POST, GET, etc.)
         */
        rest("/books").consumes("application/x-www-form-urlencoded").produces("application/json")
                .post("new").route().bean(AddBookBean.class, "addBook(${body.title}, ${body.authors}, ${body.date}, ${body.domain}, ${body.published})").endRest()
                .get().route().bean(ListAllBooksBean.class, "list").endRest()
                .get("author?value={value}").route().bean(SearchBooksBean.class, "searchByAuthor(${header.value})").endRest()
                .get("authorCount?minCount={minCount}").route().bean(SearchBooksBean.class, "searchByAuthorsCount(${header.minCount})").endRest()
                .get("title?value={value}").route().bean(SearchBooksBean.class, "searchByTitle(${header.value})").endRest()
                .get("titleRegex?value={value}").route().bean(SearchBooksBean.class, "searchByTitleRegex(${header.value})").endRest()
                .get("domain?value=${value}").route().bean(SearchBooksBean.class, "searchByDomain(${header.value})").endRest()
                .get("year?value=${value}").route().bean(SearchBooksBean.class, "searchByYear(${header.value})").endRest()
                .put("update?title=${title}&field=${field}&value=${value}").route().bean(UpdateBookBean.class, "updateByTitle(${header.title}, ${header.field}, ${header.value})").endRest();
    }
}
