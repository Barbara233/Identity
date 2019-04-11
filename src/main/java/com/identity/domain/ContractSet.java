package com.identity.domain;

import java.math.BigInteger;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.Contract;

@Repository("contractSet")
public class ContractSet {
	private List<String> accounts;
	private ClientTransactionManager ctm;
	private Identity identity;//部署合约，将智能合约部署在account(0)
	public Identity getIdentity() {
		return identity;
	}

	private Web3j web3j;
	private String contractAddress;
	public Web3j getWeb3j() {
		return web3j;
	}
	public void setWeb3j(Web3j web3j) {
		this.web3j = web3j;
	}
	public String getContractAddress() {
		return contractAddress;
	}
	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}
	public List<String> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<String> accounts) {
		this.accounts = accounts;
	}
	public ClientTransactionManager getCtm() {
		return ctm;
	}
	public void setCtm(ClientTransactionManager ctm) {
		this.ctm = ctm;
	}
	public Identity loadConstract(String address) {
		ClientTransactionManager ctm=new ClientTransactionManager(web3j,address);
		return Identity.load(contractAddress, web3j, ctm, Contract.GAS_PRICE,Contract.GAS_LIMIT);
	}
	
	public ContractSet() {
		try {
			web3j = Web3j.build(new HttpService("http://localhost:8545"));
			accounts = web3j.ethAccounts().send().getAccounts();
			ctm=new ClientTransactionManager(web3j,accounts.get(0));
			identity=Identity.deploy(web3j, ctm,Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
			contractAddress=identity.getContractAddress();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	

}
