package org.bigaidc.xmlproject;

import org.bigaidc.xmlproject.xml.XmlOps;
import org.dom4j.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestDslApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestDslApplication.class, args);

		final XmlOps xmlOps = new XmlOps();

		// SAX Parser
		// xmlOps.Process();

		// TODO: Validation
		if(xmlOps.Validate()) {
			System.out.println("Xml was validated");
		} else {
			System.out.println("Xml was NOT validated!");
		}

//		// DOM Parser
//		final Document doc = xmlOps.ParseDom();
//
//		// Find some Books
//		xmlOps.FindBooks();
//
//		// Update xml file
//		xmlOps.Update(doc, xmlOps.parserDom);
	}
}
