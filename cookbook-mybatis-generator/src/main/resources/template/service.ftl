package ${basePackage}.service;

import ${basePackage}.domain.${domainNameUpperCamel};
import ${basePackage}.vo.${domainNameUpperCamel}VO;
import service.io.ituknown.mybatis.BaseService;
import java.lang.${pkJavaType};

/**
 * ${description} - Service接口类
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${domainNameUpperCamel}Service
        extends BaseService<${domainNameUpperCamel}, ${domainNameUpperCamel}VO, ${pkJavaType}> {

}