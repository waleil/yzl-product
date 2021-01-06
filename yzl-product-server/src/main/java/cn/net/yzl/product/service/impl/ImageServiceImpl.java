package cn.net.yzl.product.service.impl;

import cn.net.yzl.product.dao.ImageMapper;
import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageBeanMapper;


    @Override
    public int insertImage(Image image) {

        imageBeanMapper.insertSelective(image);

        return image.getId();

    }
}
