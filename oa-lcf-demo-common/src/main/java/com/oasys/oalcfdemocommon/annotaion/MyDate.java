package com.oasys.oalcfdemocommon.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//功能需求
//属性上无此注解，直接按普通方式处理
//属性上有此注解，并且value值为"endDate"，表示该字段是查询条件的截止日期
//属性上有此注解，并且value值不为"endDate"，表示该字段是查询的开始日期
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 
* <p>Title: MyDate.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2019</p>  
* @author chenfengLiu
* @date 2019年1月27日  
* @version 1.0
 */
public @interface MyDate {
	String value() default "endDate";
	String pattern() default "yyyy-MM-dd HH:mm:ss";
}
