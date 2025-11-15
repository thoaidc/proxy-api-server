package com.dct.proxy.config.properties;

import com.dct.proxy.constants.ActivateStatus;
import com.dct.proxy.constants.BasePropertiesConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Contains configuration properties related to Circuit Breaker config<p>
 * When the application starts, Spring will automatically create an instance of this class
 * and load the values from configuration files like application.properties or application.yml <p>
 *
 * {@link ConfigurationProperties} helps Spring map config properties to fields,
 * instead of using @{@link Value} for each property individually <p>
 *
 * {@link BasePropertiesConstants#CIRCUIT_BREAKER_CONFIG} decides the prefix for the configurations that will be mapped <p>
 *
 * See <a href="">application-dev.yml</a> for detail
 *
 * @author thoaidc
 */
@SuppressWarnings("unused")
@ConfigurationProperties(prefix = BasePropertiesConstants.CIRCUIT_BREAKER_CONFIG)
public class CircuitBreakerProps {
    private ActivateStatus activate = ActivateStatus.DISABLED;
    private float failureRateThreshold;
    private int minimumNumberOfCalls;
    private int slidingWindowSize;
    private long waitDurationInOpenState;
    private long slowCallDurationThreshold;
    private long slowCallRateThreshold;
    private int permittedNumberOfCallsInHalfOpenState;
    private boolean automaticTransitionFromOpenToHalfOpenEnabled;
    private int connectTimeout;
    private int readTimeout;
    private Resilience4jRetryProps retry;
    private Resilience4jTimeLimiterProps timeLimiter;

    public ActivateStatus getActivate() {
        return activate;
    }

    public void setActivate(ActivateStatus activate) {
        this.activate = activate;
    }

    public float getFailureRateThreshold() {
        return failureRateThreshold;
    }

    public void setFailureRateThreshold(float failureRateThreshold) {
        this.failureRateThreshold = failureRateThreshold;
    }

    public int getMinimumNumberOfCalls() {
        return minimumNumberOfCalls;
    }

    public void setMinimumNumberOfCalls(int minimumNumberOfCalls) {
        this.minimumNumberOfCalls = minimumNumberOfCalls;
    }

    public int getSlidingWindowSize() {
        return slidingWindowSize;
    }

    public void setSlidingWindowSize(int slidingWindowSize) {
        this.slidingWindowSize = slidingWindowSize;
    }

    public long getWaitDurationInOpenState() {
        return waitDurationInOpenState;
    }

    public void setWaitDurationInOpenState(long waitDurationInOpenState) {
        this.waitDurationInOpenState = waitDurationInOpenState;
    }

    public long getSlowCallDurationThreshold() {
        return slowCallDurationThreshold;
    }

    public void setSlowCallDurationThreshold(long slowCallDurationThreshold) {
        this.slowCallDurationThreshold = slowCallDurationThreshold;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getSlowCallRateThreshold() {
        return slowCallRateThreshold;
    }

    public void setSlowCallRateThreshold(long slowCallRateThreshold) {
        this.slowCallRateThreshold = slowCallRateThreshold;
    }

    public int getPermittedNumberOfCallsInHalfOpenState() {
        return permittedNumberOfCallsInHalfOpenState;
    }

    public void setPermittedNumberOfCallsInHalfOpenState(int permittedNumberOfCallsInHalfOpenState) {
        this.permittedNumberOfCallsInHalfOpenState = permittedNumberOfCallsInHalfOpenState;
    }

    public boolean isAutomaticTransitionFromOpenToHalfOpenEnabled() {
        return automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public void setAutomaticTransitionFromOpenToHalfOpenEnabled(boolean automaticTransitionFromOpenToHalfOpenEnabled) {
        this.automaticTransitionFromOpenToHalfOpenEnabled = automaticTransitionFromOpenToHalfOpenEnabled;
    }

    public Resilience4jRetryProps getRetry() {
        return retry;
    }

    public void setRetry(Resilience4jRetryProps retry) {
        this.retry = retry;
    }

    public Resilience4jTimeLimiterProps getTimeLimiter() {
        return timeLimiter;
    }

    public void setTimeLimiter(Resilience4jTimeLimiterProps timeLimiter) {
        this.timeLimiter = timeLimiter;
    }
}
