package dev.gym.service.converter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final List<Converter<?, ?>> converterList;

    public WebConfig(List<Converter<?, ?>> converterList) {
        this.converterList = converterList;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        converterList.forEach(registry::addConverter);
    }

}
