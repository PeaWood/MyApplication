package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/27
 */
public class Data_Caculate {
    /**
     * total : 1
     * items : [{"id":246169,"userId":"yyy1","name":null,"identity":null,"password":null,"wxOpenId":null,"tenancyIdx":null,"note":null,"createTime":null,"updateTime":null,"userGroup":null,"department":null,"userPosition":null,"jobNumber":null,"mobilePhone":null,"officePhone":null,"email":null,"serviceStatus":null,"aliveStatus":null,"aliveUpdateTime":null,"gasTakeOverStatus":null,"logoutStatus":null}]
     */

    private int total;
    private List<ItemsBean> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 246169
         * userId : yyy1
         * name : null
         * identity : null
         * password : null
         * wxOpenId : null
         * tenancyIdx : null
         * note : null
         * createTime : null
         * updateTime : null
         * userGroup : null
         * department : null
         * userPosition : null
         * jobNumber : null
         * mobilePhone : null
         * officePhone : null
         * email : null
         * serviceStatus : null
         * aliveStatus : null
         * aliveUpdateTime : null
         * gasTakeOverStatus : null
         * logoutStatus : null
         */

        private int id;
        private String userId;
        private Object name;
        private Object identity;
        private Object password;
        private Object wxOpenId;
        private Object tenancyIdx;
        private Object note;
        private Object createTime;
        private Object updateTime;
        private Object userGroup;
        private Object department;
        private Object userPosition;
        private Object jobNumber;
        private Object mobilePhone;
        private Object officePhone;
        private Object email;
        private Object serviceStatus;
        private Object aliveStatus;
        private Object aliveUpdateTime;
        private Object gasTakeOverStatus;
        private Object logoutStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public Object getName() {
            return name;
        }

        public void setName(Object name) {
            this.name = name;
        }

        public Object getIdentity() {
            return identity;
        }

        public void setIdentity(Object identity) {
            this.identity = identity;
        }

        public Object getPassword() {
            return password;
        }

        public void setPassword(Object password) {
            this.password = password;
        }

        public Object getWxOpenId() {
            return wxOpenId;
        }

        public void setWxOpenId(Object wxOpenId) {
            this.wxOpenId = wxOpenId;
        }

        public Object getTenancyIdx() {
            return tenancyIdx;
        }

        public void setTenancyIdx(Object tenancyIdx) {
            this.tenancyIdx = tenancyIdx;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getUserGroup() {
            return userGroup;
        }

        public void setUserGroup(Object userGroup) {
            this.userGroup = userGroup;
        }

        public Object getDepartment() {
            return department;
        }

        public void setDepartment(Object department) {
            this.department = department;
        }

        public Object getUserPosition() {
            return userPosition;
        }

        public void setUserPosition(Object userPosition) {
            this.userPosition = userPosition;
        }

        public Object getJobNumber() {
            return jobNumber;
        }

        public void setJobNumber(Object jobNumber) {
            this.jobNumber = jobNumber;
        }

        public Object getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(Object mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public Object getOfficePhone() {
            return officePhone;
        }

        public void setOfficePhone(Object officePhone) {
            this.officePhone = officePhone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public Object getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(Object serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public Object getAliveStatus() {
            return aliveStatus;
        }

        public void setAliveStatus(Object aliveStatus) {
            this.aliveStatus = aliveStatus;
        }

        public Object getAliveUpdateTime() {
            return aliveUpdateTime;
        }

        public void setAliveUpdateTime(Object aliveUpdateTime) {
            this.aliveUpdateTime = aliveUpdateTime;
        }

        public Object getGasTakeOverStatus() {
            return gasTakeOverStatus;
        }

        public void setGasTakeOverStatus(Object gasTakeOverStatus) {
            this.gasTakeOverStatus = gasTakeOverStatus;
        }

        public Object getLogoutStatus() {
            return logoutStatus;
        }

        public void setLogoutStatus(Object logoutStatus) {
            this.logoutStatus = logoutStatus;
        }
    }
}
