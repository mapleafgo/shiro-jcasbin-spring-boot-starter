package cn.jcasbin.aop;

import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

import java.util.Collections;

public class CasbinAnnotationsAuthorizingMethodInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {
    public CasbinAnnotationsAuthorizingMethodInterceptor() {
        setMethodInterceptors(Collections.singletonList(new CasbinAnnotationMethodInterceptor(new SpringAnnotationResolver())));
    }
}
