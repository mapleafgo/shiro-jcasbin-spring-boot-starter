package cn.jcasbin.subject;

import lombok.Getter;
import lombok.NonNull;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.casbin.jcasbin.main.Enforcer;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.List;

/**
 * Casbin重新Subject
 *
 * @author 慕枫
 */
public class CasbinSubject extends WebDelegatingSubject {
    @Getter
    private final Enforcer enforcer;

    public CasbinSubject(PrincipalCollection principals, boolean authenticated, String host, Session session,
                         ServletRequest request, ServletResponse response, SecurityManager securityManager, Enforcer enforcer) {
        super(principals, authenticated, host, session, request, response, securityManager);
        this.enforcer = enforcer;
    }

    public boolean enforce(@NonNull Object... rvals) throws AuthorizationException {
        assertAuthzCheckPossible();
        return enforcer.enforce(rvals);
    }

    @Override
    public boolean hasRole(String roleIdentifier) {
        return hasPrincipals() && enforcer.hasRoleForUser(principals.toString(), roleIdentifier);
    }

    public List<List<String>> getPermissionsForUser() {
        if (hasPrincipals())
            return enforcer.getPermissionsForUser(principals.toString());
        return null;
    }

    public List<String> getRolesForUser() {
        if (hasPrincipals())
            return enforcer.getRolesForUser(principals.toString());
        return null;
    }
}
