package com.louislee92.tools.config;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class SystemConfig implements CommandLineRunner {

    public static SystemConfig INST;

    @Value("${image.dir:./image-dir}")
    public String IMG_DIR;

    @Value("${image.url.prefix:http://localhost/image-bed}")
    public String IMG_URL_PREFIX;

    @Value("${image.code:123456}")
    public String IMG_CODE;

    @Override
    public void run(String... args) throws Exception {
        INST = SpringUtil.getBean(SystemConfig.class);
        log.info("image dir = {}", INST.IMG_DIR);
        log.info("image url prefix = {}", INST.IMG_URL_PREFIX);
        log.info("image code = {}", INST.IMG_CODE);
    }
}
