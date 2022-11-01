package priv.print;

public class Exception {
    /**
     * 获取异常的调用栈文本
     * @param e 异常实例
     * @return 调用栈字符串
     */
    public static String getExceptionStackTrace(java.lang.Exception e) {
        StringBuilder result = new StringBuilder();
        result.append(e);

        StackTraceElement[] trace = e.getStackTrace();
        for ( StackTraceElement traceElement : trace )
        {
            result.append("\n\tat ").append(traceElement);
        }

        return result.toString();

    }
}
