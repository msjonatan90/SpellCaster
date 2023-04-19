package com.mpc.spellcaster.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
@EnableWebMvc
class WebConfig implements WebMvcConfigurer {
    // Add any necessary configuration for Spring MVC here
}