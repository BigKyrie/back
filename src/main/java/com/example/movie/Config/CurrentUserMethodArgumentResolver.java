package com.example.movie.Config;
import com.example.movie.Annotation.CurrentUser;
import com.example.movie.Entity.Cinema_Admin;
import com.example.movie.Entity.User;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;


public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(Cinema_Admin.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Cinema_Admin cinema_admin = (Cinema_Admin) webRequest.getAttribute("currentUser", RequestAttributes.SCOPE_REQUEST);
        if (cinema_admin != null) {
            return cinema_admin;
        }
        throw new MissingServletRequestPartException("currentUser");
    }

}