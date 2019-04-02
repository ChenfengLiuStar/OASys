package com.oasys.oalcfdemocommon.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//功能需求
//属性上无此注解或者值为false，直接按普通方式处理
//属性上有此注解并且为true，按照反向取值
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 
* <p>Title: MyColumn.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2019</p>  
* @author chenfengLiu
* @date 2019年1月27日  
* @version 1.0
 */
public @interface MyNot {
	boolean value() default true;
}
