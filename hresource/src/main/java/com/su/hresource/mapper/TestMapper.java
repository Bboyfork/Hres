package com.su.hresource.mapper;

import com.su.hresource.entity.TestBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 测试mapper
 * @author tianyu
 * @date 2020年7月27日10:09:41
 * */
@Mapper
public interface TestMapper {
    /**
     * 测试
     * @return list
     * */
    @Select("select * from SU_USER_CORE")
    List<TestBean> selectTest();

}
