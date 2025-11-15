package com.dct.proxy.config.properties;

import com.dct.proxy.constants.ActivateStatus;
import com.dct.proxy.constants.BasePropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Contains configuration properties related to Http Client config<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link BasePropertiesConstants#HTTP_CLIENT_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = BasePropertiesConstants.HTTP_CLIENT_CONFIG)
public class HttpClientProps {
    private ActivateStatus restTemplate = ActivateStatus.DISABLED;
    private int defaultConnectTimeout;
    private int defaultReadTimeout;
    private CircuitBreakerProps circuitBreaker;

    public ActivateStatus getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(ActivateStatus restTemplate) {
        this.restTemplate = restTemplate;
    }

    public int getDefaultConnectTimeout() {
        return defaultConnectTimeout;
    }

    public void setDefaultConnectTimeout(int defaultConnectTimeout) {
        this.defaultConnectTimeout = defaultConnectTimeout;
    }

    public int getDefaultReadTimeout() {
        return defaultReadTimeout;
    }

    public void setDefaultReadTimeout(int defaultReadTimeout) {
        this.defaultReadTimeout = defaultReadTimeout;
    }

    public CircuitBreakerProps getCircuitBreaker() {
        return circuitBreaker;
    }

    public void setCircuitBreaker(CircuitBreakerProps circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
    }
}
