package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/9/27
 */
public class Data_CustomerBottle {
    /**
     * total : 1
     * items : [{"id":12,"userId":"5274196324","userName":"方庆云","address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"departmentName":null,"departmentCode":null,"sAmount":0,"mAmount":1,"lAmount":0,"amount":1,"customerBottleType":null,"tenancyIdx":7,"note":"","createTime":"2020-05-12 10:49:40","updateTime":"2020-05-12 10:49:40"}]
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
         * id : 12
         * userId : 5274196324
         * userName : 方庆云
         * address : {"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"}
         * departmentName : null
         * departmentCode : null
         * sAmount : 0
         * mAmount : 1
         * lAmount : 0
         * amount : 1
         * customerBottleType : null
         * tenancyIdx : 7
         * note :
         * createTime : 2020-05-12 10:49:40
         * updateTime : 2020-05-12 10:49:40
         */

        private int id;
        private String userId;
        private String userName;
        private AddressBean address;
        private Object departmentName;
        private Object departmentCode;
        private int sAmount;
        private int mAmount;
        private int lAmount;
        private int amount;
        private Object customerBottleType;
        private int tenancyIdx;
        private String note;
        private String createTime;
        private String updateTime;

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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public Object getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(Object departmentName) {
            this.departmentName = departmentName;
        }

        public Object getDepartmentCode() {
            return departmentCode;
        }

        public void setDepartmentCode(Object departmentCode) {
            this.departmentCode = departmentCode;
        }

        public int getSAmount() {
            return sAmount;
        }

        public void setSAmount(int sAmount) {
            this.sAmount = sAmount;
        }

        public int getMAmount() {
            return mAmount;
        }

        public void setMAmount(int mAmount) {
            this.mAmount = mAmount;
        }

        public int getLAmount() {
            return lAmount;
        }

        public void setLAmount(int lAmount) {
            this.lAmount = lAmount;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public Object getCustomerBottleType() {
            return customerBottleType;
        }

        public void setCustomerBottleType(Object customerBottleType) {
            this.customerBottleType = customerBottleType;
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

        public static class AddressBean {
            /**
             * country : 中国
             * province : 云南省
             * city : 昆明市
             * county : 盘龙区
             * detail : 玉器城2栋3单元501
             */

            private String country;
            private String province;
            private String city;
            private String county;
            private String detail;

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCounty() {
                return county;
            }

            public void setCounty(String county) {
                this.county = county;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }
        }
    }
}
