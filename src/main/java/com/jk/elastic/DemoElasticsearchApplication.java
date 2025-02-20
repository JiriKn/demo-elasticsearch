package com.jk.elastic;

import com.jk.elastic.service.ElasticsearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class DemoElasticsearchApplication {

	static Logger log = Logger.getLogger(DemoElasticsearchApplication.class.getName());

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DemoElasticsearchApplication.class, args);
		ElasticsearchService elasticsearchService = context.getBean(ElasticsearchService.class);
		log.info("Index name: " + elasticsearchService.getIndexName());
		for(String filePath:args) {
			if(!filePath.startsWith("--")) {
				log.info("Ingesting file: " + filePath);
				try {
					elasticsearchService.ingestCsv(filePath);
				} catch (IOException e) {
					log.log(Level.SEVERE,"Error ingesting file: " + filePath ,e);
				}
			}
		}
	}

}
