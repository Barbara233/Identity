package com.identity.serviceImpl;

import java.io.File;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.identity.domain.ContractSet;
import com.identity.domain.Identity;
import com.identity.domain.User;
import com.identity.service.IdentityService;

@Service("identityService")
public class IdentityServiceImpl implements IdentityService{
    
	@Resource(name="contractSet")
	private ContractSet contractSet;
	private Identity identity;
	
	public static byte[] stringToBytes32(String str){
		  byte[] a = new byte[32];
		  System.arraycopy(str.getBytes(),0,a,32-str.length(),str.length());
		  return a;
	}
	
	public IdentityServiceImpl() {
	}
	@Override
	public String getConstractAddress() {
		String address = null;
		address = contractSet.getContractAddress();
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
			TransactionReceipt receipt=identity.register(u.getUsername(), stringToBytes32(u.getPassword()), u.getCharacter()).send();
			if(contractSet.getIdentity().tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
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
			TransactionReceipt receipt=this.identity.login(u.getUsername(),stringToBytes32(u.getPassword()), u.getCharacter()).send();
			if(contractSet.getIdentity().tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
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
			if(contractSet.getIdentity().tag().send().equals(new BigInteger("0"))) {
				return false;
			}else {
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void load(String address) {
		this.identity=contractSet.loadConstract(address);
	}


}
