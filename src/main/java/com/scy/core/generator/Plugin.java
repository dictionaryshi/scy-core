package com.scy.core.generator;

import com.scy.core.CollectionUtil;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

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
    public boolean validate(List<String> warnings) {
        return Boolean.TRUE;
    }

    @Override
    public boolean clientInsertMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientUpdateByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientUpdateByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
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
    public boolean providerUpdateByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean providerUpdateByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return Boolean.FALSE;
    }

    @Override
    public boolean clientSelectByExampleWithBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<String> annotations = method.getAnnotations();
        String firstElement = CollectionUtil.firstElement(annotations);
        annotations.clear();
        annotations.add(firstElement);
        return Boolean.TRUE;
    }

    @Override
    public boolean clientSelectByExampleWithoutBLOBsMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<String> annotations = method.getAnnotations();
        String firstElement = CollectionUtil.firstElement(annotations);
        annotations.clear();
        annotations.add(firstElement);
        return Boolean.TRUE;
    }

    @Override
    public boolean clientSelectByPrimaryKeyMethodGenerated(Method method, Interface interfaze, IntrospectedTable introspectedTable) {
        List<String> result = CollectionUtil.newArrayList();
        List<String> annotations = method.getAnnotations();
        String target = "@Results";
        for (String annotation : annotations) {
            if (annotation.contains(target)) {
                break;
            }
            result.add(annotation);
        }
        annotations.clear();
        annotations.addAll(result);
        return Boolean.TRUE;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        String targetPackage = "org.apache.ibatis.annotations";
        Set<FullyQualifiedJavaType> importedTypes = interfaze.getImportedTypes();
        Set<FullyQualifiedJavaType> removes = importedTypes.stream().filter(fullyQualifiedJavaType -> fullyQualifiedJavaType.getFullyQualifiedName().startsWith(targetPackage)).collect(Collectors.toSet());
        removes.forEach(importedTypes::remove);
        importedTypes.remove(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));
        interfaze.addImportedType(new FullyQualifiedJavaType(targetPackage + ".*"));
        return Boolean.TRUE;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Getter"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.ToString"));
        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@ToString");
        return Boolean.TRUE;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return Boolean.FALSE;
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        Field offset = new Field("offset", integerWrapper);
        offset.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(offset);

        Method setOffset = new Method("setOffset");
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.addParameter(new Parameter(integerWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);

        Method getOffset = new Method("getOffset");
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerWrapper);
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);

        Field limit = new Field("limit", integerWrapper);
        limit.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(limit);

        Method setLimit = new Method("setLimit");
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.addParameter(new Parameter(integerWrapper, "limit"));
        setLimit.addBodyLine("this.limit = limit;");
        topLevelClass.addMethod(setLimit);

        Method getLimit = new Method("getLimit");
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerWrapper);
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);

        return Boolean.TRUE;
    }
}
