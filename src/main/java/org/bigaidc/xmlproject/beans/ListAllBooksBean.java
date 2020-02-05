package org.bigaidc.xmlproject.beans;

import org.springframework.stereotype.Component;
import org.bigaidc.xmlproject.xml.XmlOps;

import java.io.StringWriter;

@Component
public class ListAllBooksBean {

    XmlOps xmlOps = new XmlOps();

    public String list() {
        return "";
//        return xmlOps.listAllBooks();
    }

}
