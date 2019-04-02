package com.oasys.oalcfdemocommon.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//功能需求
//属性上无此注解，直接按普通方式处理
//属性上有此注解，并且value值为"nolike"，表示where条件按=查询
//属性上有此注解，并且value值不为"nolike"，表示where条件按like查询
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
public @interface MySelect {
	String value() default "nolike";
}
