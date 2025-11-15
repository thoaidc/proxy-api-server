package com.dct.proxy.common;

import com.dct.proxy.constants.BaseExceptionConstants;
import com.dct.proxy.dto.response.BaseResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

import java.util.Locale;

/**
 * Provide common processing functions for the entire application
 * @author thoaidc
 */
@SuppressWarnings("unused")
public class MessageTranslationUtils {
    private static final Logger log = LoggerFactory.getLogger(MessageTranslationUtils.class);
    private final MessageSource messageSource; // Spring boot service for I18n

    public MessageTranslationUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Get the internationalized content (I18n) of the key based on the current locale in the application
     * @param messageKey The code corresponding to the internationalized content to be retrieved
     * @param args Arguments passed to use dynamic values for message
     * @return Value of {@link BaseExceptionConstants#TRANSLATE_NOT_FOUND} if not found message I18n
     */
    public String getMessageI18n(String messageKey, Object ...args) {
        // The value of Locale represents the current region, here used to determine the language type to translate
        Locale locale = LocaleContextHolder.getLocale();
        return getMessageI18n(locale, messageKey, BaseExceptionConstants.TRANSLATE_NOT_FOUND, args);
    }

    /**
     * Get the internationalized content (I18n) of the key based on the current locale in the application
     * @param messageKey The code corresponding to the internationalized content to be retrieved
     * @param defaultMessage Default message to fallback when not found key i18n
     * @param args Arguments passed to use dynamic values for message
     * @return Value of defaultMessage if not found message I18n
     */
    public String getMessageI18n(String messageKey, String defaultMessage, Object ...args) {
        // The value of Locale represents the current region, here used to determine the language type to translate
        Locale locale = LocaleContextHolder.getLocale();
        return getMessageI18n(locale, messageKey, defaultMessage, args);
    }

    public String getMessageI18n(Locale locale, String messageKey, String defaultMessage, Object ...args) {
        log.debug("[TRANSLATE_MESSAGE] - message key: '{}'", messageKey);
        String message = messageSource.getMessage(messageKey, args, null, locale);

        if (StringUtils.hasText(message))
            return message;

        return messageSource.getMessage(defaultMessage, args, defaultMessage, locale);
    }

    /**
     * Set the translated message I18n for the response body. Can be used by ResponseFilter (Self-Defined)
     * @param responseDTO response before sending to client
     * @return response after it has been translated and is ready to be sent to the client
     */
    public BaseResponseDTO setResponseMessageI18n(BaseResponseDTO responseDTO) {
        String messageKey = responseDTO.getMessage();

        if (StringUtils.hasText(messageKey)) {
            String messageTranslated = getMessageI18n(messageKey, responseDTO.getMessage());
            responseDTO.setMessage(messageTranslated);
        }

        return responseDTO;
    }
}
