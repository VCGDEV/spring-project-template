package com.vcgdev.demo;

import com.vcgdev.demo.config.auth.IAuthenticationFacade;
import com.vcgdev.demo.config.auth.RestTemplateInterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableOAuth2Client
public class SpringProjectTemplateApplication {

	private final IAuthenticationFacade authenticationFacade;

	public SpringProjectTemplateApplication(IAuthenticationFacade authenticationFacade) {
		this.authenticationFacade = authenticationFacade;
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringProjectTemplateApplication.class, args);
	}

	@Bean("securedInternalRestTemplate")
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors()
			.add(new RestTemplateInterceptor(authenticationFacade));
		return restTemplate;
	}

	@Bean("oauthRestTemplate")
	public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oauth2ClientContext,
			OAuth2ProtectedResourceDetails details) {
		return new OAuth2RestTemplate(details, oauth2ClientContext);
	}

}
