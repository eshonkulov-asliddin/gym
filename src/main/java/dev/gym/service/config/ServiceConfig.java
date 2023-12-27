package dev.gym.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.List;

@Configuration
@ComponentScan(basePackages = {"dev.gym.service"})
public class ServiceConfig {

    private final List<Converter<?, ?>> converterList;

    public ServiceConfig(List<Converter<?, ?>> converterList) {
        this.converterList = converterList;
    }

    @Bean(name = "customConvertionService")
    public ConversionService conversionService() {
        DefaultConversionService defaultConversionService = new DefaultConversionService();
        converterList.forEach(defaultConversionService::addConverter);
        return defaultConversionService;
    }
}
