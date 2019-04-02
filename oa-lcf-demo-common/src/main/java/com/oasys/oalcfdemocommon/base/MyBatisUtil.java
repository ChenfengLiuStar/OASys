package com.oasys.oalcfdemocommon.base;

import com.github.pagehelper.PageHelper;
import com.oasys.oalcfdemocommon.annotaion.*;
import org.apache.ibatis.jdbc.SQL;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>Title: MyBatisUtil.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author chenfengLiu
 * @version 2.0
 * @date 2019年3月15日
 */
public class MyBatisUtil<T extends Serializable> {
    //查询方法（根据传入的条件进行条件查询、排序、以及Date的between and 和分页）
    public String findAll(final T t, Map<String, Object> map) {
        final Class class1 = t.getClass();
        String sql = null;
        try {
            sql = new SQL() {{
                SELECT(getColumn(class1));
                FROM(getTableName(class1));
                WHERE(getWhere(t));
            }}.toString();
            //关键字查询
            if (map != null) {
                sql += " and ";
                String[] cnames = (String[]) map.get("cnames");
                String value = (String) map.get("value");
                for (int i = 0; i < cnames.length; i++) {
                    Field declaredField = t.getClass().getDeclaredField(cnames[i]);
                    if (declaredField.getType().equals(String.class)) {
                        sql += cnames[i] + " like '%" + value + "%'  or ";
                    } else if (declaredField.getType().equals(Integer.class) || declaredField.getType().equals(int.class) || declaredField.getType().equals(Double.class) || declaredField.getType().equals(double.class)) {
                        MyForeign myForeign = declaredField.getAnnotation(MyForeign.class);
                        if (myForeign != null) {
                            sql += cnames[i] + " in (select id from " + myForeign.table() + " where " + myForeign.name() + " like '%" + value + "%')  or ";
                        } else {
                            if (Pattern.compile("^-?\\d+(\\.\\d+)?$").matcher(value).matches())
                                sql += cnames[i] + " = " + value + " or ";
                        }
                    } else {
                        MyDate myDate = declaredField.getAnnotation(MyDate.class);
                        String pattern = myDate.pattern();
                        DateFormat sdf = new SimpleDateFormat(pattern);
                        Date parse = null;
                        try {
                            parse = sdf.parse(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (parse != null)
                            sql += cnames[i] + " = '%" + value + "%' or ";
                    }
                }
                sql = sql.substring(0, sql.length() - 3);
            }
            for (Field field : class1.getDeclaredFields()) {
                MyOrder myOrder = (MyOrder) field.getAnnotation(MyOrder.class);
                if (myOrder != null)
                    sql += " order by " + getColumnOne(field) + " " + myOrder.value();
            }
            Class superclass = class1.getSuperclass().getSuperclass();
            if (null != superclass) {
                Field declaredField = superclass.getDeclaredField("pageNum");
                Field declaredField2 = superclass.getDeclaredField("pageSize");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                if (declaredField.get(t) != null)
                    PageHelper.startPage((Integer) declaredField.get(t), (Integer) declaredField2.get(t));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    public String findCount(final T t, Map<String, Object> map) {
        final Class class1 = t.getClass();
        String sql = null;
        try {
            sql = new SQL() {{
                SELECT(" count(*) ");
                FROM(getTableName(class1));
                WHERE(getWhere(t));
            }}.toString();
            //关键字查询
            if (map != null) {
                sql += " and ";
                String[] cnames = (String[]) map.get("cnames");
                String value = (String) map.get("value");
                for (int i = 0; i < cnames.length; i++) {
                    Field declaredField = t.getClass().getDeclaredField(cnames[i]);
                    if (declaredField.getType().equals(String.class)) {
                        sql += cnames[i] + " like '%" + value + "%'  or ";
                    } else if (declaredField.getType().equals(Integer.class) || declaredField.getType().equals(int.class) || declaredField.getType().equals(Double.class) || declaredField.getType().equals(double.class)) {
                        MyForeign myForeign = declaredField.getAnnotation(MyForeign.class);
                        if (myForeign != null) {
                            sql += cnames[i] + " in (select id from " + myForeign.table() + " where " + myForeign.name() + " like '%" + value + "%')  or ";
                        } else {
                            if (Pattern.compile("^-?\\d+(\\.\\d+)?$").matcher(value).matches())
                                sql += cnames[i] + " = " + value + " or ";
                        }
                    } else {
                        MyDate myDate = declaredField.getAnnotation(MyDate.class);
                        String pattern = myDate.pattern();
                        DateFormat sdf = new SimpleDateFormat(pattern);
                        Date parse = null;
                        try {
                            parse = sdf.parse(value);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (parse != null)
                            sql += cnames[i] + " = '%" + value + "%' or ";
                    }
                }
                sql = sql.substring(0, sql.length() - 3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    //获取表中所有字段供查询
    private String getColumn(Class class1) {
        StringBuffer sb = new StringBuffer();
        for (Field field : class1.getDeclaredFields()) {
            String column = getColumnOne(field);
            if (column != null)
                sb.append(column).append(" as ").append(field.getName()).append(",");
        }
        String tempStr = sb.toString();
        if (tempStr.indexOf(",") != -1)
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        return tempStr;
    }

    //获取操作表的表名
    private String getTableName(Class class1) {
        StringBuffer sb = new StringBuffer();
        MyTable myTable = (MyTable) class1.getAnnotation(MyTable.class);
        sb.append(myTable == null ? getColumnOneName(class1.getSimpleName()) : myTable.value());
        String tableName = sb.toString();
        if (tableName.indexOf("_") == 0)
            tableName = tableName.substring(1);
        return tableName;
    }

    //获取表中属性是否是数据库有的字段
    private String getColumnOne(Field field) {
        MyColumn myColumn = (MyColumn) field.getAnnotation(MyColumn.class);
        if (myColumn != null) {
            if ("noColumn".equals(myColumn.value()))
                return null;
            else
                return myColumn.value();
        } else
            return getColumnOneName(field.getName()).toString();
    }

    //获取查询的条件
    private String getWhere(T t) throws Exception {
        StringBuffer sb = new StringBuffer("1=1 ");
        boolean b = true;
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(t);
            String column = getColumnOne(field);
            if (field.getType().equals(String.class)) {
                if (value != null && !"".equals(value.toString())) {
                    if (column != null) {
                        MySelect mySelect = (MySelect) field.getAnnotation(MySelect.class);
                        if (mySelect != null && "nolike".equals(mySelect.value()))
                            sb.append(" and " + column + " = '" + value + "' ");
                        else
                            sb.append(" and " + column + " like '%" + value + "%' ");
                    }
                }
            } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class) || field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                if (column != null) {
                    MyForeign myfk = (MyForeign) field.getAnnotation(MyForeign.class);
                    if (myfk != null) {
                        Class superclass = t.getClass().getSuperclass();
                        Field declaredField = superclass.getDeclaredField("map");
                        declaredField.setAccessible(true);
                        Map<String, Object> object = (Map<String, Object>) declaredField.get(t);
                        if (null != object.get(field.getName()))
                            sb.append(" and " + column + " in (select id from " + myfk.table() + " where " + myfk.name() + " like '%" + object.get(field.getName()) + "%')");
                    } else {
                        if (value != null) {
                            MyNot mynot = (MyNot) field.getAnnotation(MyNot.class);
                            if (mynot != null && mynot.value())
                                sb.append(" and " + column + " != " + value);
                            else
                                sb.append(" and " + column + " = " + value);
                        }
                    }
                }
            } else {
                if (value != null) {
                    if (field.getType().equals(Date.class)) {
                        MyDate myDate = (MyDate) field.getAnnotation(MyDate.class);
                        if (myDate != null) {
                            value = getStringDate(myDate.pattern(), value);
                            if ("endDate".equals(myDate.value())) {
                                if (b) {
                                    b = false;
                                    sb.append(getBetweenValue(t, value, "starDate", null));
                                }
                            } else {
                                if (b) {
                                    b = false;
                                    String betweenValue = getBetweenValue(t, value, "endDate", field);
                                    sb.append(betweenValue);
                                    if ("".equals(betweenValue)) {
                                        sb.append(" and " + column + " = '" + value + "' ");
                                    }
                                }
                            }
                        } else {
                            if (column != null)
                                sb.append(" and " + column + " = '" + value + "' ");
                        }
                    } else {
                        if (column != null)
                            sb.append(" and " + column + " = '" + value + "' ");
                    }
                }
            }
        }
        return sb.toString();
    }

    //获取查询条件Date的条件
    private String getBetweenValue(T t, Object value, String state, Field field1) throws Exception {
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            MyDate myDate = (MyDate) field.getAnnotation(MyDate.class);
            if (myDate != null) {
                if (state.equals(myDate.value())) {
                    Object value1 = field.get(t);
                    if (value1 != null) {
                        value1 = getStringDate(myDate.pattern(), value1);
                        if ("endDate".equals(state))
                            return " and " + getColumnOne(field1 == null ? field : field1) + " between '" + value + "' and '" + value1 + "'";
                        return " and " + getColumnOne(field1 == null ? field : field1) + " between '" + value1 + "' and '" + value + "'";
                    }
                }
            }
        }
        return "";
    }

    //获取（基本方法）数据库字段名
    private StringBuffer getColumnOneName(String fieldname) {
        StringBuffer sb = new StringBuffer();
        for (char c : fieldname.toCharArray()) {
            sb.append(c >= 'A' && c <= 'Z' ? "_" + c : c);
        }
        return sb;
    }

    //获取保存的数值
    private String getValues(T t) {
        StringBuffer sb = new StringBuffer();
        for (Field field : t.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(t);
                MyColumn myColumn = (MyColumn) field.getAnnotation(MyColumn.class);
                if (myColumn == null || !"noColumn".equals(myColumn.value())) {
                    if (value != null) {
                        if (field.getType().equals(Date.class))
                            value = getStringDate(((MyDate) field.getAnnotation(MyDate.class)).pattern(), value);
                        sb.append("'" + value + "',");
                    } else
                        sb.append("NULL,");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        String tempStr = sb.toString();
        if (tempStr.indexOf(",") != -1) {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }
        return tempStr;
    }

    //如果主键为空就添加否则就修改
    public String update(final T t) {
        final Class class1 = t.getClass();
        try {
            for (Field field : t.getClass().getDeclaredFields()) {
                String column = getColumnOne(field);
                if (column != null) {
                    field.setAccessible(true);
                    MyId myId = (MyId) field.getAnnotation(MyId.class);
                    if (myId != null && "id".equals(myId.value())) {
                        if (field.get(t) != null) {
                            return new SQL() {{
                                INSERT_INTO(getTableName(class1));
                                INTO_VALUES(getValues(t));
                            }}.toString();
                        } else {
                            return new SQL() {{
                                UPDATE(getTableName(class1));
                                SET(getSet(t));
                                WHERE(getId(t));
                            }}.toString();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取要修改的值
    private String getSet(T t) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (Field field : t.getClass().getDeclaredFields()) {
            String column = getColumnOne(field);
            if (column != null) {
                field.setAccessible(true);
                Object value = field.get(t);
                MyId myId = (MyId) field.getAnnotation(MyId.class);
                if (myId == null || myId != null && !"where".equals(myId.where())) {
                    if (value != null) {
                        if (field.getType().equals(Date.class))
                            value = getStringDate(((MyDate) field.getAnnotation(MyDate.class)).pattern(), value);
                        sb.append(column).append(" = ").append("'" + value + "',");
                    } else
                        sb.append(column).append(" = ").append("NULL,");
                }
            }
        }
        String tempStr = sb.toString();
        if (tempStr.indexOf(",") != -1) {
            tempStr = tempStr.substring(0, tempStr.length() - 1);
        }
        return tempStr;
    }

    //获取修改的条件
    private String getId(T t) throws Exception {
        StringBuffer sb = new StringBuffer("1=1 ");
        for (Field field : t.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            Object value = field.get(t);
            String column = getColumnOne(field);
            if (column != null) {
                MyId myId = (MyId) field.getAnnotation(MyId.class);
                if (myId != null && "where".equals(myId.where())) {
                    if (value != null) {
                        if (field.getType().equals(Date.class))
                            value = getStringDate(((MyDate) field.getAnnotation(MyDate.class)).pattern(), value);
                        sb.append(" and " + getColumnOne(field)).append(" = ").append("'" + value + "'");
                    }
                }
            }
        }
        return sb.toString();
    }

    //删除方法
    public String delete(final T t) {
        final Class class1 = t.getClass();
        String sql = null;
        try{
            sql = new SQL() {{
                DELETE_FROM(getTableName(class1));
                WHERE(getId(t));
            }}.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    //把Date转为String
    private String getStringDate(String format, Object value) {
        return new SimpleDateFormat(format).format(value);
    }
}
