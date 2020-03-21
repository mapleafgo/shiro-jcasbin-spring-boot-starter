package cn.jcasbin.aop;

import cn.jcasbin.annotation.RequiresCasbin;
import cn.jcasbin.subject.CasbinSubject;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

public class CasbinAnnotationHandler extends AuthorizingAnnotationHandler {
    public CasbinAnnotationHandler() {
        super(RequiresCasbin.class);
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        if (!(a instanceof RequiresCasbin)) return;
        CasbinSubject subject = (CasbinSubject) getSubject();
        HttpServletRequest request = (HttpServletRequest) subject.getServletRequest();
        String path = request.getServletPath();
        String method = request.getMethod();
        if (!subject.enforce(String.valueOf(subject.getPrincipal()), path, method))
            throw new UnauthorizedException("权限不足");
    }
}
