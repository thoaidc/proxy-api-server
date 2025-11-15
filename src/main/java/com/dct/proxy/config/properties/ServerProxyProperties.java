package com.dct.proxy.config.properties;

import com.dct.proxy.constants.BasePropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Contains configuration properties related to remote proxy server config<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link BasePropertiesConstants#SERVER_PROXY_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = BasePropertiesConstants.SERVER_PROXY_CONFIG)
public class ServerProxyProperties {
    private String host;
    private String proxyApi;

    public String getPharmaIntegrationHost() {
        return pharmaIntegrationHost;
    }

    public void setPharmaIntegrationHost(String pharmaIntegrationHost) {
        this.pharmaIntegrationHost = pharmaIntegrationHost;
    }

    private String pharmaIntegrationHost;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getProxyApi() {
        return proxyApi;
    }

    public void setProxyApi(String proxyApi) {
        this.proxyApi = proxyApi;
    }
}
