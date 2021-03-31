package cn.oreo5.service.api;

import cn.oreo5.entity.PO.Map;

import java.util.List;

public interface MapService {
    /**
     * 通过type查询Article
     * @param type
     * @return
     */
    List<Map> getMapByType(String type);
}
