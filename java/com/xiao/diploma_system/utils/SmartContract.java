package com.xiao.diploma_system.utils;

import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.tx.Contract;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import org.web3j.protocol.geth.Geth;

@Component
public class SmartContract {
    //设置主账户地址
    private String source = "/Users/macbook/Desktop/testNet/data/keystore/UTC--2021-03-19T14-26-20.011181000Z--07b4f5e330f05133eccb402131d4757382c0e6e2";
    public static Web3j web3j;
    public static Admin admin;
    public static Geth geth;
    private static String password="123456";
    public Credentials credentials;
    public String ContractAddress;

    public void minerStart() throws IOException {               //开始挖矿
        geth.minerStart(1).send();
    }

    public void minerStop() throws IOException {                //挖矿停止
        geth.minerStop().send();
    }

    //在此处更改智能合约地址(每次都会变)
    // )
    public SmartContract() throws Exception{
        credentials = WalletUtils.loadCredentials(password, source);
        web3j = Web3JClient.getClient();
        admin = Web3JClient.getAdmin();
        geth = Web3JClient.getGeth();
        ContractAddress="0x9A6280ce972B5853a660C7cDc16E882Fd03fe2B1";                   //修改
    }

    public Diploma deployContract()throws Exception {
        Diploma diploma = Diploma.deploy(web3j,credentials,Contract.GAS_PRICE, Contract.GAS_LIMIT).send();
        return  diploma;
    }

    //加载智能合约
    public Diploma loadContract()  {                                        //系统部署智能合约，主意gaslimit
        Diploma diploma = Diploma.load(ContractAddress, web3j, credentials,  new BigInteger("20"), new BigInteger("5453255"));
        return diploma;
    }

    //注册账户
    public String Regist()throws Exception {                                                    //注册账户使用，(在生成账户json文件时读取)
        String walletFileName="";//文件名
        String walletFilePath="/Users/macbook/Desktop/testNet/data/keystore";
        //钱包文件保持路径，请替换位自己的某文件夹路径
        walletFileName = WalletUtils.generateNewWalletFile("123456", new File(walletFilePath), false);
        Credentials new_credentials = WalletUtils.loadCredentials(password, walletFilePath + "/" + walletFileName);
        String address = new_credentials.getAddress();
        return address;
    }

    public List<String> getAccountlist() {                                                      //获取用户列表接口
        try {
            return admin.personalListAccounts().send().getAccountIds();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String createAccount() {                                                             //在User上创建用户
        try {
            NewAccountIdentifier newAccountIdentifier = admin.personalNewAccount(password).send();
            if (newAccountIdentifier != null) {
                String accountId = newAccountIdentifier.getAccountId();
//                admin.personalSetAccountName(accountId,accountName);
//                admin.
//
//                Map<String,Object> account = new HashMap<String,Object>();
//                account.put(accountId,accountInfo);
//                parity.personalSetAccountMeta(accountId,account);account

                return accountId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public PersonalAccountsInfo.AccountsInfo getAccountInfo(String accountId){
//
//        try{
//            PersonalAccountsInfo personalAccountsInfo = parity.personalAccountsInfo().send();
//
//            return  personalAccountsInfo.getAccountsInfo().get(accountId);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }

    public BigInteger getBalance(String accountId) {
        try {
            DefaultBlockParameter defaultBlockParameter = new DefaultBlockParameterNumber(web3j.ethBlockNumber().send().getBlockNumber());
            EthGetBalance ethGetBalance = web3j.ethGetBalance(accountId, defaultBlockParameter).send();
            if (ethGetBalance != null) {
                return ethGetBalance.getBalance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
