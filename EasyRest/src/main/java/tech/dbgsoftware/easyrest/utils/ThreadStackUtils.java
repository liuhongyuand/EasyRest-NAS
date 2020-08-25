package tech.dbgsoftware.easyrest.utils;

public class ThreadStackUtils {

    public static String getStackInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < Math.min(6, Thread.currentThread().getStackTrace().length); i++) {
            String className = Thread.currentThread().getStackTrace()[i].getClassName();
            String methodName = Thread.currentThread().getStackTrace()[i].getMethodName();
            String line = String.valueOf(Thread.currentThread().getStackTrace()[i].getLineNumber());
            stringBuffer.append(className).append(":").append(methodName).append(":").append(line).append("\r\n");
        }
        return stringBuffer.toString();
    }

    public static void main(String[] args) {
        System.out.println(getStackInfo());
    }

}
