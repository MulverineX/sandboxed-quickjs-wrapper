package com.whl.quickjs.wrapper;

public class QuickJSContext {

    static {
        System.loadLibrary("quickjs-android-wrapper");
    }

    private static final String UNDEFINED = "undefined.js";


    public static QuickJSContext create() {
        return new QuickJSContext();
    }

    private final long context;

    private QuickJSContext() {
        context = createContext();
    }

    public Object evaluate(String script) {
        return evaluate(script, UNDEFINED);
    }

    public Object evaluate(String script, String fileName) {
        Object result = evaluate(context, script, fileName);
        if (result instanceof Long) {
            return new JSValue(context, (Long) result);
        }

        return result;
    }

    public JSValue getGlobalObject() {
        return new JSValue(context, getGlobalObject(context));
    }

    public Object call(JSValue func, JSValue thisObj, int argCount, JSValue argValue) {
        Object result = call(context, func.getValue(), thisObj.getValue(), argCount, argValue == null ? -1 : argValue.getValue());
        if (result instanceof Long) {
            return new JSValue(context, (Long) result);
        }

        return result;
    }

    public void destroyContext() {
        destroyContext(context);
    }

    private native long createContext();
    private native void destroyContext(long context);
    private native Object evaluate(long context, String script, String fileName);
    private native long getGlobalObject(long context);
    private native Object call(long context, long func, long thisObj, int argCount, long argValue);

}