package com.musalasoft.droneservice.config.intercepter;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Random;

@Component
@Slf4j
public class DroneRequestInterceptor implements HandlerInterceptor {
    public static final String TRACE_ID = "trace-id";
    private static final String START_TIME = "startTime";

    private static final Random RANDOM = new Random();

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        final String traceId = (request.getHeader(TRACE_ID) == null ?
                traceIDGenerator("DRN") :
                request.getHeader(TRACE_ID));

        MDC.put(TRACE_ID, traceId);
        request.setAttribute(START_TIME, Instant.now().toEpochMilli());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler, Exception ex) {
        long startTime = (Long) request.getAttribute(START_TIME);
        log.info("{}|{}ms", request.getRequestURI(), (Instant.now().toEpochMilli() - startTime));
        MDC.remove(TRACE_ID);
    }
    private static String traceIDGenerator(String serviceName) {
        long randomNum = generateRandom();
        return serviceName.toUpperCase().substring(0, 3) + randomNum;
    }

    private static long generateRandom() {
        int length = 12;
        char[] digits = new char[length];
        digits[0] = (char) (RANDOM.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (RANDOM.nextInt(10) + '0');
        }
        return Long.parseLong(new String(digits));
    }
}
