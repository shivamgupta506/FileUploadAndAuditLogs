package com.mindprove;

import java.time.Instant;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private ApiLogsRepository apiLogsRepository;
    
    private static final String START_TIME = "startTime";
    private static Integer apiLogsId;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

    	request.setAttribute(START_TIME, System.currentTimeMillis());
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ipAddress = request.getRemoteAddr();
        Instant timestamp = Instant.now();

        ApiLogs logs = new ApiLogs(method, uri, ipAddress, timestamp);
        
        ApiLogs savedlogs = apiLogsRepository.save(logs);
        apiLogsId = savedlogs.getId();

        return true;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
        
        int status = response.getStatus();
        System.out.println("Response status: " + status);
        ApiLogs logs = apiLogsRepository.findById(apiLogsId).get();
        if(Objects.nonNull(logs) && logs.getId()!=null) {
        	logs.setStatus(status);
        	apiLogsRepository.save(logs);
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) throws Exception {

        long startTime = (Long) request.getAttribute(START_TIME);
        long executionTime = System.currentTimeMillis() - startTime;

        System.out.println("Request completed in " + executionTime + " ms");

        ApiLogs logs = apiLogsRepository.findById(apiLogsId).get();
        if(Objects.nonNull(logs) && logs.getId()!=null) {
        	logs.setDurationMs(executionTime);
        	apiLogsRepository.save(logs);
        }
        
    }
}
