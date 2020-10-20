package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/20
 */
public class Data_MySecurity {
    /**
     * total : 1
     * items : [{"id":22,"securitySn":"20201020210342878","securityType":{"id":20,"code":"security_0001","name":"灶台检查","tenancyIdx":null,"note":"","createTime":"2018-04-12 15:46:21","updateTime":"2019-08-29 09:18:58"},"customer":{"id":436422,"userId":"15877933794","name":"潘芬","identity":"  ","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"导入客户","createTime":"2006-05-06 18:22:00","updateTime":"2020-04-24 16:29:39","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"15877933794","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":2,"code":"00001","name":"普通住宅客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":null,"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"},"phone":"15877933794","sleepDays":null,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":"2020-10-13 11:05:26","dealTime":"2006-05-06 18:22:00","dealNumber":null,"distanceLevel":{"id":1,"code":"0001","name":"普通距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":{"id":188,"code":"km24","name":"呈贡门店","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}},"recvName":"潘芬","recvPhone":"15877933794","recvAddr":{"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"},"detail":"灶台火小","reserveTime":null,"visitStatus":{"name":"待回访","index":0},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"operUser":{"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-10-20 21:05:49","userGroup":{"id":1,"code":"00001","name":"管理员","tenancyIdx":null,"note":"系统管理员","createTime":"2017-11-03 14:34:42","updateTime":"2017-11-27 16:19:29"},"department":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"userPosition":null,"jobNumber":"1","mobilePhone":"1","officePhone":null,"email":null,"serviceStatus":{"name":"正常","index":0},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:05:49","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null},"dealedUser":{"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 21:06:10","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"},"jobNumber":"12","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:06:10","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null},"resloveInfo":null,"tenancyIdx":7,"cancelId":null,"cancelName":null,"cancelTime":null,"cancelInfo":"","createTime":"2020-10-20 21:03:42","updateTime":"2020-10-20 21:03:54","callInPhone":"00000000","processStatus":{"name":"处理中","index":1}}]
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
         * id : 22
         * securitySn : 20201020210342878
         * securityType : {"id":20,"code":"security_0001","name":"灶台检查","tenancyIdx":null,"note":"","createTime":"2018-04-12 15:46:21","updateTime":"2019-08-29 09:18:58"}
         * customer : {"id":436422,"userId":"15877933794","name":"潘芬","identity":"  ","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"导入客户","createTime":"2006-05-06 18:22:00","updateTime":"2020-04-24 16:29:39","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"15877933794","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":2,"code":"00001","name":"普通住宅客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":null,"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"},"phone":"15877933794","sleepDays":null,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":"2020-10-13 11:05:26","dealTime":"2006-05-06 18:22:00","dealNumber":null,"distanceLevel":{"id":1,"code":"0001","name":"普通距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":{"id":188,"code":"km24","name":"呈贡门店","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}}
         * recvName : 潘芬
         * recvPhone : 15877933794
         * recvAddr : {"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"}
         * detail : 灶台火小
         * reserveTime : null
         * visitStatus : {"name":"待回访","index":0}
         * department : {"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"}
         * operUser : {"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-10-20 21:05:49","userGroup":{"id":1,"code":"00001","name":"管理员","tenancyIdx":null,"note":"系统管理员","createTime":"2017-11-03 14:34:42","updateTime":"2017-11-27 16:19:29"},"department":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"userPosition":null,"jobNumber":"1","mobilePhone":"1","officePhone":null,"email":null,"serviceStatus":{"name":"正常","index":0},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:05:49","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null}
         * dealedUser : {"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 21:06:10","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"},"jobNumber":"12","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:06:10","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null}
         * resloveInfo : null
         * tenancyIdx : 7
         * cancelId : null
         * cancelName : null
         * cancelTime : null
         * cancelInfo :
         * createTime : 2020-10-20 21:03:42
         * updateTime : 2020-10-20 21:03:54
         * callInPhone : 00000000
         * processStatus : {"name":"处理中","index":1}
         */

        private int id;
        private String securitySn;
        private SecurityTypeBean securityType;
        private CustomerBean customer;
        private String recvName;
        private String recvPhone;
        private RecvAddrBean recvAddr;
        private String detail;
        private Object reserveTime;
        private VisitStatusBean visitStatus;
        private DepartmentBeanX department;
        private OperUserBean operUser;
        private DealedUserBean dealedUser;
        private Object resloveInfo;
        private int tenancyIdx;
        private Object cancelId;
        private Object cancelName;
        private Object cancelTime;
        private String cancelInfo;
        private String createTime;
        private String updateTime;
        private String callInPhone;
        private ProcessStatusBean processStatus;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSecuritySn() {
            return securitySn;
        }

        public void setSecuritySn(String securitySn) {
            this.securitySn = securitySn;
        }

        public SecurityTypeBean getSecurityType() {
            return securityType;
        }

        public void setSecurityType(SecurityTypeBean securityType) {
            this.securityType = securityType;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
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

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Object getReserveTime() {
            return reserveTime;
        }

        public void setReserveTime(Object reserveTime) {
            this.reserveTime = reserveTime;
        }

        public VisitStatusBean getVisitStatus() {
            return visitStatus;
        }

        public void setVisitStatus(VisitStatusBean visitStatus) {
            this.visitStatus = visitStatus;
        }

        public DepartmentBeanX getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentBeanX department) {
            this.department = department;
        }

        public OperUserBean getOperUser() {
            return operUser;
        }

        public void setOperUser(OperUserBean operUser) {
            this.operUser = operUser;
        }

        public DealedUserBean getDealedUser() {
            return dealedUser;
        }

        public void setDealedUser(DealedUserBean dealedUser) {
            this.dealedUser = dealedUser;
        }

        public Object getResloveInfo() {
            return resloveInfo;
        }

        public void setResloveInfo(Object resloveInfo) {
            this.resloveInfo = resloveInfo;
        }

        public int getTenancyIdx() {
            return tenancyIdx;
        }

        public void setTenancyIdx(int tenancyIdx) {
            this.tenancyIdx = tenancyIdx;
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

        public String getCancelInfo() {
            return cancelInfo;
        }

        public void setCancelInfo(String cancelInfo) {
            this.cancelInfo = cancelInfo;
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

        public String getCallInPhone() {
            return callInPhone;
        }

        public void setCallInPhone(String callInPhone) {
            this.callInPhone = callInPhone;
        }

        public ProcessStatusBean getProcessStatus() {
            return processStatus;
        }

        public void setProcessStatus(ProcessStatusBean processStatus) {
            this.processStatus = processStatus;
        }

        public static class SecurityTypeBean {
            /**
             * id : 20
             * code : security_0001
             * name : 灶台检查
             * tenancyIdx : null
             * note :
             * createTime : 2018-04-12 15:46:21
             * updateTime : 2019-08-29 09:18:58
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
             * number : 15877933794
             * haveCylinder : false
             * status : 0
             * settlementType : {"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerType : {"id":2,"code":"00001","name":"普通住宅客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerLevel : {"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerSource : null
             * customerCompany : {"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * address : {"country":"中国","province":"云南省","city":"昆明市","county":"呈贡区","detail":"呈钢小区F栋4单元301号"}
             * phone : 15877933794
             * sleepDays : null
             * leakLevelOneWanningTime : null
             * leakLevelTwoWanningTime : 2020-10-13 11:05:26
             * dealTime : 2006-05-06 18:22:00
             * dealNumber : null
             * distanceLevel : {"id":1,"code":"0001","name":"普通距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * refereeUserId : null
             * refereeUserName : null
             * department : {"id":188,"code":"km24","name":"呈贡门店","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
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
            private String number;
            private boolean haveCylinder;
            private int status;
            private SettlementTypeBean settlementType;
            private CustomerTypeBean customerType;
            private CustomerLevelBean customerLevel;
            private Object customerSource;
            private CustomerCompanyBean customerCompany;
            private AddressBean address;
            private String phone;
            private Object sleepDays;
            private Object leakLevelOneWanningTime;
            private String leakLevelTwoWanningTime;
            private String dealTime;
            private Object dealNumber;
            private DistanceLevelBean distanceLevel;
            private Object refereeUserId;
            private Object refereeUserName;
            private DepartmentBean department;

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

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public boolean isHaveCylinder() {
                return haveCylinder;
            }

            public void setHaveCylinder(boolean haveCylinder) {
                this.haveCylinder = haveCylinder;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public SettlementTypeBean getSettlementType() {
                return settlementType;
            }

            public void setSettlementType(SettlementTypeBean settlementType) {
                this.settlementType = settlementType;
            }

            public CustomerTypeBean getCustomerType() {
                return customerType;
            }

            public void setCustomerType(CustomerTypeBean customerType) {
                this.customerType = customerType;
            }

            public CustomerLevelBean getCustomerLevel() {
                return customerLevel;
            }

            public void setCustomerLevel(CustomerLevelBean customerLevel) {
                this.customerLevel = customerLevel;
            }

            public Object getCustomerSource() {
                return customerSource;
            }

            public void setCustomerSource(Object customerSource) {
                this.customerSource = customerSource;
            }

            public CustomerCompanyBean getCustomerCompany() {
                return customerCompany;
            }

            public void setCustomerCompany(CustomerCompanyBean customerCompany) {
                this.customerCompany = customerCompany;
            }

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Object getSleepDays() {
                return sleepDays;
            }

            public void setSleepDays(Object sleepDays) {
                this.sleepDays = sleepDays;
            }

            public Object getLeakLevelOneWanningTime() {
                return leakLevelOneWanningTime;
            }

            public void setLeakLevelOneWanningTime(Object leakLevelOneWanningTime) {
                this.leakLevelOneWanningTime = leakLevelOneWanningTime;
            }

            public String getLeakLevelTwoWanningTime() {
                return leakLevelTwoWanningTime;
            }

            public void setLeakLevelTwoWanningTime(String leakLevelTwoWanningTime) {
                this.leakLevelTwoWanningTime = leakLevelTwoWanningTime;
            }

            public String getDealTime() {
                return dealTime;
            }

            public void setDealTime(String dealTime) {
                this.dealTime = dealTime;
            }

            public Object getDealNumber() {
                return dealNumber;
            }

            public void setDealNumber(Object dealNumber) {
                this.dealNumber = dealNumber;
            }

            public DistanceLevelBean getDistanceLevel() {
                return distanceLevel;
            }

            public void setDistanceLevel(DistanceLevelBean distanceLevel) {
                this.distanceLevel = distanceLevel;
            }

            public Object getRefereeUserId() {
                return refereeUserId;
            }

            public void setRefereeUserId(Object refereeUserId) {
                this.refereeUserId = refereeUserId;
            }

            public Object getRefereeUserName() {
                return refereeUserName;
            }

            public void setRefereeUserName(Object refereeUserName) {
                this.refereeUserName = refereeUserName;
            }

            public DepartmentBean getDepartment() {
                return department;
            }

            public void setDepartment(DepartmentBean department) {
                this.department = department;
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

            public static class SettlementTypeBean {
                /**
                 * id : 4
                 * code : 00001
                 * name : 普通客户
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

            public static class CustomerTypeBean {
                /**
                 * id : 2
                 * code : 00001
                 * name : 普通住宅客户
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

            public static class CustomerLevelBean {
                /**
                 * id : 2
                 * code : 00001
                 * name : 一级客户
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

            public static class CustomerCompanyBean {
                /**
                 * id : 1502
                 * code : 00000
                 * name : 未定义
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

            public static class AddressBean {
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

            public static class DistanceLevelBean {
                /**
                 * id : 1
                 * code : 0001
                 * name : 普通距离
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

            public static class DepartmentBean {
                /**
                 * id : 188
                 * code : km24
                 * name : 呈贡门店
                 * parentDepartment : null
                 * lstSubDepartment : null
                 * tenancyIdx : null
                 * note : null
                 * createTime : null
                 * updateTime : null
                 */

                private int id;
                private String code;
                private String name;
                private Object parentDepartment;
                private Object lstSubDepartment;
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

                public Object getParentDepartment() {
                    return parentDepartment;
                }

                public void setParentDepartment(Object parentDepartment) {
                    this.parentDepartment = parentDepartment;
                }

                public Object getLstSubDepartment() {
                    return lstSubDepartment;
                }

                public void setLstSubDepartment(Object lstSubDepartment) {
                    this.lstSubDepartment = lstSubDepartment;
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

        public static class VisitStatusBean {
            /**
             * name : 待回访
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

        public static class DepartmentBeanX {
            /**
             * id : 102
             * code : md1
             * name : 门店一
             * parentDepartment : {"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"}
             * lstSubDepartment : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-11 11:56:55
             * updateTime : 2020-03-11 11:56:55
             */

            private int id;
            private String code;
            private String name;
            private ParentDepartmentBeanX parentDepartment;
            private Object lstSubDepartment;
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

            public ParentDepartmentBeanX getParentDepartment() {
                return parentDepartment;
            }

            public void setParentDepartment(ParentDepartmentBeanX parentDepartment) {
                this.parentDepartment = parentDepartment;
            }

            public Object getLstSubDepartment() {
                return lstSubDepartment;
            }

            public void setLstSubDepartment(Object lstSubDepartment) {
                this.lstSubDepartment = lstSubDepartment;
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

            public static class ParentDepartmentBeanX {
                /**
                 * id : 99
                 * code : LSB
                 * name : 零售部
                 * parentDepartment : {"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"}
                 * lstSubDepartment : null
                 * tenancyIdx : 7
                 * note :
                 * createTime : 2020-03-11 11:55:59
                 * updateTime : 2020-03-11 11:55:59
                 */

                private int id;
                private String code;
                private String name;
                private ParentDepartmentBean parentDepartment;
                private Object lstSubDepartment;
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

                public ParentDepartmentBean getParentDepartment() {
                    return parentDepartment;
                }

                public void setParentDepartment(ParentDepartmentBean parentDepartment) {
                    this.parentDepartment = parentDepartment;
                }

                public Object getLstSubDepartment() {
                    return lstSubDepartment;
                }

                public void setLstSubDepartment(Object lstSubDepartment) {
                    this.lstSubDepartment = lstSubDepartment;
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

                public static class ParentDepartmentBean {
                    /**
                     * id : 98
                     * code : root
                     * name : 百江培训
                     * parentDepartment : null
                     * lstSubDepartment : null
                     * tenancyIdx : 7
                     * note :
                     * createTime : 2020-03-11 11:52:37
                     * updateTime : 2020-03-11 11:52:37
                     */

                    private int id;
                    private String code;
                    private String name;
                    private Object parentDepartment;
                    private Object lstSubDepartment;
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

                    public Object getParentDepartment() {
                        return parentDepartment;
                    }

                    public void setParentDepartment(Object parentDepartment) {
                        this.parentDepartment = parentDepartment;
                    }

                    public Object getLstSubDepartment() {
                        return lstSubDepartment;
                    }

                    public void setLstSubDepartment(Object lstSubDepartment) {
                        this.lstSubDepartment = lstSubDepartment;
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
            }
        }

        public static class OperUserBean {
            /**
             * id : 246037
             * userId : bjpx_admin
             * name : 百江培训系统管理员
             * identity :
             * password : null
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-11 11:52:37
             * updateTime : 2020-10-20 21:05:49
             * userGroup : {"id":1,"code":"00001","name":"管理员","tenancyIdx":null,"note":"系统管理员","createTime":"2017-11-03 14:34:42","updateTime":"2017-11-27 16:19:29"}
             * department : {"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"}
             * userPosition : null
             * jobNumber : 1
             * mobilePhone : 1
             * officePhone : null
             * email : null
             * serviceStatus : {"name":"正常","index":0}
             * aliveStatus : {"name":"在线","index":1}
             * aliveUpdateTime : 2020-10-20 21:05:49
             * gasTakeOverStatus : {"name":"有用户卡|有标签","index":0}
             * logoutStatus : null
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
            private DepartmentBeanXX department;
            private Object userPosition;
            private String jobNumber;
            private String mobilePhone;
            private Object officePhone;
            private Object email;
            private ServiceStatusBean serviceStatus;
            private AliveStatusBean aliveStatus;
            private String aliveUpdateTime;
            private GasTakeOverStatusBean gasTakeOverStatus;
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

            public DepartmentBeanXX getDepartment() {
                return department;
            }

            public void setDepartment(DepartmentBeanXX department) {
                this.department = department;
            }

            public Object getUserPosition() {
                return userPosition;
            }

            public void setUserPosition(Object userPosition) {
                this.userPosition = userPosition;
            }

            public String getJobNumber() {
                return jobNumber;
            }

            public void setJobNumber(String jobNumber) {
                this.jobNumber = jobNumber;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
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

            public ServiceStatusBean getServiceStatus() {
                return serviceStatus;
            }

            public void setServiceStatus(ServiceStatusBean serviceStatus) {
                this.serviceStatus = serviceStatus;
            }

            public AliveStatusBean getAliveStatus() {
                return aliveStatus;
            }

            public void setAliveStatus(AliveStatusBean aliveStatus) {
                this.aliveStatus = aliveStatus;
            }

            public String getAliveUpdateTime() {
                return aliveUpdateTime;
            }

            public void setAliveUpdateTime(String aliveUpdateTime) {
                this.aliveUpdateTime = aliveUpdateTime;
            }

            public GasTakeOverStatusBean getGasTakeOverStatus() {
                return gasTakeOverStatus;
            }

            public void setGasTakeOverStatus(GasTakeOverStatusBean gasTakeOverStatus) {
                this.gasTakeOverStatus = gasTakeOverStatus;
            }

            public Object getLogoutStatus() {
                return logoutStatus;
            }

            public void setLogoutStatus(Object logoutStatus) {
                this.logoutStatus = logoutStatus;
            }

            public static class UserGroupBeanX {
                /**
                 * id : 1
                 * code : 00001
                 * name : 管理员
                 * tenancyIdx : null
                 * note : 系统管理员
                 * createTime : 2017-11-03 14:34:42
                 * updateTime : 2017-11-27 16:19:29
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

            public static class DepartmentBeanXX {
                /**
                 * id : 98
                 * code : root
                 * name : 百江培训
                 * parentDepartment : null
                 * lstSubDepartment : null
                 * tenancyIdx : 7
                 * note :
                 * createTime : 2020-03-11 11:52:37
                 * updateTime : 2020-03-11 11:52:37
                 */

                private int id;
                private String code;
                private String name;
                private Object parentDepartment;
                private Object lstSubDepartment;
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

                public Object getParentDepartment() {
                    return parentDepartment;
                }

                public void setParentDepartment(Object parentDepartment) {
                    this.parentDepartment = parentDepartment;
                }

                public Object getLstSubDepartment() {
                    return lstSubDepartment;
                }

                public void setLstSubDepartment(Object lstSubDepartment) {
                    this.lstSubDepartment = lstSubDepartment;
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

            public static class ServiceStatusBean {
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

            public static class AliveStatusBean {
                /**
                 * name : 在线
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

            public static class GasTakeOverStatusBean {
                /**
                 * name : 有用户卡|有标签
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
             * updateTime : 2020-10-20 21:06:10
             * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"}
             * department : {"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"}
             * userPosition : {"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"}
             * jobNumber : 12
             * mobilePhone : 11
             * officePhone : 11
             * email : null
             * serviceStatus : {"name":"禁用","index":1}
             * aliveStatus : {"name":"在线","index":1}
             * aliveUpdateTime : 2020-10-20 21:06:10
             * gasTakeOverStatus : {"name":"有用户卡|有标签","index":0}
             * logoutStatus : null
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
            private DepartmentBeanXXX department;
            private UserPositionBean userPosition;
            private String jobNumber;
            private String mobilePhone;
            private String officePhone;
            private Object email;
            private ServiceStatusBeanX serviceStatus;
            private AliveStatusBeanX aliveStatus;
            private String aliveUpdateTime;
            private GasTakeOverStatusBeanX gasTakeOverStatus;
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

            public DepartmentBeanXXX getDepartment() {
                return department;
            }

            public void setDepartment(DepartmentBeanXXX department) {
                this.department = department;
            }

            public UserPositionBean getUserPosition() {
                return userPosition;
            }

            public void setUserPosition(UserPositionBean userPosition) {
                this.userPosition = userPosition;
            }

            public String getJobNumber() {
                return jobNumber;
            }

            public void setJobNumber(String jobNumber) {
                this.jobNumber = jobNumber;
            }

            public String getMobilePhone() {
                return mobilePhone;
            }

            public void setMobilePhone(String mobilePhone) {
                this.mobilePhone = mobilePhone;
            }

            public String getOfficePhone() {
                return officePhone;
            }

            public void setOfficePhone(String officePhone) {
                this.officePhone = officePhone;
            }

            public Object getEmail() {
                return email;
            }

            public void setEmail(Object email) {
                this.email = email;
            }

            public ServiceStatusBeanX getServiceStatus() {
                return serviceStatus;
            }

            public void setServiceStatus(ServiceStatusBeanX serviceStatus) {
                this.serviceStatus = serviceStatus;
            }

            public AliveStatusBeanX getAliveStatus() {
                return aliveStatus;
            }

            public void setAliveStatus(AliveStatusBeanX aliveStatus) {
                this.aliveStatus = aliveStatus;
            }

            public String getAliveUpdateTime() {
                return aliveUpdateTime;
            }

            public void setAliveUpdateTime(String aliveUpdateTime) {
                this.aliveUpdateTime = aliveUpdateTime;
            }

            public GasTakeOverStatusBeanX getGasTakeOverStatus() {
                return gasTakeOverStatus;
            }

            public void setGasTakeOverStatus(GasTakeOverStatusBeanX gasTakeOverStatus) {
                this.gasTakeOverStatus = gasTakeOverStatus;
            }

            public Object getLogoutStatus() {
                return logoutStatus;
            }

            public void setLogoutStatus(Object logoutStatus) {
                this.logoutStatus = logoutStatus;
            }

            public static class UserGroupBeanXX {
                /**
                 * id : 3
                 * code : 00003
                 * name : 配送员
                 * tenancyIdx : null
                 * note : 配送员
                 * createTime : 2017-11-04 10:54:27
                 * updateTime : 2017-12-11 10:55:27
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

            public static class DepartmentBeanXXX {
                /**
                 * id : 102
                 * code : md1
                 * name : 门店一
                 * parentDepartment : {"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"}
                 * lstSubDepartment : null
                 * tenancyIdx : 7
                 * note :
                 * createTime : 2020-03-11 11:56:55
                 * updateTime : 2020-03-11 11:56:55
                 */

                private int id;
                private String code;
                private String name;
                private ParentDepartmentBeanXXX parentDepartment;
                private Object lstSubDepartment;
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

                public ParentDepartmentBeanXXX getParentDepartment() {
                    return parentDepartment;
                }

                public void setParentDepartment(ParentDepartmentBeanXXX parentDepartment) {
                    this.parentDepartment = parentDepartment;
                }

                public Object getLstSubDepartment() {
                    return lstSubDepartment;
                }

                public void setLstSubDepartment(Object lstSubDepartment) {
                    this.lstSubDepartment = lstSubDepartment;
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

                public static class ParentDepartmentBeanXXX {
                    /**
                     * id : 99
                     * code : LSB
                     * name : 零售部
                     * parentDepartment : {"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"}
                     * lstSubDepartment : null
                     * tenancyIdx : 7
                     * note :
                     * createTime : 2020-03-11 11:55:59
                     * updateTime : 2020-03-11 11:55:59
                     */

                    private int id;
                    private String code;
                    private String name;
                    private ParentDepartmentBeanXX parentDepartment;
                    private Object lstSubDepartment;
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

                    public ParentDepartmentBeanXX getParentDepartment() {
                        return parentDepartment;
                    }

                    public void setParentDepartment(ParentDepartmentBeanXX parentDepartment) {
                        this.parentDepartment = parentDepartment;
                    }

                    public Object getLstSubDepartment() {
                        return lstSubDepartment;
                    }

                    public void setLstSubDepartment(Object lstSubDepartment) {
                        this.lstSubDepartment = lstSubDepartment;
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

                    public static class ParentDepartmentBeanXX {
                        /**
                         * id : 98
                         * code : root
                         * name : 百江培训
                         * parentDepartment : null
                         * lstSubDepartment : null
                         * tenancyIdx : 7
                         * note :
                         * createTime : 2020-03-11 11:52:37
                         * updateTime : 2020-03-11 11:52:37
                         */

                        private int id;
                        private String code;
                        private String name;
                        private Object parentDepartment;
                        private Object lstSubDepartment;
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

                        public Object getParentDepartment() {
                            return parentDepartment;
                        }

                        public void setParentDepartment(Object parentDepartment) {
                            this.parentDepartment = parentDepartment;
                        }

                        public Object getLstSubDepartment() {
                            return lstSubDepartment;
                        }

                        public void setLstSubDepartment(Object lstSubDepartment) {
                            this.lstSubDepartment = lstSubDepartment;
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
                }
            }

            public static class UserPositionBean {
                /**
                 * id : 1280
                 * userIdx : 246157
                 * longitude : 116.397741
                 * latitude : 39.849646
                 * createTime : 2020-03-11 14:46:11
                 * updateTime : 2020-10-11 12:13:22
                 */

                private int id;
                private int userIdx;
                private double longitude;
                private double latitude;
                private String createTime;
                private String updateTime;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getUserIdx() {
                    return userIdx;
                }

                public void setUserIdx(int userIdx) {
                    this.userIdx = userIdx;
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

            public static class ServiceStatusBeanX {
                /**
                 * name : 禁用
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

            public static class AliveStatusBeanX {
                /**
                 * name : 在线
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

            public static class GasTakeOverStatusBeanX {
                /**
                 * name : 有用户卡|有标签
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
        }

        public static class ProcessStatusBean {
            /**
             * name : 处理中
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
