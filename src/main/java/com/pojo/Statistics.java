package com.pojo;

/**
 * @Author: feng
 * @Date: 2019/9/24 17:24
 */
public class Statistics {
    private int sumId;
    private String solutionSum;
    private String dynamicSum;
    private String sortSublistSum;
    private String userSum;

    public Statistics() {
    }

    public Statistics(int sumId, String solutionSum, String dynamicSum, String sortSublistSum, String userSum) {
        this.sumId = sumId;
        this.solutionSum = solutionSum;
        this.dynamicSum = dynamicSum;
        this.sortSublistSum = sortSublistSum;
        this.userSum = userSum;
    }

    public int getSumId() {
        return sumId;
    }

    public void setSumId(int sumId) {
        this.sumId = sumId;
    }

    public String getSolutionSum() {
        return solutionSum;
    }

    public void setSolutionSum(String solutionSum) {
        this.solutionSum = solutionSum;
    }

    public String getDynamicSum() {
        return dynamicSum;
    }

    public void setDynamicSum(String dynamicSum) {
        this.dynamicSum = dynamicSum;
    }

    public String getSortSublistSum() {
        return sortSublistSum;
    }

    public void setSortSublistSum(String sortSublistSum) {
        this.sortSublistSum = sortSublistSum;
    }

    public String getUserSum() {
        return userSum;
    }

    public void setUserSum(String userSum) {
        this.userSum = userSum;
    }
}
