package org.bigaidc.xmlproject.beans;

import org.springframework.stereotype.Component;
import org.bigaidc.xmlproject.xml.XmlOps;

import java.io.StringWriter;

@Component
public class AddBookBean {

    XmlOps xmlOps = new XmlOps();

    public String addBook(String title, String authors, StringWriter date, String domain, String published) {
        return "";
//        return xmlOps.addBook(title, authors, date, domain, published);
    }

}
