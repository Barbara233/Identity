package com.identity.serviceImpl;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;

import com.identity.dao.EventMapper;
import com.identity.dao.UserMapper;
import com.identity.domain.Block;
import com.identity.domain.ContractSet;
import com.identity.domain.Event;
import com.identity.domain.Identity;
import com.identity.domain.Identity.LoginEventResponse;
import com.identity.domain.Identity.LogoutEventResponse;
import com.identity.domain.Identity.ModifyEventResponse;
import com.identity.domain.Identity.RegisterEventResponse;
import com.identity.domain.Identity.TransferEventResponse;
import com.identity.domain.Transfer;
import com.identity.domain.User;
import com.identity.service.IdentityService;



@Service("identityService")
public class IdentityServiceImpl implements IdentityService{
    
	@Resource(name="contractSet")
	private ContractSet contractSet;
	private Identity identity;
	private String address;
	@Autowired
	private EventMapper eventMapper;
	@Autowired
	private UserMapper userMapper;
	
	public static byte[] stringToBytes32(String str){
		  byte[] a = new byte[32];
		  System.arraycopy(str.getBytes(),0,a,32-str.length(),str.length());
		  return a;
	}
	public void load(String address) {
		this.address=address;
		this.identity=contractSet.loadConstract(address);
	}
	
	public IdentityServiceImpl() {
	}
	@Override
	public String getConstractAddress() {
		String address=contractSet.getContractAddress();
		return address;
	}
	@Override
	public boolean isPublicKey(String publicKey) {
		List<String> accounts=contractSet.getAccounts();
		for (String string : accounts) {
			if(publicKey.equals(string)) {
				load(publicKey);
				return true;
			}
		}
		return false;
	}
	@Override
	public BigInteger getNumRegister() {
		BigInteger num=null;
		try {
			num=contractSet.getIdentity().numRegistrants().send();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num;
	}
	@Override
	public boolean register(User u) {
		try {
			TransactionReceipt receipt=identity.register(u.getUsername(), stringToBytes32(u.getPassword()), u.getRole()).send();
			if(identity.tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				RegisterEventResponse ee=identity.getRegisterEvents(receipt).get(0);
				Event e=getEvent("注册", ee.log.getBlockHash(),ee.log.getTransactionHash());
				e.setEventAddress(ee.owner);
		        Event gasE=getGasEvent(ee.log.getBlockHash(), address);
		        eventMapper.insert(e);
		        eventMapper.insert(gasE);
		        u.setTime(e.getEventTime());
		        u.setPublickey(address);
		        userMapper.insert(u);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean login(User u) {
		try {
			TransactionReceipt receipt=this.identity.login(u.getUsername(),stringToBytes32(u.getPassword()), u.getRole()).send();
			if(identity.tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				LoginEventResponse ee=identity.getLoginEvents(receipt).get(0);
				Event e=getEvent("登录", ee.log.getBlockHash(),ee.log.getTransactionHash());
				e.setEventAddress(ee.owner);
		        Event gasE=getGasEvent(ee.log.getBlockHash(), address);
		        eventMapper.insert(e);
		        eventMapper.insert(gasE);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public BigInteger getWallet(String address) {
		BigInteger wei=null;
		try {
			EthGetBalance ethGetBalance = contractSet.getWeb3j().ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			wei = ethGetBalance.getBalance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wei;
	}

	@Override
	public boolean logout() {
		try {
			TransactionReceipt receipt=identity.logout().send();
			if(identity.tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				LogoutEventResponse ee=identity.getLogoutEvents(receipt).get(0);
				Event e=getEvent("注销", ee.log.getBlockHash(),ee.log.getTransactionHash());
				e.setEventAddress(ee.owner);
		        Event gasE=getGasEvent(ee.log.getBlockHash(), address);
		        eventMapper.insert(e);
		        eventMapper.insert(gasE);
		        userMapper.deleteByAddress(address);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	

	@Override
	public boolean changeInfo(User u) {
		try {
			TransactionReceipt receipt=identity.modify(u.getUsername(), stringToBytes32(u.getPassword())).send();
			if(identity.tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				u.setPublickey(address);
				userMapper.update(u);
				ModifyEventResponse ee=identity.getModifyEvents(receipt).get(0);
				Event e=getEvent("修改信息", ee.log.getBlockHash(),ee.log.getTransactionHash());
				e.setEventAddress(ee.owner);
		        Event gasE=getGasEvent(ee.log.getBlockHash(), address);
		        eventMapper.insert(e);
		        eventMapper.insert(gasE);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean transfer(String _address, BigInteger count) {
		try {
			//BigInteger b=contractSet.getWeb3j().ethGetBalance(_address, DefaultBlockParameterName.LATEST).send().getBalance();
			TransactionReceipt receipt=identity.transfer(_address,count).send(); 
			//EthTransaction t=contractSet.getWeb3j().ethGetTransactionByBlockHashAndIndex(receipt.getBlockHash(), receipt.getTransactionIndex()).send();
			//BigInteger a=contractSet.getWeb3j().ethGetBalance(_address, DefaultBlockParameterName.LATEST).send().getBalance();
		/*	String from=identity.getTransferEvents(receipt).get(0).owner;
			String to=identity.getTransferEvents(receipt).get(0)._receiver;*/
			//System.out.println(b+"    "+a+"    "+(b.subtract(a)));
			if(identity.tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				TransferEventResponse ee=identity.getTransferEvents(receipt).get(0);
				Event e=getEvent("转账", ee.log.getBlockHash(),ee.log.getTransactionHash());
				e.setEventAddress(ee.owner);
				e.setEventTransfrom(ee.owner);
				e.setEventTransto(ee._receiver);
				System.out.println(_address+"  "+ee._receiver);
				e.setEventTransvalue(ee.value.toString());
		        Event gasE=getGasEvent(ee.log.getBlockHash(), address);
		        eventMapper.insert(e);
		        eventMapper.insert(gasE);
		       // System.out.println( identity.receiver_1().send());
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public Event getGasEvent(String blockHash,String address) {
			Event e=new Event();
		try {
			org.web3j.protocol.core.methods.response.EthBlock.Block b=contractSet.getWeb3j().ethGetBlockByHash(blockHash, false).send().getBlock();
			String gasUsed=b.getGasUsed().toString();
			
			Block _b=new Block();
			_b.setTimestamp(b.getTimestamp().toString());
			e.setEventTime(_b.getTimestamp());
			e.setEventTransfrom(address);
			e.setEventTransvalue(gasUsed);
			e.setEventTransto("无");
			e.setEventName("手续费");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return e;
	}
	
	private Event getEvent(String name,String blockHash,String transferHash) {
		Event e=new Event();
		try {
			org.web3j.protocol.core.methods.response.EthBlock.Block b=contractSet.getWeb3j().ethGetBlockByHash(blockHash, false).send().getBlock();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date=new Date(new Long(b.getTimestamp().toString())*1000);
			e.setBlocknum(b.getNumber().toString());
			e.setEventTime(sf.format(date));
			e.setEventName(name);
			e.setTransferhash(transferHash);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return e;
	}
	
	@Override
	public List<Event> getBlock() {
		return eventMapper.selectBlockInfo();
	}
	@Override
	public List<Event> serachBlock(Event t) {
		
		return eventMapper.selectByElements(t);
	}
	
	@Override
	public HashMap<String,List<Event>> getCheck(){
		List<Event> out=eventMapper.selectTransferFrom(address);
		List<Event> in=eventMapper.selectTransferTo(address);
		HashMap<String,List<Event>> r=new HashMap<>();
		r.put("out", out);
		r.put("in", in);
		return r;
	}
	@Override
	public List<User> getRegUsers() {
		return userMapper.selectAll();
	}
	@Override
	public List<User> selectByRegUser(User u) {
		return userMapper.selectByElements(u);
	}
	@Override
	public HashMap<String,List<Event>> selectByCheck(Event e) {
		HashMap<String,List<Event>> r=new HashMap<>();
		if(e.getEventName().equals("transfer_in")) {
			String transfer_from=e.getEventTransto();
			e.setEventTransfrom(transfer_from);
			e.setEventTransto(address);
			e.setEventName("转账");
			r.put("in", eventMapper.selectByElements(e));
		}else {
			if(e.getEventName().equals("transfer_out")) {
				e.setEventName("转账");
			}
			if(e.getEventName().equals("gas")) {
				e.setEventName("手续费");
			}
			r.put("out", eventMapper.selectByElements(e));
		}
		
		return r;
	}
	
	
	/*@Override
	public List<Transfer> getCheck() {
		List<Transfer> check=new ArrayList<>();
		Web3j web3j=contractSet.getWeb3j();
		try {
			int blockNum=web3j.ethBlockNumber().send().getBlockNumber().intValue();
			for(int i=0;i<blockNum;i++) {
				EthBlock b=web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(new BigInteger(""+i)), true).send();
			    List<EthBlock.TransactionResult> txs=b.getBlock().getTransactions();
			    txs.forEach(tx ->{
			    	Transfer t=new Transfer();
			    	EthBlock.TransactionObject transaction=(EthBlock.TransactionObject)tx.get();
			    	t.setFrom(address);
			    	t.setTimestamp(b.getBlock().getTimestamp().toString());
			    	if(transaction.getFrom().equals(address)) {
			    		if(transaction.getValue().toString().equals("0")) {
			    			t.setType("手续费");
			    		    t.setCount(transaction.getGasPrice().toString());
			    		    t.setTo("无");
			    		}else {
			    		    t.setType("转出");
			    		    t.setCount(transaction.getValue().toString());
			    		    t.setTo(transaction.getTo());
			    		}
			    		check.add(t);
			    	}
			    	if(transaction.getTo().equals(address)) {
			    		t.setType("转入");
			    		t.setCount(transaction.getValue().toString());
			    		t.setTo(transaction.getTo());
			    		check.add(t);
			    	}
			    	
			    	
			    });
			   
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return check;
	}
		
*/
	
	
	/*public HashSet<BlockInfo> loginEventFilter(HashSet<BlockInfo> result) throws Exception {
		EthFilter filter = new EthFilter();
		synchronized (filter) {
			identity.loginEventObservable(filter).subscribe(event -> {
			BlockInfo b=new BlockInfo();
			b.setAddress(this.address);
			b.setOperate("登录");
			b.setBlockNum(event.log.getBlockNumber().toString());
			b.setHash(event.log.getBlockHash());
			result.add(b);
		});  
		}
		return result;
	}*/
        /*synchronized (filter) {
			identity.logoutEventObservable(filter).subscribe(event -> {
			BlockInfo b=new BlockInfo();
			b.setAddress(this.address);
			b.setOperate("注销");
			b.setBlockNum(event.log.getBlockNumber().toString());
			b.setHash(event.log.getBlockHash());
			result.add(b);
		});  
		}
        
        synchronized (filter) {
			identity.modifyEventObservable(filter).subscribe(event -> {
			BlockInfo b=new BlockInfo();
			b.setAddress(this.address);
			b.setOperate("修改信息");
			b.setBlockNum(event.log.getBlockNumber().toString());
			b.setHash(event.log.getBlockHash());
			result.add(b);
		});  
		}
        synchronized (filter) {
			identity.transferEventObservable(filter).subscribe(event -> {
			BlockInfo b=new BlockInfo();
			b.setAddress(this.address);
			b.setOperate("转账");
			b.setBlockNum(event.log.getBlockNumber().toString());
			b.setHash(event.log.getBlockHash());
			result.add(b);
		}); 
		}
		
		
		
		 
		return result;
	  }*/



}
