package org.hac.homesecurity.aminbayt.rest;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hac.homesecurity.aminbayt.model.*;
import org.hac.homesecurity.aminbayt.util.BaytConstants;



@Path("/health")
@RequestScoped
public class HealthCheck {
	
	
	
	@GET
	@Path("/pulse")
    @Produces(MediaType.APPLICATION_JSON)
    public Time getTime() {
		String formattedDate = BaytConstants.dateFormat.format(new Date());
		return new Time(formattedDate);
    }

}


