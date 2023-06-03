package com.mirea.opagg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

@SpringBootApplication
public class OPAggApplication {
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {

		SpringApplication.run(OPAggApplication.class, args);
	}

}
