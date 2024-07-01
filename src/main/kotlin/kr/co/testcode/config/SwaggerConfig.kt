package kr.co.testcode.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Value("\${springdoc.app-url}")
    val url: String? = null

    @Value("\${springdoc.app-description}")
    val description: String? = null

    @Value("\${springdoc.app-title}")
    val title: String? = null

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().components(Components()).info(apiInfo())
    }

    private fun apiInfo(): Info {
        return Info().title(title).description(description)
    }
}