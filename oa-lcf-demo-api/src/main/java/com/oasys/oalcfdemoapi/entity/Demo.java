package com.oasys.oalcfdemoapi.entity;


import com.oasys.oalcfdemocommon.annotaion.MyColumn;
import com.oasys.oalcfdemocommon.annotaion.MyDate;
import com.oasys.oalcfdemocommon.annotaion.MyId;

import java.io.Serializable;
import java.util.Date;

public class Demo implements Serializable {
    @MyColumn
    private static final long serialVersionUID = -746484290324718584L;
    @MyId
    private Integer id;
    private String name;
    @MyDate(pattern = "yyyy-MM-dd")
    private Date date;

    public Demo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
