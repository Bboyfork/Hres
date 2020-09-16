package com.su.hresource.mapper;

import com.su.hresource.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SelectRegisterMapper {

    @Select("select * from Register")
    UserInfo getMyRegisterInfo();

}
