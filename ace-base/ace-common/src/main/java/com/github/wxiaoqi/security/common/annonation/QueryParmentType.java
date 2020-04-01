package com.github.wxiaoqi.security.common.annonation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD,ElementType.TYPE,ElementType.FIELD})
public @interface QueryParmentType {

    /**
     *查询条件的类型
     * @return
     */
    String key() default "=";;
}
