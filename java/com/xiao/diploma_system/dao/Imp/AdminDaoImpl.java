package com.xiao.diploma_system.dao.Imp;

import com.xiao.diploma_system.dao.AdminDao;
import com.xiao.diploma_system.entity.BlackList;
import com.xiao.diploma_system.utils.Diploma;
import com.xiao.diploma_system.utils.SmartContract;
import com.xiao.diploma_system.utils.Web3JClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
@Repository
public class AdminDaoImpl implements AdminDao {
    @Autowired
    SmartContract smartContract;
    private static String AdminAccountID="0x07b4f5e330f05133eccb402131d4757382c0e6e2";
    @Override
    public boolean adminLogin(String id, String password) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(AdminAccountID));
        inputParameters.add(new Utf8String(id));
        inputParameters.add(new Utf8String(password));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Bool>bool=new TypeReference<Bool>() {
        };
        outputParameters.add(bool);
        Function function = new Function("adminLogin",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(AdminAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if (result.size()==0)
            return false;
        boolean login_ok=Boolean.parseBoolean(result.get(0).getValue().toString());             //文件夹中的用户比对
        return login_ok;
    }

    @Override
    public boolean comfirmEdu(String address) throws Exception {
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a=diploma.comfirmEdu(address).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        return b.get(0).message.equals("ok");
    }

    @Override
    public boolean addBlackList(BlackList blackList,String address) throws Exception {
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a=diploma.addBlackList(address,blackList.getSid(),blackList.getSchool(),blackList.getName(),
                blackList.getReason()).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        return b.get(0).message.equals("ok");
    }

    @Override
    public boolean revokeBlackList(int index) throws Exception {
        BigInteger _index=BigInteger.valueOf(index);
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a=diploma.revokeBlackList(_index).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        return b.get(0).message.equals("ok");
    }

    @Override
    public BlackList getBlackListInfoByIndex(int index) throws Exception {
       // (address _addr , string memory _sid,string memory _name,string memory _school, string memory _reason){
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Uint256(index));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Address> _addr = new TypeReference<Address>() {
        };
        outputParameters.add(_addr);
        TypeReference<Utf8String> _sid = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_sid);
        TypeReference<Utf8String> _name = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_name);
        TypeReference<Utf8String> _school = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_school);
        TypeReference<Utf8String> _reason = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_reason);
        Function function = new Function("getBlackListInfoByIndex",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(AdminAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0)
            return null;
        BlackList blackList=new BlackList();
        blackList.setAddress(result.get(0).getValue().toString());
        blackList.setSid(result.get(1).getValue().toString());
        blackList.setName(result.get(2).getValue().toString());
        blackList.setSchool(result.get(3).getValue().toString());
        blackList.setReason(result.get(4).getValue().toString());
        blackList.setIndex(index);
        return blackList;
    }

    @Override
    public int getBltAccount() throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Uint256> accout = new TypeReference<Uint256>() {
        };
        outputParameters.add(accout);
        Function function = new Function("getBltAccount",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(AdminAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0)
            return -1;
        return Integer.valueOf(result.get(0).getValue().toString());
    }

    @Override
    public int getAppAccount() throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Uint256> accout = new TypeReference<Uint256>() {
        };
        outputParameters.add(accout);
        Function function = new Function("getApplyAccount",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(AdminAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0)
            return -1;
        return Integer.valueOf(result.get(0).getValue().toString());
    }


}
