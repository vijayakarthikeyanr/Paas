package com.paas.app.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.cloudfoundry.client.lib.CloudCredentials;
import org.cloudfoundry.client.lib.CloudFoundryClient;
import org.cloudfoundry.client.lib.domain.CloudApplication;
import org.cloudfoundry.client.lib.domain.CloudSpace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CloudFoundryConfig {
	@Value("${cloudfoundry.target.url}")
	String targetUrl;
	@Value("${cloudfoundry.username}")
	String username;
	@Value("${clodufoundry.password}")
	String password;
	public void generatePassReportFromCloudFoundry() {
		//String target = args[0];
		//String user = args[1];
		//String password = args[2];

		CloudCredentials credentials = new CloudCredentials(username, password);
		CloudFoundryClient client = new CloudFoundryClient(credentials, getTargetURL(targetUrl));
		client.login();

		System.out.printf("%nSpaces:%n");
		for (CloudSpace space : client.getSpaces()) {
			System.out.printf("  %s\t(%s)%n", space.getName(), space.getOrganization().getName());
		}

		System.out.printf("%nApplications:%n");
		for (CloudApplication application : client.getApplications()) {
			System.out.printf("  %s%n", application.getName());
		}

		/*
		 * System.out.printf("%nServices%n"); for (CloudService service :
		 * client.getServices()) { System.out.printf("  %s\t(%s)%n", service.getName(),
		 * service.getLabel()); }
		 */
	}
	
	private URL getTargetURL(String target) {
        try {
            return URI.create(target).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("The target URL is not valid: " + e.getMessage());
        }
    }
}
