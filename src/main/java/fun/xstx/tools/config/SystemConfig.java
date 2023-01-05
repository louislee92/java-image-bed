package fun.xstx.tools.config;

import cn.hutool.extra.spring.SpringUtil;
import lombok.Getter;
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

    @Value("${image.url.prefix:http://xstx.fun/image-bed}")
    public String IMA_URL_PREFIX;

    @Override
    public void run(String... args) throws Exception {
        INST = SpringUtil.getBean(SystemConfig.class);
        log.info("image dir = {}", INST.IMG_DIR);
    }
}
