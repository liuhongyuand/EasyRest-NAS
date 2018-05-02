package tech.dbgsoftware.easyrest.aop.resolvers;

import java.lang.reflect.Type;

public class ParameterTypeResolve {

    static Object resolveType(Type type, String value) throws NumberFormatException {
        if (type.getTypeName().equals(String.class.getName())){
            return String.valueOf(value);
        } else if (type.getTypeName().equals(Integer.class.getName()) || type.getTypeName().equals("int")){
            return Double.valueOf(value).intValue();
        } else if (type.getTypeName().equals(Double.class.getName()) || type.getTypeName().equals("double")){
            return Double.valueOf(value);
        } else if (type.getTypeName().equals(Long.class.getName()) || type.getTypeName().equals("long")){
            return Double.valueOf(value).longValue();
        } else if (type.getTypeName().equals(Float.class.getName()) || type.getTypeName().equals("float")){
            return Double.valueOf(value).floatValue();
        } else {
            throw new UnsupportedOperationException("Unsupported type in multipart/form-data.");
        }
    }

}
