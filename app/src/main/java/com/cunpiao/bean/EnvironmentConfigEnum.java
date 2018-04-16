package com.cunpiao.bean;

/**
 * @AUTHOR : niejiuqian
 * @DATETIME: 2018-03-02 14:25
 * @DESCRIPTION: 环境配置
 */

public enum EnvironmentConfigEnum {
    ONLINE() {
        @Override
        public String getName() {
            return "ONLINE";
        }

        @Override
        public String getHost() {
            return "http://appshop.hytest.cunpiao.com/cpibs-app-shop/";
        }

        @Override
        public String getDesc() {
            return "正式环境地址";
        }
    },

    TEST() {
        @Override
        public String getName() {
            return "TEST";
        }

        @Override
        public String getHost() {
            return "http://appshop.hytest.cunpiao.com/cpibs-app-shop/";
        }

        @Override
        public String getDesc() {
            return "测试环境地址";
        }
    },

    DEV() {
        @Override
        public String getName() {
            return "DEV";
        }

        @Override
        public String getHost() {
            return "http://localhost:8080/";
        }

        @Override
        public String getDesc() {
            return "开发环境地址";
        }
    },

    LOCAL() {
        @Override
        public String getName() {
            return "LOCAL";
        }

        @Override
        public String getHost() {
            return "http://192.168.1.10:8080/";
        }

        @Override
        public String getDesc() {
            return "本地环境地址";
        }
    };

    private String name;
    private String host;
    private String desc;

    public String getName() {
        return name;
    }

    public String getHost() {
        return host;
    }

    public String getDesc() {
        return desc;
    }


    public static EnvironmentConfigEnum getEnum(String name) {
        for (EnvironmentConfigEnum environmentConfigEnum : values()) {
            if (environmentConfigEnum.getName().equals(name)) {
                return environmentConfigEnum;
            }
        }
        return ONLINE;
    }
}
