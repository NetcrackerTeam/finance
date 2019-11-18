package com.netcracker.models;

import java.math.BigInteger;

public class Template {
    private BigInteger templateId;
    private  String name;
    private  String message;

    public Template(BigInteger templateId, String name, String message) {
        this.templateId = templateId;
        this.name = name;
        this.message = message;
    }

    public Template(BigInteger templateId, String name) {
        this.templateId = templateId;
        this.name = name;
    }

    public BigInteger getTemplateId() {
        return templateId;
    }

    public void setTemplateId(BigInteger templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
