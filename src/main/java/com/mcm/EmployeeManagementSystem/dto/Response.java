package com.mcm.EmployeeManagementSystem.dto;

import com.mcm.EmployeeManagementSystem.constant.Constant;
import com.mcm.EmployeeManagementSystem.validator.ValidationReport;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Response {
    private ValidationReport report;
    private Map<String, Object> payload;

    public Response(ValidationReport report, Object object) {
        this.report = report;
        payload = new HashMap<>();
        if (isValid()) {
            payload.put(getObjectClassType(object), object);
        }
    }

    public Response(ValidationReport report, List<Object> objects) {
        this.report = report;
        payload = new HashMap<>();
        if (isValid()) {
            for (Object object : objects) {
                payload.put(getObjectClassType(object), object);
            }
        }
    }

    private boolean isValid() {
        return report.getErrorMessages().isEmpty();
    }

    private String getObjectClassType(Object object) {
        String payloadMessage;
        if (object == null || object.getClass() == Long.class) {
            payloadMessage = Constant.ENTITY_ID;
        } else {
            payloadMessage = object.getClass().toString();
            int index = payloadMessage.lastIndexOf(".");
            payloadMessage = payloadMessage.substring(index + 1);
        }

        return payloadMessage;
    }
}
