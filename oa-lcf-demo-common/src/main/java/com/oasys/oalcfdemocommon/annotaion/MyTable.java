package com.oasys.oalcfdemocommon.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//功能需求
//属性上无此注解，直接按普通方式处理(大写字母转小写，前面拼上“_”)
//属性上有此注解，表示value的值为表的表名
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 
* <p>Title: MyTable.java</p>  
* <p>Description: </p>  
* <p>Copyright: Copyright (c) 2019</p>  
* @author chenfengLiu
* @date 2019年1月27日  
* @version 1.0
 */
public @interface MyTable {
	String value() default "";
}
