package com.westflow.seeds_manager_api.infrastructure.security;

import com.westflow.seeds_manager_api.api.config.CurrentUser;
import com.westflow.seeds_manager_api.application.usecase.user.FindUserByEmailUseCase;
import com.westflow.seeds_manager_api.domain.model.User;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final FindUserByEmailUseCase findUserByEmailUseCase;

    public CurrentUserArgumentResolver(FindUserByEmailUseCase findUserByEmailUseCase) {
        this.findUserByEmailUseCase = findUserByEmailUseCase;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
                && parameter.getParameterType().equals(User.class);
    }

    @Override
    public User resolveArgument(@Nullable MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @Nullable NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmailUseCase.execute(email);
    }

}
