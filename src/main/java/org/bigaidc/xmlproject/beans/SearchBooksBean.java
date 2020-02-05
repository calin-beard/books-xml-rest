package org.bigaidc.xmlproject.beans;

import org.springframework.stereotype.Component;

import org.bigaidc.xmlproject.xml.XmlOps;
import org.bigaidc.xmlproject.data.JsonMapper;

@Component
public class SearchBooksBean {

    XmlOps xmlOps = new XmlOps();
    JsonMapper jsonMapper = new JsonMapper();

    public String searchByAuthor(String name) {
        System.out.println("bean " + name);
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        return jsonMapper.map(xmlOps.FindBooksByAuthor(name));
    }

    public String searchByAuthorsCount(String minCount) {
        return jsonMapper.map(xmlOps.FindBooksByMinAuthors(Integer.valueOf(minCount)));
    }

    public String searchByTitle(String title) {
        return jsonMapper.map(xmlOps.FindBooksByTitle(title));
    }

    public String searchByTitleRegex(String regex) {
        return jsonMapper.map(xmlOps.FindBooksByRegexTitle(regex));
    }

    public String searchByDomain(String domain) {
        return jsonMapper.map(xmlOps.FindBooksByDomain(domain));
    }

    public String searchByYear(String year) {
        return jsonMapper.map(xmlOps.FindBooksByYear(Integer.valueOf(year)));
    }

}
