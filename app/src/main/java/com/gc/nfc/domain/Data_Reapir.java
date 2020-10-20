package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/20
 */
public class Data_Reapir {
    /**
     * total : 1
     * items : [{"id":3,"repairSn":"20201020222202691-0123","repairType":{"name":"托盘离线","index":0},"repairResultType":{"name":"损坏，需更换","index":0},"customer":{"id":436422,"userId":"15877933794","name":"潘芬","identity":"  ","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"导入客户","createTime":"2006-05-06 18:22:00","updateTime":"2020-04-24 16:29:39","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}},"dutyUser":{"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 22:27:35","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}},"number":"B15A000123","deviceId":712,"recvName":"潘芬","recvPhone":"15877933794","recvAddr":{"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"},"processStatus":{"name":"已解决","index":2},"dealedUser":{"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 22:27:35","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}},"resloveInfo":"","tenancyIdx":7,"createTime":"2020-10-20 22:22:02","updateTime":"2020-10-20 22:24:51","cancelId":null,"cancelName":null,"cancelTime":null,"cancelInfo":null}]
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
         * id : 3
         * repairSn : 20201020222202691-0123
         * repairType : {"name":"托盘离线","index":0}
         * repairResultType : {"name":"损坏，需更换","index":0}
         * customer : {"id":436422,"userId":"15877933794","name":"潘芬","identity":"  ","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"导入客户","createTime":"2006-05-06 18:22:00","updateTime":"2020-04-24 16:29:39","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}}
         * dutyUser : {"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 22:27:35","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}}
         * number : B15A000123
         * deviceId : 712
         * recvName : 潘芬
         * recvPhone : 15877933794
         * recvAddr : {"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"}
         * processStatus : {"name":"已解决","index":2}
         * dealedUser : {"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 22:27:35","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}}
         * resloveInfo :
         * tenancyIdx : 7
         * createTime : 2020-10-20 22:22:02
         * updateTime : 2020-10-20 22:24:51
         * cancelId : null
         * cancelName : null
         * cancelTime : null
         * cancelInfo : null
         */

        private int id;
        private String repairSn;
        private RepairTypeBean repairType;
        private RepairResultTypeBean repairResultType;
        private CustomerBean customer;
        private DutyUserBean dutyUser;
        private String number;
        private int deviceId;
        private String recvName;
        private String recvPhone;
        private RecvAddrBean recvAddr;
        private ProcessStatusBean processStatus;
        private DealedUserBean dealedUser;
        private String resloveInfo;
        private int tenancyIdx;
        private String createTime;
        private String updateTime;
        private Object cancelId;
        private Object cancelName;
        private Object cancelTime;
        private Object cancelInfo;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRepairSn() {
            return repairSn;
        }

        public void setRepairSn(String repairSn) {
            this.repairSn = repairSn;
        }

        public RepairTypeBean getRepairType() {
            return repairType;
        }

        public void setRepairType(RepairTypeBean repairType) {
            this.repairType = repairType;
        }

        public RepairResultTypeBean getRepairResultType() {
            return repairResultType;
        }

        public void setRepairResultType(RepairResultTypeBean repairResultType) {
            this.repairResultType = repairResultType;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public DutyUserBean getDutyUser() {
            return dutyUser;
        }

        public void setDutyUser(DutyUserBean dutyUser) {
            this.dutyUser = dutyUser;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getRecvName() {
            return recvName;
        }

        public void setRecvName(String recvName) {
            this.recvName = recvName;
        }

        public String getRecvPhone() {
            return recvPhone;
        }

        public void setRecvPhone(String recvPhone) {
            this.recvPhone = recvPhone;
        }

        public RecvAddrBean getRecvAddr() {
            return recvAddr;
        }

        public void setRecvAddr(RecvAddrBean recvAddr) {
            this.recvAddr = recvAddr;
        }

        public ProcessStatusBean getProcessStatus() {
            return processStatus;
        }

        public void setProcessStatus(ProcessStatusBean processStatus) {
            this.processStatus = processStatus;
        }

        public DealedUserBean getDealedUser() {
            return dealedUser;
        }

        public void setDealedUser(DealedUserBean dealedUser) {
            this.dealedUser = dealedUser;
        }

        public String getResloveInfo() {
            return resloveInfo;
        }

        public void setResloveInfo(String resloveInfo) {
            this.resloveInfo = resloveInfo;
        }

        public int getTenancyIdx() {
            return tenancyIdx;
        }

        public void setTenancyIdx(int tenancyIdx) {
            this.tenancyIdx = tenancyIdx;
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

        public Object getCancelId() {
            return cancelId;
        }

        public void setCancelId(Object cancelId) {
            this.cancelId = cancelId;
        }

        public Object getCancelName() {
            return cancelName;
        }

        public void setCancelName(Object cancelName) {
            this.cancelName = cancelName;
        }

        public Object getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(Object cancelTime) {
            this.cancelTime = cancelTime;
        }

        public Object getCancelInfo() {
            return cancelInfo;
        }

        public void setCancelInfo(Object cancelInfo) {
            this.cancelInfo = cancelInfo;
        }

        public static class RepairTypeBean {
            /**
             * name : 托盘离线
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

        public static class RepairResultTypeBean {
            /**
             * name : 损坏，需更换
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

        public static class CustomerBean {
            /**
             * id : 436422
             * userId : 15877933794
             * name : 潘芬
             * identity :
             * password : null
             * wxOpenId : null
             * tenancyIdx : 7
             * note : 导入客户
             * createTime : 2006-05-06 18:22:00
             * updateTime : 2020-04-24 16:29:39
             * userGroup : {"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             */

            private int id;
            private String userId;
            private String name;
            private String identity;
            private Object password;
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

        public static class DutyUserBean {
            /**
             * id : 246157
             * userId : psy
             * name : 123
             * identity : 1
             * password : null
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-11 12:31:48
             * updateTime : 2020-10-20 22:27:35
             * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             */

            private int id;
            private String userId;
            private String name;
            private String identity;
            private Object password;
            private Object wxOpenId;
            private int tenancyIdx;
            private String note;
            private String createTime;
            private String updateTime;
            private UserGroupBeanX userGroup;

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

            public UserGroupBeanX getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(UserGroupBeanX userGroup) {
                this.userGroup = userGroup;
            }

            public static class UserGroupBeanX {
                /**
                 * id : 3
                 * code : 00003
                 * name : 配送员
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

        public static class RecvAddrBean {
            /**
             * country : 中国
             * province : 云南省
             * city : 昆明市
             * county : 呈贡区
             * detail : 呈钢小区F栋4单元301号
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

        public static class ProcessStatusBean {
            /**
             * name : 已解决
             * index : 2
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

        public static class DealedUserBean {
            /**
             * id : 246157
             * userId : psy
             * name : 123
             * identity : 1
             * password : null
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-11 12:31:48
             * updateTime : 2020-10-20 22:27:35
             * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             */

            private int id;
            private String userId;
            private String name;
            private String identity;
            private Object password;
            private Object wxOpenId;
            private int tenancyIdx;
            private String note;
            private String createTime;
            private String updateTime;
            private UserGroupBeanXX userGroup;

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

            public UserGroupBeanXX getUserGroup() {
                return userGroup;
            }

            public void setUserGroup(UserGroupBeanXX userGroup) {
                this.userGroup = userGroup;
            }

            public static class UserGroupBeanXX {
                /**
                 * id : 3
                 * code : 00003
                 * name : 配送员
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
    }
}
