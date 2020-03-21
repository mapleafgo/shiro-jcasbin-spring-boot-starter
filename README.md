# SpringBoot Web Shiro Casbin

## Shiro integrates Casbin to replace Shiro's own authorization role verification

# English | [中文](./README_CN.md)

## Add
Currently not published to maven, please download the jar package to use

## Use
1. Download [model_request.conf](./model/model_request.conf) put into `classpath:casbin/model_request.conf`
2. Just add the `@RequiresCasbin` annotation to the controller method, and the request will verify the request address permissions.
The three elements here are: Shiro's `principals` value, requested path address `ServletPath`, requested method type `method`
    ```java
        @RequiresCasbin
        @GetMapping
        public List<Menu> getRoot() {
            return menuService.getRoot();
        }
    ```
3. Assigning permissions to the request interface
    ```java
    CasbinSubject subject = (CasbinSubject) SecurityUtils.getSubject();
    Enforcer enforcer = subject.getEnforcer(); // get enforcer

    String path = "/menu/root";
    String method = "GET";
    enforcer.addPermissionForUser(role, path, method); // Of course, according to the principle of rbac, the first parameter can be role or user
    enforcer.addRoleForUser(user, role); // Assign role to user
    ```

## Configuration
The following values ​​are default values
```yaml
shiro-jcasbin:
  enabled: true // Enabled jcasbin
  model: classpath:casbin/model_request.conf // madel path
  rule-table: casbin_rule_request // policy table name
  watcher: true // Enabled watcher，Need jetcd support
  watcher-key: /casbin/watcher_request // Key name used by watcher
```
