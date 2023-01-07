package com.louislee92.tools;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * 代码生成器
 */
public class CodeGenerator {


    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig
                .Builder(
                "jdbc:sqlite:E:/GitHub/java-image-bed/database.db",
                "", "");//内部会自行判断数据库类型
        // 全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        // 代码生成目录
        String projectPath = System.getProperty("user.dir");
        globalConfigBuilder.outputDir(projectPath + "/src/main/java");

        // 作者
        globalConfigBuilder.author("Louis Lee");
        globalConfigBuilder.disableOpenDir();
        // 是否覆盖旧的文件
        globalConfigBuilder.fileOverride();
        // 实体属性Swagger2注解
        //globalConfigBuilder.enableSwagger();
        // 包配置，如模块名、实体、mapper、service、controller等
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        //packageConfigBuilder.moduleName("xiaocheng");
        packageConfigBuilder.parent("com.louislee92.tools");
        packageConfigBuilder.entity("model");
        packageConfigBuilder.mapper("mapper");
//        packageConfigBuilder.service("service");
        //packageConfigBuilder.serviceImpl("serviceImpl");
//        packageConfigBuilder.controller("controller");

        // 策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();
        // 设置需要映射的表名
        strategyConfigBuilder.addInclude("image");//字符串数组，可以生成多个表

        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().convertFileName(s ->  s + "PO" );
        // 去除前缀"t_"
        //strategyConfigBuilder.addTablePrefix("t_");

        // entity的Lombok
        strategyConfigBuilder.entityBuilder().enableLombok();

        // 逻辑删除
        strategyConfigBuilder.entityBuilder().logicDeleteColumnName("deleted");
        strategyConfigBuilder.entityBuilder().logicDeletePropertyName("deleted");

        // 创建时间
        IFill gmtCreate = new Column("create_time", FieldFill.INSERT);
        // 更新时间
        IFill gmtModified = new Column("update_time", FieldFill.INSERT_UPDATE);
        strategyConfigBuilder.entityBuilder().addTableFills(gmtCreate, gmtModified);
        // 乐观锁
        //strategyConfigBuilder.entityBuilder().enableSerialVersionUID();
        //strategyConfigBuilder.entityBuilder().versionColumnName("version");
        //strategyConfigBuilder.entityBuilder().versionPropertyName("version");

        // 使用Restful风格的Controller
        strategyConfigBuilder.controllerBuilder().enableRestStyle();

        strategyConfigBuilder.mapperBuilder().enableMapperAnnotation();

        // 将请求地址转换为驼峰命名，如 http://localhost:8080/hello_id_2
//        strategyConfigBuilder.controllerBuilder().enableHyphenStyle();

        // 创建代码生成器对象，加载配置
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfigBuilder.build());
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());

        // 执行
        autoGenerator.execute();
    }
}
