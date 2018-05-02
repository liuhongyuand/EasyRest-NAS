package tech.dbgsoftware.easyrest.model.request;

import io.netty.handler.codec.http.HttpMethod;
import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class RestObject {

    private Method method;

    private List<String> httpMethodList = new ArrayList<>();

    private Class controller;

    private Map<String, Type> parameterTypeMap = new LinkedHashMap<>();

    private Map<String, String> UriValues = new HashMap<>();

    private String originalPath;

    public RestObject(Method method, HttpMethod httpMethodName, Class controller) {
        this.method = method;
        this.httpMethodList.add(httpMethodName.name().toLowerCase());
        this.controller = controller;
        analysisMethod();
    }

    public void addHttpMethod(HttpMethod httpMethod){
        if (!httpMethodList.contains(httpMethod.name().toLowerCase())){
            httpMethodList.add(httpMethod.name().toLowerCase());
        }
    }

    public void putToUriValueMap(String name, String value){
        UriValues.put(name, value);
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }

    public Map<String, String> getUriValues() {
        return UriValues;
    }

    public Method getMethod() {
        return method;
    }

    public List<String> getHttpMethodList() {
        return httpMethodList;
    }

    public Class getController() {
        return controller;
    }

    public Map<String, Type> getParameterTypeMap() {
        return parameterTypeMap;
    }

    @Override
    public int hashCode() {
        return method.hashCode() + controller.hashCode();
    }

    private void analysisMethod(){
        try {
            ClassPool pool = ClassPool.getDefault();
            pool.insertClassPath(new ClassClassPath(controller));
            CtClass cc = pool.get(controller.getName());
            CtMethod cm = cc.getDeclaredMethod(method.getName());
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < method.getParameters().length; i++) {
                String parameterName = attr.variableName(i + pos);
                Type type = method.getParameters()[i].getParameterizedType();
                parameterTypeMap.put(parameterName, type);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
