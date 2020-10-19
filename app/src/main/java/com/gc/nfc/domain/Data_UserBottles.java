package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/12
 */
public class Data_UserBottles {
    /**
     * total : 1
     * items : [{"id":10562,"number":"KMA2B05044643","publicNumber":"120500269","spec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"tareWeight":16.5,"tareWeightStatus":{"name":"正常","index":0},"productionDate":"2018-08-01 00:00:00","verifyDate":"2018-08-01 00:00:00","nextVerifyDate":"2022-08-01 00:00:00","scrapDate":"2030-08-01 00:00:00","longitude":102.745479,"latitude":25.031295,"lifeStatus":{"name":"已启用","index":1},"loadStatus":{"name":"重瓶","index":1},"serviceStatus":{"name":"客户使用","index":5},"user":{"id":433016,"userId":"13708711248","name":"李四","identity":"","password":"888888","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-23 20:24:06","updateTime":"2020-04-23 20:24:06","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}},"userDepartment":null,"factory":{"id":48,"code":"1","name":"重庆恒祥","tenancyIdx":7,"note":"","createTime":"2020-03-17 19:37:33","updateTime":"2020-05-19 10:07:02"},"fullWeight":null,"emptyWeight":null,"gasPrice":0,"tenancyIdx":7,"note":null,"createTime":"2020-03-18 14:02:17","updateTime":"2020-08-07 15:56:33","isCheckStatus":{"name":"勾选","index":1},"operIdx":246037,"operUserId":"bjpx_admin"}]
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
         * id : 10562
         * number : KMA2B05044643
         * publicNumber : 120500269
         * spec : {"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"}
         * tareWeight : 16.5
         * tareWeightStatus : {"name":"正常","index":0}
         * productionDate : 2018-08-01 00:00:00
         * verifyDate : 2018-08-01 00:00:00
         * nextVerifyDate : 2022-08-01 00:00:00
         * scrapDate : 2030-08-01 00:00:00
         * longitude : 102.745479
         * latitude : 25.031295
         * lifeStatus : {"name":"已启用","index":1}
         * loadStatus : {"name":"重瓶","index":1}
         * serviceStatus : {"name":"客户使用","index":5}
         * user : {"id":433016,"userId":"13708711248","name":"李四","identity":"","password":"888888","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-23 20:24:06","updateTime":"2020-04-23 20:24:06","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}}
         * userDepartment : null
         * factory : {"id":48,"code":"1","name":"重庆恒祥","tenancyIdx":7,"note":"","createTime":"2020-03-17 19:37:33","updateTime":"2020-05-19 10:07:02"}
         * fullWeight : null
         * emptyWeight : null
         * gasPrice : 0
         * tenancyIdx : 7
         * note : null
         * createTime : 2020-03-18 14:02:17
         * updateTime : 2020-08-07 15:56:33
         * isCheckStatus : {"name":"勾选","index":1}
         * operIdx : 246037
         * operUserId : bjpx_admin
         */

        private int id;
        private String number;
        private String publicNumber;
        private SpecBean spec;
        private double tareWeight;
        private TareWeightStatusBean tareWeightStatus;
        private String productionDate;
        private String verifyDate;
        private String nextVerifyDate;
        private String scrapDate;
        private double longitude;
        private double latitude;
        private LifeStatusBean lifeStatus;
        private LoadStatusBean loadStatus;
        private ServiceStatusBean serviceStatus;
        private UserBean user;
        private Object userDepartment;
        private FactoryBean factory;
        private Object fullWeight;
        private Object emptyWeight;
        private int gasPrice;
        private int tenancyIdx;
        private Object note;
        private String createTime;
        private String updateTime;
        private IsCheckStatusBean isCheckStatus;
        private int operIdx;
        private String operUserId;

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

        public String getPublicNumber() {
            return publicNumber;
        }

        public void setPublicNumber(String publicNumber) {
            this.publicNumber = publicNumber;
        }

        public SpecBean getSpec() {
            return spec;
        }

        public void setSpec(SpecBean spec) {
            this.spec = spec;
        }

        public double getTareWeight() {
            return tareWeight;
        }

        public void setTareWeight(double tareWeight) {
            this.tareWeight = tareWeight;
        }

        public TareWeightStatusBean getTareWeightStatus() {
            return tareWeightStatus;
        }

        public void setTareWeightStatus(TareWeightStatusBean tareWeightStatus) {
            this.tareWeightStatus = tareWeightStatus;
        }

        public String getProductionDate() {
            return productionDate;
        }

        public void setProductionDate(String productionDate) {
            this.productionDate = productionDate;
        }

        public String getVerifyDate() {
            return verifyDate;
        }

        public void setVerifyDate(String verifyDate) {
            this.verifyDate = verifyDate;
        }

        public String getNextVerifyDate() {
            return nextVerifyDate;
        }

        public void setNextVerifyDate(String nextVerifyDate) {
            this.nextVerifyDate = nextVerifyDate;
        }

        public String getScrapDate() {
            return scrapDate;
        }

        public void setScrapDate(String scrapDate) {
            this.scrapDate = scrapDate;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public LifeStatusBean getLifeStatus() {
            return lifeStatus;
        }

        public void setLifeStatus(LifeStatusBean lifeStatus) {
            this.lifeStatus = lifeStatus;
        }

        public LoadStatusBean getLoadStatus() {
            return loadStatus;
        }

        public void setLoadStatus(LoadStatusBean loadStatus) {
            this.loadStatus = loadStatus;
        }

        public ServiceStatusBean getServiceStatus() {
            return serviceStatus;
        }

        public void setServiceStatus(ServiceStatusBean serviceStatus) {
            this.serviceStatus = serviceStatus;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public Object getUserDepartment() {
            return userDepartment;
        }

        public void setUserDepartment(Object userDepartment) {
            this.userDepartment = userDepartment;
        }

        public FactoryBean getFactory() {
            return factory;
        }

        public void setFactory(FactoryBean factory) {
            this.factory = factory;
        }

        public Object getFullWeight() {
            return fullWeight;
        }

        public void setFullWeight(Object fullWeight) {
            this.fullWeight = fullWeight;
        }

        public Object getEmptyWeight() {
            return emptyWeight;
        }

        public void setEmptyWeight(Object emptyWeight) {
            this.emptyWeight = emptyWeight;
        }

        public int getGasPrice() {
            return gasPrice;
        }

        public void setGasPrice(int gasPrice) {
            this.gasPrice = gasPrice;
        }

        public int getTenancyIdx() {
            return tenancyIdx;
        }

        public void setTenancyIdx(int tenancyIdx) {
            this.tenancyIdx = tenancyIdx;
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

        public IsCheckStatusBean getIsCheckStatus() {
            return isCheckStatus;
        }

        public void setIsCheckStatus(IsCheckStatusBean isCheckStatus) {
            this.isCheckStatus = isCheckStatus;
        }

        public int getOperIdx() {
            return operIdx;
        }

        public void setOperIdx(int operIdx) {
            this.operIdx = operIdx;
        }

        public String getOperUserId() {
            return operUserId;
        }

        public void setOperUserId(String operUserId) {
            this.operUserId = operUserId;
        }

        public static class SpecBean {
            /**
             * id : 21
             * code : 0002
             * name : 15公斤
             * tenancyIdx : null
             * note :
             * createTime : 2018-01-12 09:26:52
             * updateTime : 2018-05-16 10:23:22
             */

            private int id;
            private String code;
            private String name;
            private Object tenancyIdx;
            private String note;
            private String createTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getTenancyIdx() {
                return tenancyIdx;
            }

            public void setTenancyIdx(Object tenancyIdx) {
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
        }

        public static class TareWeightStatusBean {
            /**
             * name : 正常
             * index : 0
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

        public static class LifeStatusBean {
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

        public static class LoadStatusBean {
            /**
             * name : 重瓶
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

        public static class ServiceStatusBean {
            /**
             * name : 客户使用
             * index : 5
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
             * userGroup : {"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
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
            private UserGroupBean userGroup;

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

            public UserGroupBean getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(UserGroupBean userGroup) {
                this.userGroup = userGroup;
            }

            public static class UserGroupBean {
                /**
                 * id : 4
                 * code : 00004
                 * name : 客户
                 * tenancyIdx : null
                 * note : null
                 * createTime : null
                 * updateTime : null
                 */

                private int id;
                private String code;
                private String name;
                private Object tenancyIdx;
                private Object note;
                private Object createTime;
                private Object updateTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
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
            }
        }

        public static class FactoryBean {
            /**
             * id : 48
             * code : 1
             * name : 重庆恒祥
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-17 19:37:33
             * updateTime : 2020-05-19 10:07:02
             */

            private int id;
            private String code;
            private String name;
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

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
        }

        public static class IsCheckStatusBean {
            /**
             * name : 勾选
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
    }
}
