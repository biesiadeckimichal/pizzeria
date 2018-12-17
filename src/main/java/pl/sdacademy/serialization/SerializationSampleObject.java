package pl.sdacademy.serialization;

import java.io.Serializable;

public class SerializationSampleObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stringValue;
    private transient String transientStringValue;
    private int intValue;
    private transient int transientIntValue;

    public SerializationSampleObject() {
        System.out.println("Object created with constructor");
    }

    {
        System.out.println("Object initialized");
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getTransientStringValue() {
        return transientStringValue;
    }

    public void setTransientStringValue(String transientStringValue) {
        this.transientStringValue = transientStringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public int getTransientIntValue() {
        return transientIntValue;
    }

    public void setTransientIntValue(int transientIntValue) {
        this.transientIntValue = transientIntValue;
    }
}
