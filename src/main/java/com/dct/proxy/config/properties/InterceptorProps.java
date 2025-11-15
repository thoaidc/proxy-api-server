package com.dct.proxy.config.properties;

import com.dct.proxy.constants.ActivateStatus;
import com.dct.proxy.constants.BasePropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link BasePropertiesConstants#INTERCEPTOR_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = BasePropertiesConstants.INTERCEPTOR_CONFIG)
public class InterceptorProps {
    private ActivateStatus activate = ActivateStatus.DISABLED;
    private List<InterceptorConfig> chain = new ArrayList<>();

    public ActivateStatus getActivate() {
        return activate;
    }

    public void setActivate(ActivateStatus activate) {
        this.activate = activate;
    }

    public List<InterceptorConfig> getChain() {
        return Optional.ofNullable(chain).orElse(Collections.emptyList());
    }

    public void setChain(List<InterceptorConfig> chain) {
        this.chain = chain;
    }

    public static class InterceptorConfig {
        private Class<?> name;
        private String[] includedPatterns;
        private String[] excludedPatterns;

        public Class<?> getName() {
            return name;
        }

        public void setName(Class<?> name) {
            this.name = name;
        }

        public String[] getIncludedPatterns() {
            return includedPatterns;
        }

        public void setIncludedPatterns(String[] includedPatterns) {
            this.includedPatterns = includedPatterns;
        }

        public String[] getExcludedPatterns() {
            return excludedPatterns;
        }

        public void setExcludedPatterns(String[] excludedPatterns) {
            this.excludedPatterns = excludedPatterns;
        }
    }
}
