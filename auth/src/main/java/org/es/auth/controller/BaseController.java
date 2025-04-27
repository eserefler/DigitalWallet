package org.es.auth.controller;


import org.es.auth.jwt.JwtModel;

public abstract class BaseController {

    private static final ThreadLocal<JwtModel> jwtModelThreadLocal = new ThreadLocal<>();

    public void setJwtModel(JwtModel jwtModel) {
        jwtModelThreadLocal.set(jwtModel);
    }

    public JwtModel getJwtModel() {
        return jwtModelThreadLocal.get();
    }

    public void clearJwtModel() {
        jwtModelThreadLocal.remove();
    }
}

