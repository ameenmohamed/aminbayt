package org.hac.amin.bayt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import io.undertow.Undertow.Builder;


@SpringBootApplication
@ComponentScan("org.hac") 
public class AmeenbaytApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmeenbaytApplication.class, args);
	}
	
	
	/*@Bean
	public UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {
	    UndertowEmbeddedServletContainerFactory factory =  new UndertowEmbeddedServletContainerFactory();
	     
	    factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
	        @Override
	        public void customize(io.undertow.Undertow.Builder builder) {
	            builder.addHttpListener(8080, "0.0.0.0");
	        }

			@Override
			public void customize(Builder builder) {
				// TODO Auto-generated method stub
				
			}
	    });
	     
	    return factory;
	}*/
}
