package com.generation.clicksolucao.configuration;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

@Configuration
public class SwaggerConfig {

	@Bean
	OpenAPI springBlogPessoalOpenAPI() {
		return (new OpenAPI().info(new Info().title("Click Solução").description(
				"Click Solução é uma plataforma e-commerce sobre comercialização de serviços, contribuindo para o aumento da renda das pessoas participantes e autonomia das pessoas impactadas, promovendo o empreendedorismo sustentável.")
				.version("v0.0.1")
				.license(new License()
						.name("Click Solução")
						.url("https://github.com/Projeto-inter/click-solucao"))
				.contact(new Contact()
						.name("Rafael Gouveia")
						.url("https://github.com/RaffaGouveia/click-solucao")
						.email("raffagouveiabarros@gmail.com")
						.name("Juliana Lopes")
						.url("https://github.com/julianalopesco/click-solucao")
						.email("julianalopesmf@gmail.com")
						.name("Daiane Bittencourt")
						.url("https://github.com/Daianebittencourt/Projeto_Click_Solucao")
						.email("daibittencourt2000@gmail.com")
						.name("Flávia Maria")
						.url("https://github.com/flavws/click-solucao")
						.email("persona.fmaria@gmail.com")
						.name("Dario Pereira").url("https://github.com/Dario-alabakiz/click-solucao")
						.email("dariowsp23@gmail.com"))))
				.externalDocs(
						new ExternalDocumentation().description("Github").url("https://github.com/Projeto-inter"));
	}

	@Bean
	public OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {

		return openApi -> {
			openApi.getPaths().values().forEach(pathItem -> pathItem.readOperations().forEach(operation -> {

				ApiResponses apiResponses = operation.getResponses();

				apiResponses.addApiResponse("200", createApiResponse("Sucesso!"));
				apiResponses.addApiResponse("201", createApiResponse("Objeto persistido!"));
				apiResponses.addApiResponse("204", createApiResponse("Objeto excluído!"));
				apiResponses.addApiResponse("400", createApiResponse("Erro na requisição!"));
				apiResponses.addApiResponse("401", createApiResponse("Acesso não autorizado!"));
				apiResponses.addApiResponse("403", createApiResponse("Acesso proibido!"));
				apiResponses.addApiResponse("404", createApiResponse("Objeto não encontrado!"));
				apiResponses.addApiResponse("500", createApiResponse("Erro na aplicação!"));

			}));
		};
	}

	private ApiResponse createApiResponse(String message) {

		return new ApiResponse().description(message);

	}
}