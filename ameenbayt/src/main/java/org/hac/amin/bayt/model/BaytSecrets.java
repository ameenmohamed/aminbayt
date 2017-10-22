package org.hac.amin.bayt.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

import lombok.Data;

@Component
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:secret.properties")
@ConfigurationProperties
public @Data class BaytSecrets {

	@Value("${awsakId}")
    private String key;

    @Value("${awsseckey}")
    private String  secret;

	
}
