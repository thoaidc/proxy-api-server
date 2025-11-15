package com.dct.proxy.constants;

/**
 * Contains the prefixes for the config property files <p>
 * Refer to these files in the <a href="">com/dct/model/config/properties</a> directory for more details
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
public interface BasePropertiesConstants {

    String DATASOURCE_CONFIG = "spring.datasource";
    String HIKARI_CONFIG = "spring.datasource.hikari";
    String HIKARI_DATASOURCE_CONFIG = "spring.datasource.hikari.data-source-properties";
    String ENABLED_DATASOURCE = "app.datasource";
    String ENABLED_AUDITING = "app.jpa-auditing";

    String I18N_CONFIG = "app.i18n";
    String SOCKET_CONFIG = "app.socket";
    String ENABLED_SOCKET = "app.socket.activate";

    String INTERCEPTOR_CONFIG = "app.interceptors";
    String ENABLED_INTERCEPTOR_CONFIG = "app.interceptors.activate";

    String RESOURCE_CONFIG = "app.resources";
    String ENABLED_RESOURCE = "app.resources.activate";

    String CORS_CONFIG = "app.cors";
    String ENABLED_CORS = "app.cors.activate";

    String REDIS_CONFIG = "app.redis";
    String ENABLED_REDIS = "app.redis.activate";

    String RABBIT_MQ_CONFIG = "app.rabbitmq";
    String ENABLED_RABBIT_MQ = "app.rabbitmq.activate";

    String RATE_LIMIT_CONFIG = "app.rate-limiter";
    String ENABLED_RATE_LIMIT = "app.rate-limiter.activate";

    String SECURITY_CONFIG = "app.security";
    String AUTHENTICATION_TYPE = "app.security.authentication-type";
    String SECURITY_OAUTH2_CONFIG = "app.security.oauth2";
    String ENABLED_OAUTH2 = "app.security.oauth2.activate";

    String HTTP_CLIENT_CONFIG = "app.http-client";
    String CIRCUIT_BREAKER_CONFIG = "app.http-client.circuit-breaker";
    String CIRCUIT_BREAKER_RETRY_CONFIG = "app.http-client.circuit-breaker.retry";
    String CIRCUIT_BREAKER_TIME_LIMITER_CONFIG = "app.http-client.circuit-breaker.time-limiter";

    String ENABLED_REST_TEMPLATE = "app.http-client.rest-template";
    String ENABLED_CIRCUIT_BREAKER_CONFIG = "app.http-client.circuit-breaker.activate";
    String ENABLED_CIRCUIT_BREAKER_RETRY_CONFIG = "app.http-client.circuit-breaker.retry.activate";
    String ENABLED_CIRCUIT_BREAKER_TIME_LIMITER_CONFIG = "app.http-client.circuit-breaker.time-limiter.activate";
}
