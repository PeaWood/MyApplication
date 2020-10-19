package com.gc.nfc.domain;

/**
 * Created by lenovo on 2020/9/26
 */
public class Data_User {
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
     * updateTime : 2020-09-26 16:11:07
     * userGroup : {"id":3,"code":"00003","name":"配送员","tenancyIdx":null,"note":"配送员","createTime":"2017-11-04 10:54:27","updateTime":"2017-12-11 10:55:27"}
     * department : {"id":102,"code":"md1","name":"门店一","parentDepartment":{"id":99,"code":"LSB","name":"零售部","parentDepartment":{"id":98,"code":"root","name":"百江培训","parentDepartment":null,"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:52:37","updateTime":"2020-03-11 11:52:37"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:55:59","updateTime":"2020-03-11 11:55:59"},"lstSubDepartment":null,"tenancyIdx":7,"note":"","createTime":"2020-03-11 11:56:55","updateTime":"2020-03-11 11:56:55"}
     * userPosition : {"id":1280,"userIdx":246157,"longitude":116.397714,"latitude":39.850006,"createTime":"2020-03-11 14:46:11","updateTime":"2020-09-23 23:30:37"}
     * jobNumber : 12
     * mobilePhone : 11
     * officePhone : 11
     * email : null
     * serviceStatus : {"name":"禁用","index":1}
     * aliveStatus : {"name":"在线","index":1}
     * aliveUpdateTime : 2020-09-26 16:11:07
     * gasTakeOverStatus : {"name":"无用户卡|有标签","index":3}
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
         * longitude : 116.397714
         * latitude : 39.850006
         * createTime : 2020-03-11 14:46:11
         * updateTime : 2020-09-23 23:30:37
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
         * name : 无用户卡|有标签
         * index : 3
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
