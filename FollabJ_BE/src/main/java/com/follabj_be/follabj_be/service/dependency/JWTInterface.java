package com.follabj_be.follabj_be.service.dependency;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface JWTInterface {
    public Map<String, String> getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
