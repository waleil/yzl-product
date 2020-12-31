package cn.net.yzl.product.service.impl;

import cn.net.yzl.product.dao.ImageBeanMapper;
import cn.net.yzl.product.model.ImageBean;
import cn.net.yzl.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageBeanMapper imageBeanMapper;


    @Override
    public int insertImage(String url, Integer type) {
        ImageBean imageBean = new ImageBean();

        if (type==1){
            imageBean.setPictureUrl(url);
        }else {
            imageBean.setVideoUrl(url);
        }

        imageBeanMapper.insertSelective(imageBean);

        return imageBean.getId();

    }
}
