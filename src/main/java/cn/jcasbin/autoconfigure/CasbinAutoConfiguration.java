package cn.jcasbin.autoconfigure;

import cn.hutool.core.io.IoUtil;
import cn.jcasbin.adapter.HutoolDBAdapter;
import cn.jcasbin.advisor.CasbinAdvisor;
import cn.jcasbin.properties.CasbinProperties;
import cn.jcasbin.subject.CasbinDefaultWebSubjectFactory;
import cn.jcasbin.watcher.EtcdWatcher;
import io.etcd.jetcd.Client;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.boot.autoconfigure.ShiroAutoConfiguration;
import org.apache.shiro.spring.config.web.autoconfigure.ShiroWebAutoConfiguration;
import org.casbin.jcasbin.main.Enforcer;
import org.casbin.jcasbin.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.charset.Charset;

@Slf4j
@Configuration
@EnableConfigurationProperties(CasbinProperties.class)
@AutoConfigureBefore({ShiroWebAutoConfiguration.class, ShiroAutoConfiguration.class})
@ConditionalOnProperty(prefix = CasbinProperties.PREFIX, value = "enabled", matchIfMissing = true)
public class CasbinAutoConfiguration {
    @Autowired
    private CasbinProperties properties;

    @Bean
    @SneakyThrows
    public SubjectFactory subjectFactory(DataSource dataSource, Client client) {
        Model model = new Model();
        String modelPath = new ClassPathResource(properties.getModel()).exists()
            ? properties.getModel() : "classpath:conf/model_request.conf";
        @Cleanup InputStream is = ResourceUtils.getURL(modelPath).openStream();
        model.loadModelFromText(IoUtil.read(is, Charset.defaultCharset()));
        Enforcer enforcer = new Enforcer(model, new HutoolDBAdapter(dataSource, properties.getRuleTable()));
        EtcdWatcher watcher = new EtcdWatcher(client, properties.getWatcherKey());
        enforcer.setWatcher(watcher);
        if (properties.getWatcher()) watcher.startWatch();
        return new CasbinDefaultWebSubjectFactory(enforcer);
    }

    @Bean
    public CasbinAdvisor casbinAdvisor() {
        return new CasbinAdvisor();
    }
}
