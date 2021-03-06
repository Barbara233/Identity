package com.identity.dao;

import java.util.List;


import com.identity.domain.Event;


public interface EventMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event
     *
     * @mbg.generated Tue May 07 14:15:39 CST 2019
     */
    public int insert(Event record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table event
     *
     * @mbg.generated Tue May 07 14:15:39 CST 2019
     */
    public int insertSelective(Event record);
    
    public List<Event> selectAll();
    
    public List<Event> selectTransferFrom(String event_address);
    public List<Event> selectTransferTo(String event_address);
    public List<Event> selectByName(String event_name);
    public List<Event> selectByElements(Event e);
    public List<Event> selectBlockInfo();
}