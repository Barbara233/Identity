package com.identity.service;

import java.math.BigInteger;

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
}
