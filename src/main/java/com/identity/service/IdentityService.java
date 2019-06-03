package com.identity.service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import org.web3j.protocol.core.methods.response.EthBlock;

import com.identity.domain.Block;
import com.identity.domain.Event;
import com.identity.domain.Transfer;
import com.identity.domain.User;

public interface IdentityService {
	public String getConstractAddress();
	public boolean isPublicKey(String publicKey);
    public BigInteger getNumRegister();
    public boolean register(User u);
    public boolean login(User u);
    public BigInteger getWallet(String address);
    public boolean logout();
    public void load(String address);
    public boolean changeInfo(User u);
    public boolean transfer(String address,BigInteger count);
    public List<Event> getBlock();
    /*public HashSet<BlockInfo> loginEventFilter(HashSet<BlockInfo> result) throws Exception;
    public HashSet<BlockInfo> registerEventFilter(HashSet<BlockInfo> result) throws Exception;*/
    public List<Event> serachBlock(Event t);
    public HashMap<String,List<Event>> getCheck();
    public List<User> getRegUsers();
    public List<User> selectByRegUser(User u);
    public HashMap<String,List<Event>> selectByCheck(Event e);
}
