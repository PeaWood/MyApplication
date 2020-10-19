package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/12
 */
public class Data_UserCard {
    /**
     * total : 1
     * items : [{"id":2912,"number":"0000052","tenancyIdx":7,"deviceStatus":{"name":"已启用","index":1},"user":{"id":433016,"userId":"13708711248","name":"李四","identity":"","password":"888888","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-23 20:24:06","updateTime":"2020-04-23 20:24:06","userGroup":null},"note":null,"createTime":"2020-10-12 22:18:00","updateTime":"2020-10-12 22:18:00"}]
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
         * id : 2912
         * number : 0000052
         * tenancyIdx : 7
         * deviceStatus : {"name":"已启用","index":1}
         * user : {"id":433016,"userId":"13708711248","name":"李四","identity":"","password":"888888","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-23 20:24:06","updateTime":"2020-04-23 20:24:06","userGroup":null}
         * note : null
         * createTime : 2020-10-12 22:18:00
         * updateTime : 2020-10-12 22:18:00
         */

        private int id;
        private String number;
        private int tenancyIdx;
        private DeviceStatusBean deviceStatus;
        private UserBean user;
        private Object note;
        private String createTime;
        private String updateTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getTenancyIdx() {
            return tenancyIdx;
        }

        public void setTenancyIdx(int tenancyIdx) {
            this.tenancyIdx = tenancyIdx;
        }

        public DeviceStatusBean getDeviceStatus() {
            return deviceStatus;
        }

        public void setDeviceStatus(DeviceStatusBean deviceStatus) {
            this.deviceStatus = deviceStatus;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public Object getNote() {
            return note;
        }

        public void setNote(Object note) {
            this.note = note;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public static class DeviceStatusBean {
            /**
             * name : 已启用
             * index : 1
             */

            private String name;
            private int index;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getIndex() {
                return index;
            }

            public void setIndex(int index) {
                this.index = index;
            }
        }

        public static class UserBean {
            /**
             * id : 433016
             * userId : 13708711248
             * name : 李四
             * identity :
             * password : 888888
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-04-23 20:24:06
             * updateTime : 2020-04-23 20:24:06
             * userGroup : null
             */

            private int id;
            private String userId;
            private String name;
            private String identity;
            private String password;
            private Object wxOpenId;
            private int tenancyIdx;
            private String note;
            private String createTime;
            private String updateTime;
            private Object userGroup;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public Object getWxOpenId() {
                return wxOpenId;
            }

            public void setWxOpenId(Object wxOpenId) {
                this.wxOpenId = wxOpenId;
            }

            public int getTenancyIdx() {
                return tenancyIdx;
            }

            public void setTenancyIdx(int tenancyIdx) {
                this.tenancyIdx = tenancyIdx;
            }

            public String getNote() {
                return note;
            }

            public void setNote(String note) {
                this.note = note;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public Object getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(Object userGroup) {
                this.userGroup = userGroup;
            }
        }
    }
}
