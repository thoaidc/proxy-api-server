package com.dct.proxy.config.properties;

import com.dct.proxy.constants.BaseCommonConstants;
import com.dct.proxy.constants.BasePropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link BasePropertiesConstants#I18N_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = BasePropertiesConstants.I18N_CONFIG)
public class I18nProps {
    private String[] baseNames = BaseCommonConstants.DEFAULT_MESSAGE_SOURCE_BASENAME;
    private String encoding = BaseCommonConstants.DEFAULT_MESSAGE_SOURCE_ENCODING;

    public String[] getBaseNames() {
        return baseNames;
    }

    public void setBaseNames(String[] baseNames) {
        this.baseNames = baseNames;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
}
