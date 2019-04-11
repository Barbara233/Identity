pragma solidity ^0.4.24;

contract identity{
	uint public numRegistrants;
	mapping(address=>User) public users;
	mapping(address=> uint) public add;
	uint public tag;
	struct User{
		string name;
		bytes32 password;
		string role;
		bool hasRegistered;
		bool hasLogin;
	}
	event Register(string name,bytes32 password,string role);
	event Login(string name,bytes32 password,string role);
	event Modify(string newname,bytes32 newpassword);
	event Logout();
	event Transfer(address indexed receiver);
	
	constructor() public{
		numRegistrants=0;
		tag=0;
	}
	
	function register(string name,bytes32 password,string role) public{
		if(add[msg.sender]!=0&&users[msg.sender].hasRegistered==true){
			tag=0;
		}else{
			users[msg.sender].name=name;
			users[msg.sender].password=password;
			users[msg.sender].role=role;
			users[msg.sender].hasRegistered=true;
			numRegistrants += 1; 
			add[msg.sender]=1;
			Register(name,password,role);
			tag=1;
			}
	}
	
	function login(string name,bytes32 password,string role) public  {
		if(add[msg.sender]!=0&&users[msg.sender].hasRegistered==true){
			if(keccak256(users[msg.sender].name) == keccak256(name)&&users[msg.sender].password == password&&keccak256(users[msg.sender].role) == keccak256(role)){
				users[msg.sender].hasLogin=true;
				Login(name,password,role);   
				tag=1;
			}else{
				tag=0;
			}
		}else{
			tag=0;
		}
	}
	
	function modify(string newname,bytes32 newpassword) public {
		users[msg.sender].name=newname;
		users[msg.sender].password=sha3(newpassword);
		Modify(newname,newpassword);
		tag=1;
	}
	
	function logout() public {
		if(add[msg.sender]!=0&&users[msg.sender].hasRegistered&&users[msg.sender].hasLogin){
			delete users[msg.sender];
			delete add[msg.sender];
			if(!users[msg.sender].hasRegistered){
				numRegistrants -= 1; 
				Logout();
				tag=1;
			}else{
				tag=0;
			}
		}
	}
	
	function transfer(address receiver) payable {
		if((msg.sender.balance - msg.value)>=0){
			if(!(msg.sender.send(msg.value))){
				throw;
			}
			Transfer(receiver);
			tag=1;
		}else{
			tag=0;
		}
	}
	
}
	