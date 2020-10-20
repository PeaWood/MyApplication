package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/20
 */
public class Data_Order {
    /**
     * total : 250
     * items : [{"id":32818,"orderSn":"20200928102443750-464","callInPhone":"00000000","customer":{"id":436320,"userId":"5274196324","name":"方庆云","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-24 14:45:40","updateTime":"2020-04-24 14:45:40","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"5274196324","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":{"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"phone":"00000000","sleepDays":null,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":null,"dealTime":null,"dealNumber":"","distanceLevel":{"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":null},"originalAmount":120,"orderAmount":110,"refoundSum":0,"payType":null,"couponCount":null,"orderStatus":1,"payStatus":{"name":"待支付","index":0},"invoiceStatus":{"name":"待开发票","index":0},"urgent":false,"timeSpan":32360,"accessType":{"name":"客服接入","index":1},"orderTriggerType":{"name":"普通订单","index":0},"recvAddr":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"recvLongitude":102.746662,"recvLatitude":25.168436,"visitStatus":{"name":"待回访","index":0},"recvName":"方庆云","recvPhone":"00000000","reserveTime":null,"comment":null,"dispatcher":{"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 21:44:13","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"},"jobNumber":"12","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:44:13","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null},"dispatcherStartTime":"2020-09-28 10:28:08","orderServiceQuality":null,"recycleGasCylinder":null,"deliveryGasCylinder":null,"orderOpHistoryList":null,"gasRefoundDetailList":[],"tenancyIdx":7,"note":null,"deliveryTime":null,"reviceTime":"2020-09-28 10:28:08","payTime":null,"completeTime":null,"createTime":"2020-09-28 10:24:43","updateTime":"2020-09-28 10:28:08","orderDetailList":[{"id":34539,"orderIdx":32818,"originalPrice":120,"goods":{"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":25,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-10-17 11:31:40"},"dealPrice":110,"quantity":1,"subtotal":110,"dispatchFee":0,"tenancyIdx":7,"createTime":"2020-09-28 10:24:43","updateTime":"2020-09-28 10:24:43"}],"dispatchFee":0,"operUser":{"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":"111111","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-10-20 21:44:49","userGroup":null}}]
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
         * id : 32818
         * orderSn : 20200928102443750-464
         * callInPhone : 00000000
         * customer : {"id":436320,"userId":"5274196324","name":"方庆云","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-24 14:45:40","updateTime":"2020-04-24 14:45:40","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"5274196324","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":{"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"phone":"00000000","sleepDays":null,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":null,"dealTime":null,"dealNumber":"","distanceLevel":{"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":null}
         * originalAmount : 120
         * orderAmount : 110
         * refoundSum : 0
         * payType : null
         * couponCount : null
         * orderStatus : 1
         * payStatus : {"name":"待支付","index":0}
         * invoiceStatus : {"name":"待开发票","index":0}
         * urgent : false
         * timeSpan : 32360
         * accessType : {"name":"客服接入","index":1}
         * orderTriggerType : {"name":"普通订单","index":0}
         * recvAddr : {"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"}
         * recvLongitude : 102.746662
         * recvLatitude : 25.168436
         * visitStatus : {"name":"待回访","index":0}
         * recvName : 方庆云
         * recvPhone : 00000000
         * reserveTime : null
         * comment : null
         * dispatcher : {"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 21:44:13","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"},"jobNumber":"12","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 21:44:13","gasTakeOverStatus":{"name":"有用户卡|有标签","index":0},"logoutStatus":null}
         * dispatcherStartTime : 2020-09-28 10:28:08
         * orderServiceQuality : null
         * recycleGasCylinder : null
         * deliveryGasCylinder : null
         * orderOpHistoryList : null
         * gasRefoundDetailList : []
         * tenancyIdx : 7
         * note : null
         * deliveryTime : null
         * reviceTime : 2020-09-28 10:28:08
         * payTime : null
         * completeTime : null
         * createTime : 2020-09-28 10:24:43
         * updateTime : 2020-09-28 10:28:08
         * orderDetailList : [{"id":34539,"orderIdx":32818,"originalPrice":120,"goods":{"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":25,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-10-17 11:31:40"},"dealPrice":110,"quantity":1,"subtotal":110,"dispatchFee":0,"tenancyIdx":7,"createTime":"2020-09-28 10:24:43","updateTime":"2020-09-28 10:24:43"}]
         * dispatchFee : 0
         * operUser : {"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":"111111","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-10-20 21:44:49","userGroup":null}
         */

        private int id;
        private String orderSn;
        private String callInPhone;
        private CustomerBean customer;
        private int originalAmount;
        private int orderAmount;
        private int refoundSum;
        private Object payType;
        private Object couponCount;
        private int orderStatus;
        private PayStatusBean payStatus;
        private InvoiceStatusBean invoiceStatus;
        private boolean urgent;
        private int timeSpan;
        private AccessTypeBean accessType;
        private OrderTriggerTypeBean orderTriggerType;
        private RecvAddrBean recvAddr;
        private double recvLongitude;
        private double recvLatitude;
        private VisitStatusBean visitStatus;
        private String recvName;
        private String recvPhone;
        private Object reserveTime;
        private Object comment;
        private DispatcherBean dispatcher;
        private String dispatcherStartTime;
        private Object orderServiceQuality;
        private Object recycleGasCylinder;
        private Object deliveryGasCylinder;
        private Object orderOpHistoryList;
        private int tenancyIdx;
        private Object note;
        private Object deliveryTime;
        private String reviceTime;
        private Object payTime;
        private Object completeTime;
        private String createTime;
        private String updateTime;
        private int dispatchFee;
        private OperUserBean operUser;
        private List<?> gasRefoundDetailList;
        private List<OrderDetailListBean> orderDetailList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getCallInPhone() {
            return callInPhone;
        }

        public void setCallInPhone(String callInPhone) {
            this.callInPhone = callInPhone;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public int getOriginalAmount() {
            return originalAmount;
        }

        public void setOriginalAmount(int originalAmount) {
            this.originalAmount = originalAmount;
        }

        public int getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(int orderAmount) {
            this.orderAmount = orderAmount;
        }

        public int getRefoundSum() {
            return refoundSum;
        }

        public void setRefoundSum(int refoundSum) {
            this.refoundSum = refoundSum;
        }

        public Object getPayType() {
            return payType;
        }

        public void setPayType(Object payType) {
            this.payType = payType;
        }

        public Object getCouponCount() {
            return couponCount;
        }

        public void setCouponCount(Object couponCount) {
            this.couponCount = couponCount;
        }

        public int getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(int orderStatus) {
            this.orderStatus = orderStatus;
        }

        public PayStatusBean getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(PayStatusBean payStatus) {
            this.payStatus = payStatus;
        }

        public InvoiceStatusBean getInvoiceStatus() {
            return invoiceStatus;
        }

        public void setInvoiceStatus(InvoiceStatusBean invoiceStatus) {
            this.invoiceStatus = invoiceStatus;
        }

        public boolean isUrgent() {
            return urgent;
        }

        public void setUrgent(boolean urgent) {
            this.urgent = urgent;
        }

        public int getTimeSpan() {
            return timeSpan;
        }

        public void setTimeSpan(int timeSpan) {
            this.timeSpan = timeSpan;
        }

        public AccessTypeBean getAccessType() {
            return accessType;
        }

        public void setAccessType(AccessTypeBean accessType) {
            this.accessType = accessType;
        }

        public OrderTriggerTypeBean getOrderTriggerType() {
            return orderTriggerType;
        }

        public void setOrderTriggerType(OrderTriggerTypeBean orderTriggerType) {
            this.orderTriggerType = orderTriggerType;
        }

        public RecvAddrBean getRecvAddr() {
            return recvAddr;
        }

        public void setRecvAddr(RecvAddrBean recvAddr) {
            this.recvAddr = recvAddr;
        }

        public double getRecvLongitude() {
            return recvLongitude;
        }

        public void setRecvLongitude(double recvLongitude) {
            this.recvLongitude = recvLongitude;
        }

        public double getRecvLatitude() {
            return recvLatitude;
        }

        public void setRecvLatitude(double recvLatitude) {
            this.recvLatitude = recvLatitude;
        }

        public VisitStatusBean getVisitStatus() {
            return visitStatus;
        }

        public void setVisitStatus(VisitStatusBean visitStatus) {
            this.visitStatus = visitStatus;
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

        public Object getReserveTime() {
            return reserveTime;
        }

        public void setReserveTime(Object reserveTime) {
            this.reserveTime = reserveTime;
        }

        public Object getComment() {
            return comment;
        }

        public void setComment(Object comment) {
            this.comment = comment;
        }

        public DispatcherBean getDispatcher() {
            return dispatcher;
        }

        public void setDispatcher(DispatcherBean dispatcher) {
            this.dispatcher = dispatcher;
        }

        public String getDispatcherStartTime() {
            return dispatcherStartTime;
        }

        public void setDispatcherStartTime(String dispatcherStartTime) {
            this.dispatcherStartTime = dispatcherStartTime;
        }

        public Object getOrderServiceQuality() {
            return orderServiceQuality;
        }

        public void setOrderServiceQuality(Object orderServiceQuality) {
            this.orderServiceQuality = orderServiceQuality;
        }

        public Object getRecycleGasCylinder() {
            return recycleGasCylinder;
        }

        public void setRecycleGasCylinder(Object recycleGasCylinder) {
            this.recycleGasCylinder = recycleGasCylinder;
        }

        public Object getDeliveryGasCylinder() {
            return deliveryGasCylinder;
        }

        public void setDeliveryGasCylinder(Object deliveryGasCylinder) {
            this.deliveryGasCylinder = deliveryGasCylinder;
        }

        public Object getOrderOpHistoryList() {
            return orderOpHistoryList;
        }

        public void setOrderOpHistoryList(Object orderOpHistoryList) {
            this.orderOpHistoryList = orderOpHistoryList;
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

        public Object getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(Object deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getReviceTime() {
            return reviceTime;
        }

        public void setReviceTime(String reviceTime) {
            this.reviceTime = reviceTime;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public Object getCompleteTime() {
            return completeTime;
        }

        public void setCompleteTime(Object completeTime) {
            this.completeTime = completeTime;
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

        public int getDispatchFee() {
            return dispatchFee;
        }

        public void setDispatchFee(int dispatchFee) {
            this.dispatchFee = dispatchFee;
        }

        public OperUserBean getOperUser() {
            return operUser;
        }

        public void setOperUser(OperUserBean operUser) {
            this.operUser = operUser;
        }

        public List<?> getGasRefoundDetailList() {
            return gasRefoundDetailList;
        }

        public void setGasRefoundDetailList(List<?> gasRefoundDetailList) {
            this.gasRefoundDetailList = gasRefoundDetailList;
        }

        public List<OrderDetailListBean> getOrderDetailList() {
            return orderDetailList;
        }

        public void setOrderDetailList(List<OrderDetailListBean> orderDetailList) {
            this.orderDetailList = orderDetailList;
        }

        public static class CustomerBean {
            /**
             * id : 436320
             * userId : 5274196324
             * name : 方庆云
             * identity :
             * password : null
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-04-24 14:45:40
             * updateTime : 2020-04-24 14:45:40
             * userGroup : {"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * number : 5274196324
             * haveCylinder : false
             * status : 0
             * settlementType : {"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerType : {"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerLevel : {"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerSource : {"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * customerCompany : {"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * address : {"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"}
             * phone : 00000000
             * sleepDays : null
             * leakLevelOneWanningTime : null
             * leakLevelTwoWanningTime : null
             * dealTime : null
             * dealNumber :
             * distanceLevel : {"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null}
             * refereeUserId : null
             * refereeUserName : null
             * department : null
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
            private CustomerSourceBean customerSource;
            private CustomerCompanyBean customerCompany;
            private AddressBean address;
            private String phone;
            private Object sleepDays;
            private Object leakLevelOneWanningTime;
            private Object leakLevelTwoWanningTime;
            private Object dealTime;
            private String dealNumber;
            private DistanceLevelBean distanceLevel;
            private Object refereeUserId;
            private Object refereeUserName;
            private Object department;

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

            public CustomerSourceBean getCustomerSource() {
                return customerSource;
            }

            public void setCustomerSource(CustomerSourceBean customerSource) {
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

            public Object getLeakLevelTwoWanningTime() {
                return leakLevelTwoWanningTime;
            }

            public void setLeakLevelTwoWanningTime(Object leakLevelTwoWanningTime) {
                this.leakLevelTwoWanningTime = leakLevelTwoWanningTime;
            }

            public Object getDealTime() {
                return dealTime;
            }

            public void setDealTime(Object dealTime) {
                this.dealTime = dealTime;
            }

            public String getDealNumber() {
                return dealNumber;
            }

            public void setDealNumber(String dealNumber) {
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

            public Object getDepartment() {
                return department;
            }

            public void setDepartment(Object department) {
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
                 * id : 3
                 * code : 00002
                 * name : 餐饮客户
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

            public static class CustomerSourceBean {
                /**
                 * id : 3
                 * code : 00004
                 * name : 别人介绍
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

            public static class DistanceLevelBean {
                /**
                 * id : 2
                 * code : 0002
                 * name : 远距离
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

        public static class PayStatusBean {
            /**
             * name : 待支付
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

        public static class InvoiceStatusBean {
            /**
             * name : 待开发票
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

        public static class AccessTypeBean {
            /**
             * name : 客服接入
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

        public static class OrderTriggerTypeBean {
            /**
             * name : 普通订单
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

        public static class RecvAddrBean {
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

        public static class DispatcherBean {
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
             * updateTime : 2020-10-20 21:44:13
             * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"}
             * department : {"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"}
             * userPosition : {"id":1280,"userIdx":246157,"longitude":116.397741,"latitude":39.849646,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-11 12:13:22"}
             * jobNumber : 12
             * mobilePhone : 11
             * officePhone : 11
             * email : null
             * serviceStatus : {"name":"禁用","index":1}
             * aliveStatus : {"name":"在线","index":1}
             * aliveUpdateTime : 2020-10-20 21:44:13
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
            private DepartmentBean department;
            private UserPositionBean userPosition;
            private String jobNumber;
            private String mobilePhone;
            private String officePhone;
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

            public DepartmentBean getDepartment() {
                return department;
            }

            public void setDepartment(DepartmentBean department) {
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

            public static class DepartmentBean {
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

            public static class ServiceStatusBean {
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

        public static class OperUserBean {
            /**
             * id : 246037
             * userId : bjpx_admin
             * name : 百江培训系统管理员
             * identity :
             * password : 111111
             * wxOpenId : null
             * tenancyIdx : 7
             * note :
             * createTime : 2020-03-11 11:52:37
             * updateTime : 2020-10-20 21:44:49
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

        public static class OrderDetailListBean {
            /**
             * id : 34539
             * orderIdx : 32818
             * originalPrice : 120
             * goods : {"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":25,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-10-17 11:31:40"}
             * dealPrice : 110
             * quantity : 1
             * subtotal : 110
             * dispatchFee : 0
             * tenancyIdx : 7
             * createTime : 2020-09-28 10:24:43
             * updateTime : 2020-09-28 10:24:43
             */

            private int id;
            private int orderIdx;
            private int originalPrice;
            private GoodsBean goods;
            private int dealPrice;
            private int quantity;
            private int subtotal;
            private int dispatchFee;
            private int tenancyIdx;
            private String createTime;
            private String updateTime;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getOrderIdx() {
                return orderIdx;
            }

            public void setOrderIdx(int orderIdx) {
                this.orderIdx = orderIdx;
            }

            public int getOriginalPrice() {
                return originalPrice;
            }

            public void setOriginalPrice(int originalPrice) {
                this.originalPrice = originalPrice;
            }

            public GoodsBean getGoods() {
                return goods;
            }

            public void setGoods(GoodsBean goods) {
                this.goods = goods;
            }

            public int getDealPrice() {
                return dealPrice;
            }

            public void setDealPrice(int dealPrice) {
                this.dealPrice = dealPrice;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public int getSubtotal() {
                return subtotal;
            }

            public void setSubtotal(int subtotal) {
                this.subtotal = subtotal;
            }

            public int getDispatchFee() {
                return dispatchFee;
            }

            public void setDispatchFee(int dispatchFee) {
                this.dispatchFee = dispatchFee;
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

            public static class GoodsBean {
                /**
                 * id : 24
                 * code : BJ001
                 * name : 15KG
                 * specifications : 15
                 * unit : 公斤
                 * weight : 15
                 * price : 25
                 * realPrice : null
                 * gasCylinderSpec : {"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"}
                 * status : 0
                 * lifeExpectancy : 111
                 * goodsType : {"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"}
                 * area : {"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"}
                 * tenancyIdx : 7
                 * note : null
                 * createTime : 2020-03-11 13:22:30
                 * updateTime : 2020-10-17 11:31:40
                 */

                private int id;
                private String code;
                private String name;
                private String specifications;
                private String unit;
                private int weight;
                private int price;
                private Object realPrice;
                private GasCylinderSpecBean gasCylinderSpec;
                private int status;
                private int lifeExpectancy;
                private GoodsTypeBean goodsType;
                private AreaBean area;
                private int tenancyIdx;
                private Object note;
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

                public String getSpecifications() {
                    return specifications;
                }

                public void setSpecifications(String specifications) {
                    this.specifications = specifications;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }

                public int getWeight() {
                    return weight;
                }

                public void setWeight(int weight) {
                    this.weight = weight;
                }

                public int getPrice() {
                    return price;
                }

                public void setPrice(int price) {
                    this.price = price;
                }

                public Object getRealPrice() {
                    return realPrice;
                }

                public void setRealPrice(Object realPrice) {
                    this.realPrice = realPrice;
                }

                public GasCylinderSpecBean getGasCylinderSpec() {
                    return gasCylinderSpec;
                }

                public void setGasCylinderSpec(GasCylinderSpecBean gasCylinderSpec) {
                    this.gasCylinderSpec = gasCylinderSpec;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getLifeExpectancy() {
                    return lifeExpectancy;
                }

                public void setLifeExpectancy(int lifeExpectancy) {
                    this.lifeExpectancy = lifeExpectancy;
                }

                public GoodsTypeBean getGoodsType() {
                    return goodsType;
                }

                public void setGoodsType(GoodsTypeBean goodsType) {
                    this.goodsType = goodsType;
                }

                public AreaBean getArea() {
                    return area;
                }

                public void setArea(AreaBean area) {
                    this.area = area;
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

                public static class GasCylinderSpecBean {
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

                public static class GoodsTypeBean {
                    /**
                     * id : 44
                     * code : 001
                     * name : 液化气
                     * tenancyIdx : 7
                     * note :
                     * createTime : 2020-03-11 13:21:36
                     * updateTime : 2020-03-11 13:21:53
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

                public static class AreaBean {
                    /**
                     * id : 28
                     * country : 中国
                     * province : 云南省
                     * city : 昆明市
                     * county : 全部区
                     * tenancyIdx : 7
                     * note : null
                     * createTime : 2020-03-11 13:22:30
                     * updateTime : 2020-03-11 13:22:30
                     */

                    private int id;
                    private String country;
                    private String province;
                    private String city;
                    private String county;
                    private int tenancyIdx;
                    private Object note;
                    private String createTime;
                    private String updateTime;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

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
                }
            }
        }
    }
}
