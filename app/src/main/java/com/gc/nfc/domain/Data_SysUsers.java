package com.gc.nfc.domain;

import java.util.List;

/**
 * Created by lenovo on 2020/10/20
 */
public class Data_SysUsers {
    /**
     * total : 3
     * items : [{"id":246157,"userId":"psy","name":"123","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:31:48","updateTime":"2020-10-20 22:44:26","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1280,"userIdx":246157,"longitude":113.528786,"latitude":22.493937,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-20 22:38:45"},"jobNumber":"12","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-20 22:44:26","gasTakeOverStatus":{"name":"无用户卡|无标签","index":2},"logoutStatus":null},{"id":246185,"userId":"psy2","name":"11","identity":"","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 12:39:59","updateTime":"2020-10-19 17:21:55","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":103,"code":"md2","name":"门店二","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:57:06","updateTime":"2020-03-11 11:57:06"},"userPosition":{"id":1281,"userIdx":246185,"longitude":102.7346714952257,"latitude":25.00393283420139,"createTime":"2020-03-11 15:34:57","updateTime":"2020-06-15 17:03:54"},"jobNumber":"16","mobilePhone":"11","officePhone":"11","email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-10-19 17:21:55","gasTakeOverStatus":{"name":"无用户卡|有标签","index":3},"logoutStatus":null},{"id":875276,"userId":"psy4","name":"1","identity":"1","password":null,"wxOpenId":null,"tenancyIdx":7,"note":"","createTime":"2020-06-05 13:32:04","updateTime":"2020-09-28 12:44:16","userGroup":{"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"},"department":{"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"},"userPosition":{"id":1289,"userIdx":875276,"longitude":116.3614529079861,"latitude":39.964638671875,"createTime":"2020-06-05 14:21:16","updateTime":"2020-09-20 10:48:19"},"jobNumber":"1","mobilePhone":"111","officePhone":null,"email":null,"serviceStatus":{"name":"禁用","index":1},"aliveStatus":{"name":"在线","index":1},"aliveUpdateTime":"2020-09-28 12:44:16","gasTakeOverStatus":{"name":"无用户卡|有标签","index":3},"logoutStatus":null}]
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
         * id : 246157
         * userId : psy
         * name : 123
         * identity : 1
         * password : null
         * wxOpenId : null
         * tenancyIdx : 7
         * note :
         * createTime : 2020-03-11 12:31:48
         * updateTime : 2020-10-20 22:44:26
         * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"}
         * department : {"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"}
         * userPosition : {"id":1280,"userIdx":246157,"longitude":113.528786,"latitude":22.493937,"createTime":"2020-03-11 14:46:11","updateTime":"2020-10-20 22:38:45"}
         * jobNumber : 12
         * mobilePhone : 11
         * officePhone : 11
         * email : null
         * serviceStatus : {"name":"禁用","index":1}
         * aliveStatus : {"name":"在线","index":1}
         * aliveUpdateTime : 2020-10-20 22:44:26
         * gasTakeOverStatus : {"name":"无用户卡|无标签","index":2}
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
        private UserGroupBean userGroup;
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

        public UserGroupBean getUserGroup() {
            return userGroup;
        }

        public void setUserGroup(UserGroupBean userGroup) {
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

        public static class UserGroupBean {
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
             * longitude : 113.528786
             * latitude : 22.493937
             * createTime : 2020-03-11 14:46:11
             * updateTime : 2020-10-20 22:38:45
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
             * name : 无用户卡|无标签
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
    }
}
