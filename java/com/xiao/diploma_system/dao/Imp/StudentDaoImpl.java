package com.xiao.diploma_system.dao.Imp;

import com.xiao.diploma_system.dao.StudentDao;
import com.xiao.diploma_system.entity.Apply;
import com.xiao.diploma_system.entity.Revoke;
import com.xiao.diploma_system.entity.Student;
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
public class StudentDaoImpl implements StudentDao {
    @Autowired
    SmartContract smartContract;
    private static String userAccountID="0x07b4f5e330f05133eccb402131d4757382c0e6e2";
    @Override
    public boolean registerStudent(Student student) throws Exception{
//        String _sid, String _name, String _password, String _school, String _major, String _eduHash, String _time, String _eduType)
        String newAddress=smartContract.Regist();
        Diploma diploma=smartContract.loadContract();
//        int stu_accout=getStudentAccount();
        String _sid = student.getSid();
        String _name=student.getName();
        String _password=student.getPassword();
        String _school=student.getSchool();
        String _major=student.getMajor();
        String _eduHash=student.getEduHash();
        int _index=student.getIndex();
        int _state=student.getState();
        String _time = student.getTime();
        String _eduType=student.getEduType();
        String _image=student.getImage();
        TransactionReceipt a = diploma.registStudent(newAddress,_sid, _name, _password, _school, _major, _eduHash, _time, _eduType,_image).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        if (b.size()==0)
            return false;
        System.out.println(b.get(0).message);
        if(b.get(0).message.equals("ok")){
            System.out.println("registerStudent : "+b.get(0).message);
            int state=1;
            int index=a.getTransactionIndex().intValue();
            System.out.println("state : "+state);
            System.out.println("index : "+index);
            return   setState(newAddress,state,index);
        }
        return  false;
    }

    @Override
    public int getStudentAccount() throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Uint256> account=new TypeReference<Uint256>() {
        };
        outputParameters.add(account);
        Function function = new Function("getStuAccount",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        return Integer.parseInt(result.get(0).getValue().toString());
    }

    @Override
    public Apply getApplyInfoByAddr(String address) throws Exception {
//        string memory _school, string memory _major,string memory _eduHash, string memory _eduType,string memory _time
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Utf8String> _school = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_school);
        TypeReference<Utf8String> _major = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_major);
        TypeReference<Utf8String> _eduHash = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_eduHash);
        TypeReference<Utf8String> _eduType = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_eduType);
        TypeReference<Utf8String> _time = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_time);
        Function function = new Function("getApplyInfoByAddr",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0){
            return null;
        }
        Apply apply=new Apply();
        apply.setSchool(result.get(0).getValue().toString());
        apply.setMajor(result.get(1).getValue().toString());
        apply.setEduHash(result.get(2).getValue().toString());
        apply.setEduType(result.get(3).getValue().toString());
        apply.setTime(result.get(4).getValue().toString());
        return apply;

    }

    @Override
    public int getIDByIndex(int index) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Uint256(index));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Uint256> id = new TypeReference<Uint256>() {
        };
        outputParameters.add(id);
        Function function = new Function("getIDByIndex",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0)
            return -1;
        return Integer.valueOf(result.get(0).getValue().toString());
    }

    @Override
    public Revoke getRevokeInfoByAddr(String address) throws Exception {
//        string memory _sid , string memory _name,string memory _reason){
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Utf8String> _sid = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_sid);
        TypeReference<Utf8String> _name = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_name);
        TypeReference<Utf8String> _reason = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_reason);
        Function function = new Function("getRevokeInfoByAddr",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0){
            return null;
        }
        Revoke revoke=new Revoke();
        revoke.setSid(result.get(0).getValue().toString());
        revoke.setName(result.get(1).getValue().toString());
        revoke.setReason(result.get(2).getValue().toString());
        revoke.setAddress(address);
        return revoke;
    }


    @Override
    public String getAdressByID(int id) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
//      inputParameters.add(new Address(to));
        inputParameters.add(new Uint256(id));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Address> adderss = new TypeReference<Address>() {
        };
        outputParameters.add(adderss);
        Function function = new Function("getAddressByID",
                inputParameters,outputParameters);
//       System.out.println(function.getOutputParameters().get(0));
//       System.out.println(demo.setMessage("asd").send().getTransactionHash());
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if(result.size()==0)
            return null;
        return result.get(0).getValue().toString();
    }

    @Override
    public Student getStudentInfoByAddress(String address) throws Exception {
        //(uint _id,string memory _sid, string memory _name, string memory _password,int8 _state,int8 _index){
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Uint256> id = new TypeReference<Uint256>() {
        };
        outputParameters.add(id);
        TypeReference<Utf8String> sid = new TypeReference<Utf8String>() {
        };
        outputParameters.add(sid);
        TypeReference<Utf8String> name = new TypeReference<Utf8String>() {
        };
        outputParameters.add(name);
        TypeReference<Utf8String> password = new TypeReference<Utf8String>() {
        };
        outputParameters.add(password);
        TypeReference<Uint256> state = new TypeReference<Uint256>() {
        };
        outputParameters.add(state);
        TypeReference<Uint256> index = new TypeReference<Uint256>() {
        };
        outputParameters.add(index);
        TypeReference<Utf8String> image = new TypeReference<Utf8String>() {
        };
        outputParameters.add(image);
        Function function = new Function("getStudentInfoByAddr",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        int _id=Integer.parseInt(result.get(0).getValue().toString());
        Student student=new Student();
        student.setId(_id);
        student.setSid(result.get(1).getValue().toString());
        student.setName(result.get(2).getValue().toString());
        student.setPassword(result.get(3).getValue().toString());
        student.setState(Integer.parseInt(result.get(4).getValue().toString()));
        student.setIndex(Integer.parseInt(result.get(5).getValue().toString()));
        student.setImage(result.get(6).getValue().toString());
        return student;
    }

    @Override
    public boolean studentLogin(int id, String password) throws Exception {
        userAccountID=getAdressByID(id);
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(userAccountID));
        inputParameters.add(new Uint256(id));
        inputParameters.add(new Utf8String(password));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Bool>bool=new TypeReference<Bool>() {
        };
        outputParameters.add(bool);
        Function function = new Function("studentLogin",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if (result.size()==0)
            return false;
        boolean login_ok=Boolean.parseBoolean(result.get(0).getValue().toString());
        if(!login_ok){
            userAccountID=smartContract.getAccountlist().get(1);
        }
        return login_ok;
    }

    @Override
    public boolean ifExsit(String address) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Bool>bool=new TypeReference<Bool>() {
        };
        outputParameters.add(bool);
        Function function = new Function("ifExsit",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if (result.size()==0)
            return false;
        boolean _exsit=Boolean.parseBoolean(result.get(0).getValue().toString());
        return _exsit;
    }

    @Override
    public boolean exsitEdu(String address) throws Exception {
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Bool>bool=new TypeReference<Bool>() {
        };
        outputParameters.add(bool);
        Function function = new Function("exsitEdu",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        if (result.size()==0)
            return false;
        boolean _exsit=Boolean.parseBoolean(result.get(0).getValue().toString());
        return _exsit;
    }

    @Override
    public boolean setState(String address,int state, int index) throws Exception {
        Diploma diploma=smartContract.loadContract();
        BigInteger _state = BigInteger.valueOf(state);
        BigInteger _index = BigInteger.valueOf(index);
        TransactionReceipt a = diploma.setstate(address, _state, _index).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        if (b.size()==0)
            return false;
        System.out.println("setState : "+b.get(0).message);
        return b.get(0).message.equals("ok");
    }

    @Override
    public boolean setImage(String address, String _image) throws Exception {
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a = diploma.setimage(address,_image).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        if (b.size()==0)
            return false;
        System.out.println("setImage : "+b.get(0).message);
        return b.get(0).message.equals("ok");
    }

    @Override
    public boolean updateStudentInfo(String address, Student student) throws Exception {
//        String addr, String _sid, String _name, String _password, String _school, String _major, String _time, String _eduType) {
            Diploma diploma=smartContract.loadContract();
        TransactionReceipt a = diploma.updateStudentInfo(address, student.getSid(), student.getName(), student.getPassword(), student.getSchool(), student.getMajor(), student.getTime(), student.getEduType(),student.getImage()).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        System.out.println(b.size());
        System.out.println(b.get(0).message);
        return b.get(0).message.equals("ok");
    }

    @Override
    public Student queryDiploma(Student student,String address) throws Exception {

//       string memory _school, string memory _major, string memory _eduType,string memory _eduHash)
        List<Type> inputParameters = new ArrayList<>();
        inputParameters.add(new Address(address));
        List<TypeReference<?>> outputParameters=  new ArrayList<>();
        TypeReference<Utf8String> _school = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_school);
        TypeReference<Utf8String> _major = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_major);
        TypeReference<Utf8String> _eduType = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_eduType);
        TypeReference<Utf8String> _eduHash = new TypeReference<Utf8String>() {
        };
        outputParameters.add(_eduHash);
        TypeReference<Utf8String> time = new TypeReference<Utf8String>() {
        };
        outputParameters.add(time);
        Function function = new Function("queryDiploma",
                inputParameters,outputParameters);
        String encodedFunction = FunctionEncoder.encode(function);
        org.web3j.protocol.core.methods.response.EthCall response = Web3JClient.getClient().ethCall(
                Transaction.createEthCallTransaction(userAccountID, smartContract.ContractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();
        List<Type> result = FunctionReturnDecoder.decode(
                response.getValue(), function.getOutputParameters());
        student.setSchool(result.get(0).getValue().toString());
        student.setMajor(result.get(1).getValue().toString());
        student.setEduType(result.get(2).getValue().toString());
        student.setEduHash(result.get(3).getValue().toString());
        student.setTime(result.get(4).getValue().toString());
        return student;
    }

    @Override
    public String revokeEdu(Revoke revoke) throws Exception {
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a = diploma.revokeEdu(revoke.getAddress(), revoke.getSid(), revoke.getName(), revoke.getReason()).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        System.out.println(b.get(0).message);
        return b.get(0).message;
    }

    @Override
    public boolean revokeStu(String address, int id, String password) throws Exception {
        Diploma diploma=smartContract.loadContract();
        BigInteger _id=BigInteger.valueOf(id);
        TransactionReceipt a = diploma.revokeStu(address,_id,password).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        System.out.println(b.get(0).message);
        System.out.println(b.get(0).message.equals("ok"));
        return b.get(0).message.equals("ok");
    }



    @Override
    public String applyDiploma(String address,Apply apply) throws Exception {
        Diploma diploma=smartContract.loadContract();
        TransactionReceipt a=diploma.applyDiploma(address,apply.getSchool(),apply.getMajor(),apply.getTime(),apply.getEduType()).sendAsync().get();
        List<Diploma.EventResponseLogEventResponse> b = diploma.getEventResponseLogEvents(a);
        if(b.get(0).message.equals("ok")) {
            System.out.println("applyDiploma : "+b.get(0).message);
            TransactionReceipt c = diploma.setEduHash(address, address, apply.getTime()).sendAsync().get();
            List<Diploma.EventResponseLogEventResponse> d = diploma.getEventResponseLogEvents(c);
            System.out.println("setEduHash : "+d.get(0).message);
            return d.get(0).message;
        }
        System.out.println(b.get(0).message);
        return b.get(0).message;
    }


}
