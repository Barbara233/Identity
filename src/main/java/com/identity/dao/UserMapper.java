package com.identity.dao;

import java.util.List;

import com.identity.domain.User;

public interface UserMapper {
   
    public int insert(User record);
    public int insertSelective(User record);
    public int deleteByAddress(String address);
    public List<User> selectByElements(User u); 
    public List<User> selectAll();
    public int update(User u);
}