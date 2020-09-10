package com.scy.core.generator;

import com.scy.core.CollectionUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Plugin
 *
 * @author shichunyang
 * Created by shichunyang on 2020/9/10.
 */
public class Plugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return Boolean.TRUE;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String targetPackage = "org.apache.ibatis.annotations";
        Set<FullyQualifiedJavaType> importedTypes = interfaze.getImportedTypes();
        importedTypes.remove(new ArrayList<>(importedTypes).get(1));
        importedTypes.remove(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));
        Set<FullyQualifiedJavaType> removes = importedTypes.stream().filter(fullyQualifiedJavaType -> fullyQualifiedJavaType.getFullyQualifiedName().startsWith(targetPackage)).collect(Collectors.toSet());
        removes.forEach(importedTypes::remove);
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + ".*"));
        return Boolean.TRUE;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.*"));
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@ToString");
        return Boolean.TRUE;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<String> annotations = method.getAnnotations();
        annotations.clear();
        annotations.add("@Select({");
        annotations.add("\"select \",");
        annotations.add("\"* \",");
        annotations.add("\"from " + introspectedTable.getFullyQualifiedTable() + " \",");
        annotations.add("\"where id = #{id}\"");
        annotations.add("})");
        return Boolean.TRUE;
    }

    @Override
    public boolean clientDeleteByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.TRUE;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<String> annotations = method.getAnnotations();
        String selectProvider = annotations.get(0);
        annotations.clear();
        annotations.add(selectProvider);
        List<Parameter> parameters = method.getParameters();
        parameters.clear();
        Parameter parameter = new Parameter(new ArrayList<>(interfaze.getImportedTypes()).get(0), "condition");
        parameters.add(parameter);
        return Boolean.TRUE;
    }

    @Override
    public boolean clientUpdateByPrimaryKeySelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.TRUE;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return Boolean.TRUE;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return Boolean.FALSE;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return Boolean.TRUE;
    }

    @Override
    public boolean clientInsertSelectiveMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.TRUE;
    }

    @Override
    public boolean providerGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        Set<FullyQualifiedJavaType> importedTypes = topLevelClass.getImportedTypes();
        importedTypes.remove(new ArrayList<>(importedTypes).get(1));
        return Boolean.TRUE;
    }

    @Override
    public boolean providerApplyWhereMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean providerInsertSelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.TRUE;
    }

    @Override
    public boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        List<Parameter> parameters = method.getParameters();
        parameters.clear();
        Parameter parameter = new Parameter(new ArrayList<>(topLevelClass.getImportedTypes()).get(0), "condition");
        parameters.add(parameter);
        List<String> bodyLines = method.getBodyLines();
        bodyLines.clear();
        bodyLines.add("SQL sql = new SQL();");
        bodyLines.add("");
        bodyLines.add("sql.SELECT(\"*\");");
        bodyLines.add("");
        bodyLines.add("sql.FROM(\"" + introspectedTable.getFullyQualifiedTable() + "\");");
        bodyLines.add("");
        List<IntrospectedColumn> columns = introspectedTable.getAllColumns();
        if (!CollectionUtil.isEmpty(columns)) {
            columns.forEach(column -> {
                String actualColumnName = column.getActualColumnName();
                String javaProperty = column.getJavaProperty();
                String javaPropertyGet = javaProperty.substring(0, 1).toUpperCase() + javaProperty.substring(1);
                bodyLines.add("if (condition.get" + javaPropertyGet + "() != null) {");
                bodyLines.add("sql.WHERE(\"" + actualColumnName + " = #{" + javaProperty + "}\");");
                bodyLines.add("}");
                bodyLines.add("");
            });
        }
        bodyLines.add("return sql.toString();");
        return Boolean.TRUE;
    }

    @Override
    public boolean providerUpdateByPrimaryKeySelectiveMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.TRUE;
    }
}
