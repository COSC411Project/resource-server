package app.proxies;

import java.io.IOException;
import java.net.URISyntaxException;

public interface INLPProxy {

	String getJobs() throws URISyntaxException, IOException, InterruptedException;
	
}
