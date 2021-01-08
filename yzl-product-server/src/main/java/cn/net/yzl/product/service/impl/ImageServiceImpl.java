package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.ImageMapper;
import cn.net.yzl.product.dao.ImageStoreMapper;
import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.model.db.ImageStore;
import cn.net.yzl.product.model.vo.ImageDTO;
import cn.net.yzl.product.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageMapper imageBeanMapper;

    @Autowired
    private ImageStoreMapper imageStoreMapper;


    @Override
    public int insertImage(Image image) {

        imageBeanMapper.insertSelective(image);

        return image.getId();

    }

    @Override
    public ComResponse createAlbum(ImageStore imageStore) {
        imageStoreMapper.insertSelective(imageStore);
        return ComResponse.success();
    }

    @Override
    public ComResponse selectByStoreId(Integer id) {
        List<ImageDTO> list =  imageBeanMapper.selectByStoreId(id);
        if (list == null || list.size() == 0){
            return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE,"该图片库下还没有图片");
        }
        return ComResponse.success(list);
    }

    @Override
    public ComResponse selectTypeById(Integer id) {
        Integer type = imageStoreMapper.selectTypeById(id);
        return ComResponse.success(type);
    }
}
