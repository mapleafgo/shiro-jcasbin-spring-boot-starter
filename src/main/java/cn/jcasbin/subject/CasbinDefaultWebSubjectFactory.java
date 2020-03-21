package cn.jcasbin.subject;

import org.apache.shiro.mgt.DefaultSubjectFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubjectContext;
import org.casbin.jcasbin.main.Enforcer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CasbinDefaultWebSubjectFactory extends DefaultSubjectFactory {
    private final Enforcer enforcer;

    public CasbinDefaultWebSubjectFactory(Enforcer enforcer) {
        super();
        this.enforcer = enforcer;
    }

    @Override
    public Subject createSubject(SubjectContext context) {
        boolean isNotBasedOnWebSubject = context.getSubject() != null && !(context.getSubject() instanceof WebSubject);
        if (!(context instanceof WebSubjectContext) || isNotBasedOnWebSubject) {
            return super.createSubject(context);
        }
        WebSubjectContext wsc = (WebSubjectContext) context;
        SecurityManager securityManager = wsc.resolveSecurityManager();
        Session session = wsc.resolveSession();
        PrincipalCollection principals = wsc.resolvePrincipals();
        boolean authenticated = wsc.resolveAuthenticated();
        String host = wsc.resolveHost();
        ServletRequest request = wsc.resolveServletRequest();
        ServletResponse response = wsc.resolveServletResponse();

        return new CasbinSubject(principals, authenticated, host, session,
            request, response, securityManager, enforcer);
    }
}
