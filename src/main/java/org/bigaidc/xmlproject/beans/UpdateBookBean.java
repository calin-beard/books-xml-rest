package org.bigaidc.xmlproject.beans;

import org.springframework.stereotype.Component;
import org.bigaidc.xmlproject.xml.XmlOps;

import java.io.StringWriter;

@Component
public class UpdateBookBean {

    XmlOps xmlOps = new XmlOps();

    public String updateByTitle(String title, String field, String value) {
        return "";
//        return xmlOps.updateBookByTitle(title, field, value);
    }
}


