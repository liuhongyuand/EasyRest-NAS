package com.easyrest.utils;

import java.lang.reflect.Type;

public class ParameterTypeResolve {

    public static Object resolveType(Type type, String value) throws NumberFormatException {
        if (type.getTypeName().equals(String.class.getName())){
            return String.valueOf(value);
        } else if (type.getTypeName().equals(Integer.class.getName()) || type.getTypeName().equals("int")){
            return Integer.valueOf(value);
        } else if (type.getTypeName().equals(Double.class.getName()) || type.getTypeName().equals("double")){
            return Double.valueOf(value);
        } else if (type.getTypeName().equals(Long.class.getName()) || type.getTypeName().equals("long")){
            return Long.valueOf(value);
        } else if (type.getTypeName().equals(Float.class.getName()) || type.getTypeName().equals("float")){
            return Float.valueOf(value);
        } else {
            throw new UnsupportedOperationException("Unsupported type in multipart/form-data.");
        }
    }

}
