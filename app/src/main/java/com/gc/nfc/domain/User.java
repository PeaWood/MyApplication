package com.gc.nfc.domain;

public class User {
    private String departmentCode;

    private String departmentName;

    private String groupCode;

    private String groupName;

    private String password;

    private int scanType;

    private String username;

    public String getDepartmentCode() {
        return this.departmentCode;
    }

    public String getDepartmentName() {
        return this.departmentName;
    }

    public String getGroupCode() {
        return this.groupCode;
    }

    public String getGroupName() {
        return this.groupName;
    }

    public String getPassword() {
        return this.password;
    }

    public int getScanType() {
        return this.scanType;
    }

    public String getUsername() {
        return this.username;
    }

    public void setDepartmentCode(String paramString) {
        this.departmentCode = paramString;
    }

    public void setDepartmentName(String paramString) {
        this.departmentName = paramString;
    }

    public void setGroupCode(String paramString) {
        this.groupCode = paramString;
    }

    public void setGroupName(String paramString) {
        this.groupName = paramString;
    }

    public void setPassword(String paramString) {
        this.password = paramString;
    }

    public void setScanType(int paramInt) {
        this.scanType = paramInt;
    }

    public void setUsername(String paramString) {
        this.username = paramString;
    }
}

