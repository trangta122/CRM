package com.intern.crm;


import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition (
		info = @Info (
				title = "CRM APIs document",
				version = "1.0",
				description = "CRM B2B - Batch15 - DG8Ext"
		)
)

@SecurityScheme(
		name = "Authorization",
		scheme = "Bearer",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)
public class CrmApplication {

	public static void main(String[] args) {

		SpringApplication.run(CrmApplication.class, args);

	}


}
