package com.cloud.spring.demo.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * mybatis代码生成器
 */
public class CodeAutoGenerator {

    /**
     * 包名
     */
    private static final String PACKAGE_NAME = "com.cloud.spring.demo";

    /**
     * 模块名称
     */
    private static final String MODULE_NAME = "";

    /**
     * 输出文件的路径
     */
    private static String projectPath = System.getProperty("user.dir");

    private static final String OUT_PATH = projectPath + "/src/main/java";

    /**
     * 代码生成者
     */
    private static final String AUTHOR = "Administrator";

    /**
     * JDBC配置
     */
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String URL = "jdbc:postgresql://10.121.74.190:5432/dsgweb";
    private static final String USER_NAME = "dsg";
    private static final String PASSWORD = "dsg";

    /**
     * postgresql 生成演示
     *
     * @param args
     */
    public static void main(String[] args) {
        //代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成文件的输出目录
        gc.setOutputDir(OUT_PATH);
        //开发人员
        gc.setAuthor(AUTHOR);
        //是否打开输出目录
        gc.setOpen(true);
        //service 命名方式
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");//service impl 命名方式
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setControllerName("%sController");
        gc.setEntityName("%sBean");
        gc.setFileOverride(true);
        gc.setActiveRecord(false);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(true);// XML columList
        autoGenerator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.POSTGRE_SQL);
        dsc.setUrl(URL);
        //数据库 schema name
        //dsc.setSchemaName("public");
        dsc.setDriverName(DRIVER);
        dsc.setUsername(USER_NAME);
        dsc.setPassword(PASSWORD);
        autoGenerator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //父包模块名
        //pc.setModuleName(scanner("模块名"));
        //父包名。// 自定义包路径  如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(PACKAGE_NAME);
        pc.setEntity("entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setController("controller");//设置控制器包名
        pc.setMapper("mapper");
        pc.setXml("src/main/resources/mapper");
        autoGenerator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        //自定义xml生成路径
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名称
                return projectPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        autoGenerator.setCfg(cfg);

        autoGenerator.setTemplate(new TemplateConfig().setXml(null));

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //  strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");//自定义继承的Entity类全称，带包名
        //【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        //生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        //strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");//自定义继承的Controller类全称，带包名
        //需要包含的表名，允许正则表达式
        strategy.setInclude("md_auth");
        //strategy.setExclude(new String[]{"test"}) // 排除生成的表
        //strategy.setSuperEntityColumns("id");//自定义基础的Entity类，公共字段
        strategy.setControllerMappingHyphenStyle(true);//驼峰转连字符
        strategy.setTablePrefix("md_");//表前缀
        autoGenerator.setStrategy(strategy);

        autoGenerator.execute();
    }
}
