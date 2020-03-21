package cn.jcasbin.aop;

import org.apache.shiro.aop.AnnotationResolver;
import org.apache.shiro.authz.aop.AuthorizingAnnotationMethodInterceptor;

public class CasbinAnnotationMethodInterceptor extends AuthorizingAnnotationMethodInterceptor {
    public CasbinAnnotationMethodInterceptor(AnnotationResolver resolver) {
        super(new CasbinAnnotationHandler(), resolver);
    }
}
