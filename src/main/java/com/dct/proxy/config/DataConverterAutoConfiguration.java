package com.dct.proxy.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * Create beans to handle the conversion of values between objects and between objects and JSON
 * @author thoaidc
 */
@AutoConfiguration
public class DataConverterAutoConfiguration {
    private static final Logger log = LoggerFactory.getLogger(DataConverterAutoConfiguration.class);

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
        log.debug("[OBJECT_MAPPER_AUTO_CONFIG] - Auto configure default object mapper");
        return buildObjectMapper();
    }

    /**
     * Support for Java date and time API.
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

    public static ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Avoid errors when encountering undefined properties in JSON that are not present in the Java class being converted
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.disable(SerializationFeature.INDENT_OUTPUT);
        // Do not serialize (convert to JSON) fields with empty or null values
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        objectMapper.registerModule(new JavaTimeModule()); // To support Instant datatype of Java 8
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }
}
