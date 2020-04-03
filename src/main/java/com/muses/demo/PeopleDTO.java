/*
 * Copyright 2019 All rights reserved.
 */

package com.muses.demo;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author miaoqiang
 * @date 2020/4/3.
 */
public class PeopleDTO implements Serializable {
    private static final long serialVersionUID = -1916238994508829278L;

    private String name;
    private String gender;
    private String cardNo;
    private String height;
    private String weight;

    public PeopleDTO() {
        this.name = "小花";
        this.gender = "女";
        this.cardNo = "132123123";
        this.height = "166cm";
        this.weight = "50kg";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .appendSuper(super.toString())
            .append("他(她)的名字", name)
            .append("性别是", gender)
            .append("证件号是", cardNo)
            .append("身高是", height)
            .append("体重是", weight)
            .toString();
    }
}
