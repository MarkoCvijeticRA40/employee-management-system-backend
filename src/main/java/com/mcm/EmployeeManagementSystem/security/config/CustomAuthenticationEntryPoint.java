package com.mcm.EmployeeManagementSystem.security.config;

import com.mcm.EmployeeManagementSystem.usecase.log.ShouldWarnAdminUseCase;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tomcat.util.buf.MessageBytes;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;
    private final ShouldWarnAdminUseCase shouldWarnAdminUseCase;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Logger logger = LogManager.getLogger(SecurityConfig.class);
        String authorizationHeader = request.getHeader("Authorization");
        String username = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }

        Object a = null;
        try {
            a = findCoyoteRequest(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Field coyoteRequest = null;
        try {
            coyoteRequest = a.getClass().getDeclaredField("coyoteRequest");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        coyoteRequest.setAccessible(true);
        Object b = null;
        try {
            b = coyoteRequest.get(a);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Field uriMB = null;
        try {
            uriMB = b.getClass().getDeclaredField("uriMB");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        uriMB.setAccessible(true);
        MessageBytes c = null;
        try {
            c = (MessageBytes) uriMB.get(b);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(c.getString());

        logger.warn("Unauthorized access attempt on api: " + c + " for user: " + username);
        shouldWarnAdminUseCase.check("WARN ", "LoggerController", "Unauthorized access attempt on api: " + request.getRequestURI() + " for user: " + username);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("FORBIDDEN");
    }

    private Class getClassByName(Class classObject, String name) {
        Map<Class, List<Field>> fieldMap = new HashMap<>();
        Class returnClass = null;
        Class tempClass = classObject;
        while (tempClass != null) {
            fieldMap.put(tempClass, Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass();
        }

        for (Map.Entry<Class, List<Field>> entry : fieldMap.entrySet()) {
            for (Field f : entry.getValue()) {
                if (f.getName().equals(name)) {
                    returnClass = entry.getKey();
                    break;
                }
            }
        }
        return returnClass;
    }

    private Object findCoyoteRequest(Object request) throws Exception {
        Class a = getClassByName(request.getClass(), "request");
        Field request1 = a.getDeclaredField("request");
        request1.setAccessible(true);
        Object b = request1.get(request);
        if (getClassByName(b.getClass(), "coyoteRequest") == null) {
            return findCoyoteRequest(b);
        } else {
            return b;
        }
    }
}
