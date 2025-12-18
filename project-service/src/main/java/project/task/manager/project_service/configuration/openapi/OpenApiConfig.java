package project.task.manager.project_service.configuration.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author 4ndr33w
 * @version 1.0
 */
@Configuration
public class OpenApiConfig {
	
	private final static String CONTEXT_PATH = "/project-service";
	@Bean
	public OpenAPI openApi() {
		SecurityScheme jwtSchema = new SecurityScheme()
				.type(SecurityScheme.Type.HTTP)
				.scheme("bearer")
				.bearerFormat("JWT")
				.name("Authorization")
				.in(SecurityScheme.In.HEADER)
				.description("Bearer Token");
		
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearer");
		
		return new OpenAPI()
				.info(new Info()
						.title("Сервис управления проектами Task Manager")
						.contact(new Contact()
								.name("Andr33w")
								.email("andr33w@example.com"))
						.version("1.0")
						.description("Сервис управления проектами Task Manager"))
				.servers(List.of(
						new Server()
								.url("http://localhost:901" + CONTEXT_PATH) // ← ОСНОВНОЙ URL!
								.description("API Gateway (use this in browser)"),
						
						new Server()
								.url("http://localhost:903" + CONTEXT_PATH)
								.description("Direct access (localhost)"),
						
						new Server()
								.url("http://project-service:903" + CONTEXT_PATH)
								.description("Direct access (Docker network)")
				))
				.components(new Components()
						.addSecuritySchemes("bearer", jwtSchema))
				.addSecurityItem(securityRequirement);
	}
}
