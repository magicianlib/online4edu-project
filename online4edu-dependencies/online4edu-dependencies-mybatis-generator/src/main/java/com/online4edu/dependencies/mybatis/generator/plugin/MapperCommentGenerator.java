package com.online4edu.dependencies.mybatis.generator.plugin;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * 注释生成
 *
 * @author Shilin <br > mingrn97@gmail.com
 * @date 2021/03/12 10:21
 */
public class MapperCommentGenerator implements CommentGenerator {

    /**
     * 开始的分隔符,例如mysql为`,sqlserver为[
     */
    private String beginningDelimiter = "";

    /**
     * 结束的分隔符,例如mysql为`,sqlserver为]
     */
    private String endingDelimiter = "";

    public MapperCommentGenerator() {
        super();
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
    }

    /**
     * xml中的注释
     *
     * @param xmlElement
     */
    @Override
    public void addComment(XmlElement xmlElement) {
        xmlElement.addElement(new TextElement("<!--"));
        String content = "  WARNING - " + MergeConstants.NEW_ELEMENT_TAG;
        xmlElement.addElement(new TextElement(content));
        xmlElement.addElement(new TextElement("-->"));
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        return;
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        String beginningDelimiter = properties.getProperty("beginningDelimiter");
        if (StringUtility.stringHasValue(beginningDelimiter)) {
            this.beginningDelimiter = beginningDelimiter;
        }
        String endingDelimiter = properties.getProperty("endingDelimiter");
        if (StringUtility.stringHasValue(endingDelimiter)) {
            this.endingDelimiter = endingDelimiter;
        }
    }

    public String getDelimiterName(String name) {
        return beginningDelimiter + name + endingDelimiter;
    }

    /**
     * 删除标记
     */
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
        StringBuilder sb = new StringBuilder();
        sb.append(" * ");
        sb.append(MergeConstants.NEW_ELEMENT_TAG);
        if (markAsDoNotDelete) {
            sb.append(" do_not_delete_during_merge");
        }
        javaElement.addJavaDocLine(sb.toString());
    }

    /**
     * Example使用
     *
     * @param innerClass
     * @param introspectedTable
     */
    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    }

    /**
     * 给字段添加数据库备注
     *
     * @param field
     * @param introspectedTable
     * @param introspectedColumn
     */
    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {

//        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
//            field.addJavaDocLine("/**");
//            StringBuilder sb = new StringBuilder();
//            sb.append(" * ");
//            sb.append(introspectedColumn.getRemarks());
//            field.addJavaDocLine(sb.toString());
//            field.addJavaDocLine(" */");
//        }

        // 添加注解
        if (field.isTransient()) {
            field.addAnnotation("@Transient");
        }
        /*for (IntrospectedColumn column : introspectedTable.getPrimaryKeyColumns()) {
            if (introspectedColumn == column) {
                field.addAnnotation("@Id");
                break;
            }
        }*/
        String column = introspectedColumn.getActualColumnName();
        if (StringUtility.stringContainsSpace(column) || introspectedTable.getTableConfiguration().isAllColumnDelimitingEnabled()) {
            column = introspectedColumn.getContext().getBeginningDelimiter()
                    + column
                    + introspectedColumn.getContext().getEndingDelimiter();
        }


        field.addAnnotation("@ApiModelProperty(\"" + introspectedColumn.getRemarks() + "\")");

//        if (!column.equals(introspectedColumn.getJavaProperty())) {
//            //@Column
//            field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
//        } else if (StringUtility.stringHasValue(beginningDelimiter) || StringUtility.stringHasValue(endingDelimiter)) {
//            field.addAnnotation("@Column(name = \"" + getDelimiterName(column) + "\")");
//        }

        if (introspectedColumn.isIdentity()) {
            field.addAnnotation("@TableId(type = IdType.AUTO)");
            /*if (introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement().equals("JDBC")) {
                field.addAnnotation("@GeneratedValue(generator = \"JDBC\")");
            } else {
                field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY)");
            }*/
        } else if (introspectedColumn.isSequenceColumn()) {
            // 在 Oracle 中,如果需要是 SEQ_TABLENAME, 那么可以配置为 select SEQ_{1} from dual
            /*String tableName = introspectedTable.getFullyQualifiedTableNameAtRuntime();
            String sql = MessageFormat.format(introspectedTable.getTableConfiguration().getGeneratedKey().getRuntimeSqlStatement(), tableName, tableName.toUpperCase());
            field.addAnnotation("@GeneratedValue(strategy = GenerationType.IDENTITY, generator = \"" + sql + "\")");*/
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    }

    /**
     * getter方法注释
     */
    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
//        StringBuilder sb = new StringBuilder();
//        method.addJavaDocLine("/**");
//        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
//            sb.append(" * 获取");
//            sb.append(introspectedColumn.getRemarks());
//            method.addJavaDocLine(sb.toString());
//            method.addJavaDocLine(" *");
//        }
//        sb.setLength(0);
//        sb.append(" * @return ");
//        sb.append(introspectedColumn.getActualColumnName());
//        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
//            sb.append(" - ");
//            sb.append(introspectedColumn.getRemarks());
//        }
//        method.addJavaDocLine(sb.toString());
//        method.addJavaDocLine(" */");
    }

    /**
     * setter方法注释
     */
    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
//        StringBuilder sb = new StringBuilder();
//        method.addJavaDocLine("/**");
//        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
//            sb.append(" * 设置");
//            sb.append(introspectedColumn.getRemarks());
//            method.addJavaDocLine(sb.toString());
//            method.addJavaDocLine(" *");
//        }
//        Parameter parm = method.getParameters().get(0);
//        sb.setLength(0);
//        sb.append(" * @param ");
//        sb.append(parm.getName());
//        if (StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
//            sb.append(" ");
//            sb.append(introspectedColumn.getRemarks());
//        }
//        method.addJavaDocLine(sb.toString());
//        method.addJavaDocLine(" */");
    }


    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    }
}
