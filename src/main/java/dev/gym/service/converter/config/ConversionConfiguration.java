package dev.gym.service.converter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.support.GenericConversionService;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ConversionConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private final Set<Converter<?, ?>> converters;
    private final Set<ConverterFactory<?, ?>> converterFactories;
    private final ConversionService conversionService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (conversionService instanceof GenericConversionService gcs) {
            converters.forEach(gcs::addConverter);
            converterFactories.forEach(gcs::addConverterFactory);
        }
    }
}
