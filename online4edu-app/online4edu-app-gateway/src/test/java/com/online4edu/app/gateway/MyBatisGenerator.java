package com.online4edu.app.gateway;

import com.online4edu.dependencies.mybatis.generator.util.CodeGeneratorUtil;
import org.junit.Test;

/**
 * @author magicianlib@gmail.com
 * @since 2021/03/13 12:38
 */
public class MyBatisGenerator {

    private static final String JDBC_URL = "jdbc:mysql://localhost/test?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "admin123";
    private static final String AUTHOR = "magicianlib@gmail.com";
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    private static final String PROJECT_PACKAGE = "com.online4edu.app.gateway";

    @Test
    public void generator() {
        System.out.println(PROJECT_PATH);
        String[][] tableNames = {
                {"sys_administrative_region", "", "行政区划", "id", "String"},
        };
        CodeGeneratorUtil.create(tableNames, AUTHOR, JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD, PROJECT_PATH, PROJECT_PACKAGE, null);

    }

}