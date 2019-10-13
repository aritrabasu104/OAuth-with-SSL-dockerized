package myapp;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableOAuth2Sso
public class MyAppApplication {
	
	@Autowired
	private Environment env;
	
	private static final String SERVER_PORT_PROP_NAME = "server.port";
	private static final String SERVER_HTTP_PORT_PROP_NAME = "server.http.port";
	
	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		// Enable SSL Trafic
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		// Add HTTP to HTTPS redirect
		tomcat.addAdditionalTomcatConnectors(httpToHttpsRedirectConnector());

		return tomcat;
	}

	/*
	 * We need to redirect from HTTP to HTTPS. Without SSL, this application used
	 * port 8082. With SSL it will use port 8443. So, any request for 8082 needs to
	 * be redirected to HTTPS on 8443.
	 */
	private Connector httpToHttpsRedirectConnector() {
		Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
		connector.setScheme("http");
		connector.setPort(Integer.parseInt(env.getProperty(SERVER_HTTP_PORT_PROP_NAME)));
		connector.setSecure(false);
		connector.setRedirectPort(Integer.parseInt(env.getProperty(SERVER_PORT_PROP_NAME)));
		return connector;
	}
}
