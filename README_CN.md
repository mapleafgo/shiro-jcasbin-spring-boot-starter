# SpringBoot Web Shiro Casbin

## Shiro整合Casbin，替换Shiro自身的权限角色验证

# [English](./README.md) | 中文

## 安装
当前暂未发布到maven，使用请下载jar包

## 使用
1. 下载[model_request.conf](./model/model_request.conf)放在`classpath:casbin/model_request.conf`
2. 在控制器方法上加上`@RequiresCasbin`注解，该请求便会去验证该请求地址权限。\
这里的三元素分别是： Shiro的`principals`值、请求的path地址`ServletPath`、请求的方法类型`method`
    ```java
    @RequiresCasbin
    @GetMapping("menu/root")
    public List<Menu> getRoot() {
        return menuService.getRoot();
    }
    ```
3. 为请求接口分配权限
    ```java
    CasbinSubject subject = (CasbinSubject) SecurityUtils.getSubject();
    Enforcer enforcer = subject.getEnforcer(); // 获取到enforcer

    String path = "/menu/root";
    String method = "GET";
    enforcer.addPermissionForUser(role, path, method); // 当然根据rbac的原则，第一个参数可以是role也可以是user
    enforcer.addRoleForUser(user, role); // 给user赋予role
    ```

## 配置
下面的值为默认值
```yaml
shiro-jcasbin:
  enabled: true // 开启jcasbin
  model: classpath:casbin/model_request.conf // 模型路径
  rule-table: casbin_rule_request // policy存储表名
  watcher: true // 开启watcher，需要jetcd支持
  watcher-key: /casbin/watcher_request // watcher使用的键名
```
