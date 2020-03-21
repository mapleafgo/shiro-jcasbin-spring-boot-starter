# SpringBoot Web Shiro Casbin

## Shiro integrates Casbin to replace Shiro's own authorization role verification

# English | [中文](./README_CN.md)

## Add
Currently not published to maven, please download the jar package to use

## Use
Just add the `@RequiresCasbin` annotation to the controller method, and the request will verify the request address permissions.
The three elements here are: Shiro's `principals` value, requested path address `ServletPath`, requested method type `method`
```java
    @RequiresCasbin
    @GetMapping
    public List<Menu> getRoot() {
        return menuService.getRoot();
    }
```

## Configuration
The following values ​​are default values
```yaml
shiro-jcasbin:
  enabled: true // 开启jcasbin
  model: classpath:casbin/model_request.conf // 模型路径
  rule-table: casbin_rule_request // policy存储表名
  watcher: true // 开启watcher，需要jetcd支持
  watcher-key: /casbin/watcher_request // watcher使用的键名
```
