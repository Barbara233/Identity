pragma solidity ^0.4.24;

contract identity{
	uint public numRegistrants;
	mapping(address=>User) public users;
	mapping(address=> uint) public add;
	address public receiver_1;
	uint public tag;
	struct User{
		string name;
		bytes32 password;
		string role;
		bool hasRegistered;
		bool hasLogin;
	}
	event Register(address owner);
	event Login(address owner);
	event Modify(address owner);
	event Logout(address owner);
	event Transfer(address owner,address _receiver,uint value);
	
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
			emit Register(msg.sender);
			tag=1;
			}
	}
	
	function login(string name,bytes32 password,string role) public  {
		if(add[msg.sender]!=0&&users[msg.sender].hasRegistered==true){
			if(keccak256(users[msg.sender].name) == keccak256(name)&&(users[msg.sender].password == password||users[msg.sender].password==sha3(password))&&keccak256(users[msg.sender].role) == keccak256(role)){
				users[msg.sender].hasLogin=true;
				emit Login(msg.sender);   
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
		emit Modify(msg.sender);
		tag=1;
	}
	
	function logout() public {
		if(add[msg.sender]!=0&&users[msg.sender].hasRegistered&&users[msg.sender].hasLogin){
			delete users[msg.sender];
			delete add[msg.sender];
			if(!users[msg.sender].hasRegistered){
				numRegistrants -= 1; 
				emit Logout(msg.sender);
				tag=1;
			}else{
				tag=0;
			}
		}
	}
	
	function transfer(address receiver)public payable {
		if((msg.sender.balance -msg.value)>=0){
			if(!(receiver.send(msg.value))){
				throw;
			}
			emit Transfer(msg.sender,receiver,msg.value);
			receiver_1=receiver;
			tag=1;
		}else{
			tag=0;
		}
	}
	
}
	