package com.kk;

import com.kk.entity.Diease;
import com.kk.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface sqlMapper {
    void addUser(User user);
    void addDiease(Diease diease);


}
