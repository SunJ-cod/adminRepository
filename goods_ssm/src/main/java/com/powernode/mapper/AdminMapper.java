package com.powernode.mapper;

import com.powernode.bean.Admin;

public interface AdminMapper {
    int deleteByPrimaryKey(Integer adminId);

    int insert(Admin row);

    int insertSelective(Admin row);

    Admin selectByPrimaryKey(Integer adminId);

    int updateByPrimaryKeySelective(Admin row);

    int updateByPrimaryKey(Admin row);

    String findCode(String code);

    boolean add(Admin admin);

    Admin query(Admin admin);
}