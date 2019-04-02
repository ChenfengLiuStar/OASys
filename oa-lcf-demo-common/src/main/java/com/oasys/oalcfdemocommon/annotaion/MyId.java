package com.oasys.oalcfdemocommon.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//功能需求
//属性上无此注解，直接按普通方式处理
//属性上有此注解value为id，表示此字段为表的主键
//属性上有此注解where为where，表示此字段为表的修改删除的条件
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 *
 * <p>Title: MyId.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author chenfengLiu
 * @date 2019年1月27日
 * @version 1.0
 */
public @interface MyId {
    String value() default "id";

    String where() default "where";
}
