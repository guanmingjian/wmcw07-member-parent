package cn.oreo5.service.api;

import cn.oreo5.entity.PO.Photo;

import java.util.List;

public interface PhotoService {

    /**
     * 根据图片类型获取图片列表数据
     * @param type
     * @return
     */
    List<Photo> getPhotoByType(String type);
}
