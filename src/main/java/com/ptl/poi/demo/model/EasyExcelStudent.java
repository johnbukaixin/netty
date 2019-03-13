package com.ptl.poi.demo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.Date;

/**
 * created by panta on 2019/3/13.
 *
 * @author panta
 */

public class EasyExcelStudent extends BaseRowModel {

    @ExcelProperty(index = 0,value = "名称",format = "居中")
    private String name;
    @ExcelProperty(index = 1,value = "年龄")
    private int age;
    @ExcelProperty(index = 2,value = "出生年月")
    private Date birthday;
    @ExcelProperty(index = 3,value = "身高")
    private float height;
    @ExcelProperty(index = 4,value = "体重")
    private double weight;
    @ExcelProperty(index = 5,value = "性别")
    private boolean sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
