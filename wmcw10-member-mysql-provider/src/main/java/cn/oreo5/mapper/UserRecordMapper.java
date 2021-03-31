package cn.oreo5.mapper;

import cn.oreo5.entity.PO.UserRecord;
import cn.oreo5.entity.PO.UserRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRecordMapper {
    int countByExample(UserRecordExample example);

    int deleteByExample(UserRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    List<UserRecord> selectByExample(UserRecordExample example);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRecord record, @Param("example") UserRecordExample example);

    int updateByExample(@Param("record") UserRecord record, @Param("example") UserRecordExample example);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);
}