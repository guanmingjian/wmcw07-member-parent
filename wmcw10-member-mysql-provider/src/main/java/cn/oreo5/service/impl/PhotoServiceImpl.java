package cn.oreo5.service.impl;

import cn.oreo5.entity.PO.Photo;
import cn.oreo5.entity.PO.PhotoExample;
import cn.oreo5.mapper.PhotoMapper;
import cn.oreo5.service.api.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public List<Photo> getPhotoByType(String type) {

        PhotoExample photoExample = new PhotoExample();

        PhotoExample.Criteria criteria = photoExample.createCriteria();

        criteria.andClasEqualTo(Integer.parseInt(type));

        return photoMapper.selectByExample(photoExample);
    }
}
