package org.hac.amin.bayt.model;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import lombok.Data;

@Component
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@ConfigurationProperties(prefix = "ameenbayt.baytConfig")
@JsonComponent
public @Data class BaytConfig implements Serializable{
	
	private String dateFormat;
	private String appKey;
	private String appSecret;
	private String accessToken;
	private String dbPicLocation;
	private String dbVidLocation;
	private String raspiVidpath;
	private String imgResizeValue;
	private String lightOnCmdS1;
	private String lightOffCmdS1;
	private String imgWidth;
	private String imgHeight;
	private boolean activateSecurity;
	private int picTimeout;
	private String raspistillPath;
	private int motionPauseTime ;
	private String systemState;
	private String timeFormat;
	private int burstCount;
	private int imgClickQuality;
	private String s3root;
	private String s3fileloc;
	private String awsakId;
	private String awsseckey;

}
