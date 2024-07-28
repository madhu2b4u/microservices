package org.apigateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				// Route 1: Simple redirection with header and parameter addition
				.route(p -> p.path("/get")
						.filters(f -> f
								.addRequestHeader("MyHeader", "MyURI")
								.addRequestParameter("Param", "MyValue"))
						.uri("http://httpbin.org:80"))

				// Route 2: Load-balanced routing to currency-exchange-service
				.route(p -> p.path("/currency-exchange-service/**")
						.uri("lb://currency-exchange-service"))

				// Route 3: Load-balanced routing to currency-conversion
				.route(p -> p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))

				// Route 4: Load-balanced routing to currency-conversion-feign
				.route(p -> p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion-feign"))

				// Route 5: Path rewriting and load-balanced routing
				.route(p -> p.path("/currency-conversion-new/**")
						.filters(f -> f.rewritePath(
								"/currency-conversion-new/(?<segment>.*)",
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}

}
