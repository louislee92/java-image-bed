package fun.xstx.tools;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 图床APP
 */
@MapperScan("fun.xstx.tools.mapper")
@SpringBootApplication
@ServletComponentScan
@EnableSpringUtil
@EnableAspectJAutoProxy
public class ImageBedApp {

    public static void main(String[] args) {
        SpringApplication.run(ImageBedApp.class, args);
    }
}
