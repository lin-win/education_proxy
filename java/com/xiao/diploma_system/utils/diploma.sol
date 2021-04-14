pragma solidity >=0.4.22 <0.6.0;



contract DiplomaSystem {

    struct StudentStruct{

        uint id  ;

        string sid  ;

        string name  ;

        string password ; 

        string school  ;

        string major  ;

        string eduHash  ;

        string time  ;

        int8 state  ;

        int8 index  ;

        string eduType  ;

        string image  ;

        bool isUsed  ;

    }

    

    struct RevokeStruct{

        string sid  ;

        string name  ;

        string reason  ;

         bool isUsed;

    }

    

    struct ApplyStruct{

        string school  ;

        string major  ;

        string time  ;

        string eduType  ;

        string eduHash  ;

        

        bool isUsed;

    }

    

    struct AdminStruct{

         string id  ;//ID

         string password ;

    }

    

    struct BlackListStruct{

        address addr; //地址

        string sid  ;//学号

        string name  ;//姓名

        string school  ;//学校

        string reason ;//原因

        

        bool isUsed;

    }

    mapping (address=>StudentStruct) stu;

    mapping (address=>ApplyStruct) appl;

    mapping (address=>RevokeStruct) rvo;

    mapping (uint=>BlackListStruct) blt;

    mapping (uint=>address) stu_address;

    mapping (address=>AdminStruct) adm;

    mapping (uint=>uint) stu_id;

    address public owner;

    uint bltAccount=0;

    uint stuAccount=0;

    uint id_counter=0;

    uint applyAccount=0;

    constructor() public {

        owner=msg.sender;

        adm[msg.sender].id="admin";

        adm[msg.sender].password="123456";

    }

    

    modifier admin(){

        require(owner == msg.sender);

        _; }

    

      

    event  eventResponseLog(

            address addr,

            string message

        );

        

    

    //==========功能函数================  

    function ifExsit(address add) public view returns (bool){

                return stu[add].isUsed;

    }

    

    function exsitEdu(address addr) public view returns (bool){

        string memory n="null";

        if(keccak256(bytes(stu[addr].eduHash)) == keccak256(bytes(n)))

            return false;

        else

            return true;

    }

    

    function getStuAccount() public view returns (uint){

        return stuAccount;

    }

    

    function getApplyAccount() public view returns (uint){

        return applyAccount;

    }

    function getBltAccount() public view returns (uint){

        return bltAccount;

    }

    

    //==========用户模块  登陆、注册、修改================

    function registStudent(address addr,string memory _sid, string memory _name, string memory _password,string memory _school

     , string memory _major,string memory _eduHash,string memory _time, string memory _eduType,string memory _image) public  returns(bool){

        if(!ifExsit(addr)){

        stu_address[id_counter]=addr;

        StudentStruct memory student =StudentStruct(id_counter,_sid,_name,_password,_school,_major,_eduHash,_time,1,1,_eduType,_image,true);

        stu[addr]=student;

        stu_id[stuAccount]=id_counter;

        id_counter++;

        stuAccount++;

        emit eventResponseLog(addr,"ok");

        return true;

        }else{

        revert(); // 撤销交易

        }

    }

    

    

    function adminLogin(address  addr, string memory _id, string memory _password) view public admin returns(bool){

        if( keccak256(bytes(adm[addr].id)) == keccak256(bytes(_id)) &&

         keccak256(bytes(adm[addr].password)) == keccak256(bytes(_password))){

             return true; //ID及密码匹配正确 登录系统

        } else {

        return false; //其他情况返回 false

        }

    }

    

    function studentLogin(address  addr, uint  _id, string memory _password) view public returns(bool){

        if(!ifExsit(addr)){

            return false; //地址不存在，则返回 false

        } else if(( stu[addr].id == _id) &&

         keccak256(bytes(stu[addr].password)) == keccak256(bytes(_password))){

             return true; //地址、ID及密码匹配正确 登录系统

        } else {

        return false; //其他情况返回 false

        }

    }



    function setstate(address addr,int8 _state,int8 _index)public returns (bool){

        require(ifExsit(addr)); //判断地址是否存在，不存在则调用失败

        stu[addr].state=_state;

        stu[addr].index=_index;

        emit eventResponseLog(addr,"ok");

        return true;

    }

    

    function setimage(address addr,string memory _image)public returns (bool){

        require(ifExsit(addr)); //判断地址是否存在，不存在则调用失败

        stu[addr].image=_image;

        emit eventResponseLog(addr,"ok");

        return true;

    }

    

    function updateStudentInfo(address addr,string memory _sid, string memory _name, string memory _password,string memory _school

     , string memory _major,string memory _time, string memory _eduType,string memory _image) public returns(bool){

     require(ifExsit(addr)); //判断地址是否存在，不存在则调用失败

     stu[addr].sid=_sid;

     stu[addr].name=_name;

     stu[addr].password=_password;

     stu[addr].school=_school;

     stu[addr].major=_major;

     stu[addr].time=_time;

     stu[addr].eduType=_eduType;
	stu[addr].image=_image;

     emit eventResponseLog(addr,"ok");

     return true;

    }

    

    

    

    //==========用户模块    各种查询================

    function getAddressByID(uint _id)view public returns(address){

        if(stu_address[_id]!= address(0))

            return stu_address[_id];

        else

            revert(); // 撤销交易

    }

    

    function getIDByIndex(uint _index)view public returns(uint){

        if(_index>=0  &&  _index<stuAccount)

            return stu_id[_index];

        else

            revert(); // 撤销交易

    }

    

    function getBlackListInfoByIndex(uint index) view public returns(address _addr

     , string memory _sid,string memory _name,string memory _school, string memory _reason){

     require(blt[index].isUsed); //判断地址是否存在，不存在则调用失败

     return (blt[index].addr,blt[index].sid,blt[index].name, blt[index].school,blt[index].reason);

    }

    

    

    function getApplyInfoByAddr(address addr) view public returns(string memory _school

     , string memory _major,string memory _eduHash, string memory _eduType,string memory _time){

     require(appl[addr].isUsed); //判断地址是否存在，不存在则调用失败

     return (appl[addr].school,appl[addr].major,appl[addr].eduHash, appl[addr].eduType,appl[addr].time);

    }



    function getRevokeInfoByAddr(address addr) view public returns(string memory _sid

     , string memory _name,string memory _reason){

     require(rvo[addr].isUsed); //判断地址是否存在，不存在则调用失败

     return (rvo[addr].sid,rvo[addr].name,rvo[addr].reason);

    }





    function getStudentInfoByAddr(address addr) view public returns(uint _id,string memory _sid, string memory _name, string memory _password,int8 _state,int8 _index,string memory _image){

     require(ifExsit(addr)); //判断地址是否存在，不存在则调用失败

     return (stu[addr].id,stu[addr].sid, stu[addr].name, stu[addr].password,stu[addr].state,stu[addr].index,stu[addr].image);

    }



    function queryDiploma (address addr) view public returns(string memory _school

     , string memory _major, string memory _eduType,string memory _eduHash,string memory _time){

        if(ifExsit(addr)){

        return(stu[addr].school,stu[addr].major,stu[addr].eduType,stu[addr].eduHash,stu[addr].time);

        }else{

        //查询失败

        revert(); // 撤销交易

        }

    }

    

    

    

    //==========学历申请与确认================

    function applyDiploma(address addr,string memory _school

     , string memory _major,string memory _time, string memory _eduType)public returns(string memory){

         string memory n="null";

         if(keccak256(bytes(stu[addr].eduHash)) == keccak256(bytes(n)))

           {

             if(!appl[addr].isUsed){

                  ApplyStruct memory a=ApplyStruct(_school,_major,_time,_eduType,"null",true);

                  appl[addr]=a;

                  applyAccount++;

                  emit eventResponseLog(addr,"ok");

                  return "ok";

             } 

             else{

                 emit eventResponseLog(addr,"已有待确认学历");

                 return "已有待确认学历";

             }

           }

         else{

             return "已存有学历";

             emit eventResponseLog(addr,"已存有学历");

         }

            

     }

    

    function setEduHash(address addr,string memory _eduHash,string memory _time)public returns (bool){

        if(appl[addr].isUsed){

            appl[addr].eduHash=_eduHash;

            appl[addr].time=_time;

            emit eventResponseLog(addr,"ok");

            return true;

        }

        else{

            emit eventResponseLog(addr,"failed");

            return false;

        }

            

    }



    

    function comfirmEdu(address addr) public admin returns (bool){

        if(appl[addr].isUsed){

            stu[addr].school=appl[addr].school;

            stu[addr].major=appl[addr].major;

            stu[addr].eduType=appl[addr].eduType;

            stu[addr].eduHash=appl[addr].eduHash;

            stu[addr].time=appl[addr].time;

            applyAccount--;

            delete appl[addr];

            emit eventResponseLog(addr,"ok");

            return true;

        }

        else {

            emit eventResponseLog(addr,"failed");

        return false; // 撤销交易

        }

    }



    function revokeStu(address addr,uint _id, string memory _password) public admin returns(bool){

        if(ifExsit(addr)){

            if((stu[addr].id == _id) &&

             keccak256(bytes(stu[addr].password)) == keccak256(bytes(_password))){

                uint id=stu[addr].id;

                delete stu_address[id];

                delete stu[addr];

                uint i;

                for(i=0;i<stuAccount;i++)

                    if(stu_id[i]==id)

                        break;

                for(uint j=i;j<stuAccount-1;j++)

                    stu_address[j]=stu_address[j+1];

                delete stu_id[stuAccount-1];

                stuAccount--;

                emit eventResponseLog(addr,"ok");

                return true;

             }

        }else {

            emit eventResponseLog(addr,"failed");

        return false;

        }

    }

    

    function revokeEdu(address addr,string memory _id, string memory _name, string memory _reason) public admin returns(bool){

        if(ifExsit(addr)){

        RevokeStruct memory revoke = RevokeStruct(_id,_name,_reason,true);

        rvo[addr]=revoke;

        stu[addr].eduHash="null";

        stu[addr].school="null";

        stu[addr].major="null";

        stu[addr].eduType="null";
        
        stu[addr].time="null";

        emit eventResponseLog(addr,"ok");

        return true;

        }else {

            emit eventResponseLog(addr,"failed");

        return false;

        }

    }

    


    function addBlackList(address addr,string memory _sid,string memory _name,string memory _school,string memory _reason) public admin returns(bool){

        BlackListStruct memory b=BlackListStruct(addr,_sid,_school,_name,_reason,true);

        blt[bltAccount]=b;

        bltAccount++;

        emit eventResponseLog(addr,"ok");

        return true;

    }

    

    function revokeBlackList(uint index) public returns (bool){

        if(blt[index].isUsed){

            delete blt[index];

            for(uint j=index;j<bltAccount-1;j++)

                blt[j]=blt[j+1];

            bltAccount--;

            emit eventResponseLog(msg.sender,"ok");

            return true;

        }

        else

        {

             emit eventResponseLog(msg.sender,"failed");

            return false;

        }

       

    }

    

    //===============Test================

    function testApply1(address addr) public  returns (string memory){

         return applyDiploma(addr,"3","3","3","3");

    }

    

    function testApply2(address addr) public  returns (bool){

        return setEduHash(addr,"eduHash","time");

    }

    function testApply3(address addr) public  returns (bool){

        return comfirmEdu(addr);

    }

    

    function testRegistStudent() public  returns(bool){

        if(!ifExsit(msg.sender)){

        stu_address[id_counter]=msg.sender;

        StudentStruct memory student =StudentStruct(id_counter,"1","1","1","null","null","null","null",1,1,"null","null",true);

        stu[msg.sender]=student;

        stu_id[stuAccount]=id_counter;

        id_counter++;

        stuAccount++;

        return true;

        }else{

        revert();

        }

    }

    

    function testaddBlt() public  returns(bool){

        addBlackList(msg.sender,"1","1","1","reason");

        return true;

    }

}
