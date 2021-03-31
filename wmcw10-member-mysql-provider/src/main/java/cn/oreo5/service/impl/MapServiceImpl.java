package cn.oreo5.service.impl;

import cn.oreo5.entity.PO.Map;
import cn.oreo5.entity.PO.MapExample;
import cn.oreo5.mapper.MapMapper;
import cn.oreo5.service.api.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class MapServiceImpl implements MapService {

    @Autowired
    private MapMapper mapMapper;

    @Override
    public List<Map> getMapByType(String type) {

        MapExample mapExample = new MapExample();

        MapExample.Criteria criteria = mapExample.createCriteria();

        criteria.andClasEqualTo(Integer.parseInt(type));

        List<Map> list = mapMapper.selectByExample(mapExample);

        return list;
    }
}
