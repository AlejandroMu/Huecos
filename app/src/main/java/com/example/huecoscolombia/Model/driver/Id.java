package com.example.huecoscolombia.Model.driver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
    enum Type{
        TEXT,INTEGER
    }
    String name() default "id";
    Type type() default Type.TEXT;
}
