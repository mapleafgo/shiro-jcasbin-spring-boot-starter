package cn.jcasbin.advisor;

import cn.jcasbin.annotation.RequiresCasbin;
import cn.jcasbin.aop.CasbinAnnotationsAuthorizingMethodInterceptor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.Objects;

public class CasbinAdvisor extends StaticMethodMatcherPointcutAdvisor {
    public CasbinAdvisor() {
        setAdvice(new CasbinAnnotationsAuthorizingMethodInterceptor());
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        Method m = method;

        if (checkAnnotation(m)) {
            return true;
        }

        try {
            m = targetClass.getMethod(m.getName(), m.getParameterTypes());
            return checkAnnotation(m) || checkAnnotation(targetClass);
        } catch (NoSuchMethodException ignored) {
        }
        return false;
    }

    private <T extends AnnotatedElement> boolean checkAnnotation(T m) {
        return Objects.nonNull(AnnotationUtils.findAnnotation(m, RequiresCasbin.class));
    }
}
