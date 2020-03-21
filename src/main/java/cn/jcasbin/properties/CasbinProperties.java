package cn.jcasbin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(CasbinProperties.PREFIX)
public class CasbinProperties {
    public static final String PREFIX = "shiro-jcasbin";

    private Boolean watcher = true;

    private String watcherKey = "/casbin/watcher_request";

    private String model = "classpath:casbin/model_request.conf";

    private String ruleTable = "casbin_rule_request";
}
