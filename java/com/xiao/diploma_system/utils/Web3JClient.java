package com.xiao.diploma_system.utils;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.geth.Geth;                //web3库的集合
import org.web3j.protocol.http.HttpService;

public class Web3JClient {
    private static String ip = "http://127.0.0.1:8545";

    private Web3JClient() {
    }

    private volatile static Web3j web3j;
    private volatile static Admin admin;
    private volatile static Geth geth;

    public static Web3j getClient() {
        if (web3j == null) {
            synchronized (Web3JClient.class) {
                if (web3j == null) {

                    web3j = Web3j.build(new HttpService(ip));
                }
            }
        }
        return web3j;
    }

    public static Admin getAdmin() {
        if (admin == null) {
            synchronized (Web3JClient.class) {
                if (admin == null) {


                    admin = Admin.build(new HttpService(ip));
                }
            }
        }
        return admin;
    }

    public static Geth getGeth() {
        if (geth == null) {
            synchronized (Web3JClient.class) {
                if (geth == null) {


                    geth = Geth.build(new HttpService(ip));
                }
            }
        }
        return geth;
    }
}

