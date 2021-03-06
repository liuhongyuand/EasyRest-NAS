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
            CtClass[] ctClasses = new CtClass[method.getParameterCount()];
            int index = 0;
            for (Class aClass : method.getParameterTypes()) {
                ClassPool poolPara = ClassPool.getDefault();
                poolPara.insertClassPath(new ClassClassPath(aClass));
                ctClasses[index++] = poolPara.get(aClass.getName());
            }
            CtMethod cm = ctClasses.length == 0 ? cc.getDeclaredMethod(method.getName()) : cc.getDeclaredMethod(method.getName(), ctClasses);
            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            boolean afterThis = false;
            List<String> paraName = new ArrayList<>(method.getParameters().length);
            for (int i = 0; i < attr.tableLength(); i++) {
                String name = attr.variableName(i);
                if (!afterThis && name.equalsIgnoreCase("this")){
                    afterThis = true;
                    continue;
                }
                if (afterThis && paraName.size() < method.getParameters().length) {
                    paraName.add(name);
                }
            }
            for (int i = 0; i < method.getParameters().length; i++) {
                Type type = method.getParameters()[i].getParameterizedType();
                parameterTypeMap.put(paraName.get(i), type);
            }
        } catch (Exception e){
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
