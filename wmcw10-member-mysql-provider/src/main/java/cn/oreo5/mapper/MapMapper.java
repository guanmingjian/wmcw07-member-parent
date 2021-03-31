package cn.oreo5.mapper;

import cn.oreo5.entity.PO.Map;
import cn.oreo5.entity.PO.MapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MapMapper {
    int countByExample(MapExample example);

    int deleteByExample(MapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Map record);

    int insertSelective(Map record);

    List<Map> selectByExample(MapExample example);

    Map selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Map record, @Param("example") MapExample example);

    int updateByExample(@Param("record") Map record, @Param("example") MapExample example);

    int updateByPrimaryKeySelective(Map record);

    int updateByPrimaryKey(Map record);
}