package org.hac.amin.bayt.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
public @Data class ExecutorUtil {
	
	ExecutorService executor ;
	
	@PostConstruct
	public void init(){
		executor = Executors.newFixedThreadPool(3);
	}

	
}
