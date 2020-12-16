package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/9/26
 */
public class Data_TaskOrders {
    /**
     * total : 67
     * items : [{"id":"380821","taskName":"等待接单","process":{"id":"380815","workFlowType":"GAS_ORDER_FLOW","buinessKey":"20200920175004080-465"},"object":{"id":32813,"orderSn":"20200920175004080-465","callInPhone":"00000000","customer":{"id":436320,"userId":"5274196324","name":"方庆云","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-24 14:45:40","updateTime":"2020-04-24 14:45:40","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"5274196324","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":{"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"phone":"00000000","sleepDays":6,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":null,"dealTime":null,"dealNumber":"","distanceLevel":{"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":null},"originalAmount":120,"orderAmount":110,"refoundSum":0,"payType":null,"couponCount":null,"orderStatus":0,"payStatus":{"name":"待支付","index":0},"invoiceStatus":{"name":"待开发票","index":0},"urgent":false,"timeSpan":8860,"accessType":{"name":"客服接入","index":1},"orderTriggerType":{"name":"普通订单","index":0},"recvAddr":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"recvLongitude":102.746662,"recvLatitude":25.168436,"visitStatus":{"name":"待回访","index":0},"recvName":"方庆云","recvPhone":"00000000","reserveTime":null,"comment":null,"dispatcher":null,"dispatcherStartTime":null,"orderServiceQuality":null,"recycleGasCylinder":null,"deliveryGasCylinder":null,"orderOpHistoryList":null,"gasRefoundDetailList":[],"tenancyIdx":7,"note":null,"deliveryTime":null,"reviceTime":null,"payTime":null,"completeTime":null,"createTime":"2020-09-20 17:50:04","updateTime":"2020-09-20 17:50:04","orderDetailList":[{"id":34534,"orderIdx":32813,"originalPrice":120,"goods":{"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":120,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"dealPrice":110,"quantity":1,"subtotal":110,"dispatchFee":0,"tenancyIdx":7,"createTime":"2020-09-20 17:50:04","updateTime":"2020-09-20 17:50:04"}],"dispatchFee":0,"operUser":{"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":"111111","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-09-25 17:54:24","userGroup":null}}}]
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
         * id : 380821
         * taskName : 等待接单
         * process : {"id":"380815","workFlowType":"GAS_ORDER_FLOW","buinessKey":"20200920175004080-465"}
         * object : {"id":32813,"orderSn":"20200920175004080-465","callInPhone":"00000000","customer":{"id":436320,"userId":"5274196324","name":"方庆云","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-24 14:45:40","updateTime":"2020-04-24 14:45:40","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"5274196324","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":{"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"phone":"00000000","sleepDays":6,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":null,"dealTime":null,"dealNumber":"","distanceLevel":{"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":null},"originalAmount":120,"orderAmount":110,"refoundSum":0,"payType":null,"couponCount":null,"orderStatus":0,"payStatus":{"name":"待支付","index":0},"invoiceStatus":{"name":"待开发票","index":0},"urgent":false,"timeSpan":8860,"accessType":{"name":"客服接入","index":1},"orderTriggerType":{"name":"普通订单","index":0},"recvAddr":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"recvLongitude":102.746662,"recvLatitude":25.168436,"visitStatus":{"name":"待回访","index":0},"recvName":"方庆云","recvPhone":"00000000","reserveTime":null,"comment":null,"dispatcher":null,"dispatcherStartTime":null,"orderServiceQuality":null,"recycleGasCylinder":null,"deliveryGasCylinder":null,"orderOpHistoryList":null,"gasRefoundDetailList":[],"tenancyIdx":7,"note":null,"deliveryTime":null,"reviceTime":null,"payTime":null,"completeTime":null,"createTime":"2020-09-20 17:50:04","updateTime":"2020-09-20 17:50:04","orderDetailList":[{"id":34534,"orderIdx":32813,"originalPrice":120,"goods":{"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":120,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"dealPrice":110,"quantity":1,"subtotal":110,"dispatchFee":0,"tenancyIdx":7,"createTime":"2020-09-20 17:50:04","updateTime":"2020-09-20 17:50:04"}],"dispatchFee":0,"operUser":{"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":"111111","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-09-25 17:54:24","userGroup":null}}
         */

        private String id;
        private String taskName;
        private ProcessBean process;
        private ObjectBean object;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public ProcessBean getProcess() {
            return process;
        }

        public void setProcess(ProcessBean process) {
            this.process = process;
        }

        public ObjectBean getObject() {
            return object;
        }

        public void setObject(ObjectBean object) {
            this.object = object;
        }

        public static class ProcessBean {
            /**
             * id : 380815
             * workFlowType : GAS_ORDER_FLOW
             * buinessKey : 20200920175004080-465
             */

            private String id;
            private String workFlowType;
            private String buinessKey;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getWorkFlowType() {
                return workFlowType;
            }

            public void setWorkFlowType(String workFlowType) {
                this.workFlowType = workFlowType;
            }

            public String getBuinessKey() {
                return buinessKey;
            }

            public void setBuinessKey(String buinessKey) {
                this.buinessKey = buinessKey;
            }
        }

        public static class ObjectBean {
            /**
             * id : 32813
             * orderSn : 20200920175004080-465
             * callInPhone : 00000000
             * customer : {"id":436320,"userId":"5274196324","name":"方庆云","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-04-24 14:45:40","updateTime":"2020-04-24 14:45:40","userGroup":{"id":4,"code":"00004","name":"客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"number":"5274196324","haveCylinder":false,"status":0,"settlementType":{"id":4,"code":"00001","name":"普通客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerType":{"id":3,"code":"00002","name":"餐饮客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerLevel":{"id":2,"code":"00001","name":"一级客户","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerSource":{"id":3,"code":"00004","name":"别人介绍","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"customerCompany":{"id":1502,"code":"00000","name":"未定义","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"address":{"country":"中国","province":"云南省","city":"昆明市","county":"盘龙区","detail":"玉器城2栋3单元501"},"phone":"00000000","sleepDays":6,"leakLevelOneWanningTime":null,"leakLevelTwoWanningTime":null,"dealTime":null,"dealNumber":"","distanceLevel":{"id":2,"code":"0002","name":"远距离","tenancyIdx":null,"note":null,"createTime":null,"updateTime":null},"refereeUserId":null,"refereeUserName":null,"department":null}
             * originalAmount : 120
             * orderAmount : 110
             * refoundSum : 0
             * payType : null
             * couponCount : null
             * orderStatus : 0
             * payStatus : {"name":"待支付","index":0}
             * invoiceStatus : {"name":"待开发票","index":0}
             * urgent : false
             * timeSpan : 8860
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
             * dispatcher : null
             * dispatcherStartTime : null
             * orderServiceQuality : null
             * recycleGasCylinder : null
             * deliveryGasCylinder : null
             * orderOpHistoryList : null
             * gasRefoundDetailList : []
             * tenancyIdx : 7
             * note : null
             * deliveryTime : null
             * reviceTime : null
             * payTime : null
             * completeTime : null
             * createTime : 2020-09-20 17:50:04
             * updateTime : 2020-09-20 17:50:04
             * orderDetailList : [{"id":34534,"orderIdx":32813,"originalPrice":120,"goods":{"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":120,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"dealPrice":110,"quantity":1,"subtotal":110,"dispatchFee":0,"tenancyIdx":7,"createTime":"2020-09-20 17:50:04","updateTime":"2020-09-20 17:50:04"}]
             * dispatchFee : 0
             * operUser : {"id":246037,"userId":"bjpx_admin","name":"百江培训系统管理员","identity":"","password":"111111","wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-09-25 17:54:24","userGroup":null}
             */

            private int id;
            private String orderSn;
            private String callInPhone;
            private CustomerBean customer;
            private double originalAmount;
            private double orderAmount;
            private double refoundSum;
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
            private String reserveTime;
            private String comment;
            private Object dispatcher;
            private Object dispatcherStartTime;
            private Object orderServiceQuality;
            private Object recycleGasCylinder;
            private Object deliveryGasCylinder;
            private Object orderOpHistoryList;
            private int tenancyIdx;
            private Object note;
            private Object deliveryTime;
            private Object reviceTime;
            private Object payTime;
            private Object completeTime;
            private String createTime;
            private String updateTime;
            private double dispatchFee;
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

            public double getOriginalAmount() {
                return originalAmount;
            }

            public void setOriginalAmount(double originalAmount) {
                this.originalAmount = originalAmount;
            }

            public double getOrderAmount() {
                return orderAmount;
            }

            public void setOrderAmount(double orderAmount) {
                this.orderAmount = orderAmount;
            }

            public double getRefoundSum() {
                return refoundSum;
            }

            public void setRefoundSum(double refoundSum) {
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

            public String getReserveTime() {
                return reserveTime;
            }

            public void setReserveTime(String reserveTime) {
                this.reserveTime = reserveTime;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }

            public Object getDispatcher() {
                return dispatcher;
            }

            public void setDispatcher(Object dispatcher) {
                this.dispatcher = dispatcher;
            }

            public Object getDispatcherStartTime() {
                return dispatcherStartTime;
            }

            public void setDispatcherStartTime(Object dispatcherStartTime) {
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

            public Object getReviceTime() {
                return reviceTime;
            }

            public void setReviceTime(Object reviceTime) {
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

            public double getDispatchFee() {
                return dispatchFee;
            }

            public void setDispatchFee(double dispatchFee) {
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
                 * sleepDays : 6
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
                private int sleepDays;
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

                public int getSleepDays() {
                    return sleepDays;
                }

                public void setSleepDays(int sleepDays) {
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
                 * updateTime : 2020-09-25 17:54:24
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
                 * id : 34534
                 * orderIdx : 32813
                 * originalPrice : 120
                 * goods : {"id":24,"code":"BJ001","name":"15KG","specifications":"15","unit":"公斤","weight":15,"price":120,"realPrice":null,"gasCylinderSpec":{"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"},"status":0,"lifeExpectancy":111,"goodsType":{"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"},"area":{"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"},"tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"}
                 * dealPrice : 110
                 * quantity : 1
                 * subtotal : 110
                 * dispatchFee : 0
                 * tenancyIdx : 7
                 * createTime : 2020-09-20 17:50:04
                 * updateTime : 2020-09-20 17:50:04
                 */

                private int id;
                private int orderIdx;
                private int originalPrice;
                private GoodsBean goods;
                private int dealPrice;
                private int quantity;
                private int subtotal;
                private double dispatchFee;
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

                public double getDispatchFee() {
                    return dispatchFee;
                }

                public void setDispatchFee(double dispatchFee) {
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
                     * price : 120
                     * realPrice : null
                     * gasCylinderSpec : {"id":21,"code":"0002","name":"15公斤","tenancyIdx":null,"note":" ","createTime":"2018-01-12 09:26:52","updateTime":"2018-05-16 10:23:22"}
                     * status : 0
                     * lifeExpectancy : 111
                     * goodsType : {"id":44,"code":"001","name":"液化气","tenancyIdx":7,"note":"","createTime":"2020-03-11 13:21:36","updateTime":"2020-03-11 13:21:53"}
                     * area : {"id":28,"country":"中国","province":"云南省","city":"昆明市","county":"全部区","tenancyIdx":7,"note":null,"createTime":"2020-03-11 13:22:30","updateTime":"2020-03-11 13:22:30"}
                     * tenancyIdx : 7
                     * note : null
                     * createTime : 2020-03-11 13:22:30
                     * updateTime : 2020-03-11 13:22:30
                     */

                    private int id;
                    private String code;
                    private String name;
                    private String specifications;
                    private String unit;
                    private double weight;
                    private double price;
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

                    public double getId() {
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

                    public double getWeight() {
                        return weight;
                    }

                    public void setWeight(double weight) {
                        this.weight = weight;
                    }

                    public double getPrice() {
                        return price;
                    }

                    public void setPrice(double price) {
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
}
