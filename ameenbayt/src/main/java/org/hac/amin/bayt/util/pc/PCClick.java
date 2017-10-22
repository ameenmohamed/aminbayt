package org.hac.amin.bayt.util.pc;

import java.util.ArrayList;
import java.util.List;

import org.hac.amin.bayt.util.Click;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
public class PCClick implements Click {

	@Override
	public String apiClick() {		
		return "pcClick";
	}

	@Override
	public String click() {
		
		return "pcClick";
	}

	@Override
	public String testCmd() {
		List<String> command = new ArrayList<String>();
		command.add("ls");
		command.add("-l");
		command.add("/var/tmp");
		return null;
	}

}
