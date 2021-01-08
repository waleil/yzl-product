package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.Image;
import cn.net.yzl.product.model.db.ImageStore;

public interface ImageService {
    int insertImage(Image image);

    ComResponse createAlbum(ImageStore imageStore);

    ComResponse selectByStoreId(Integer id);

    ComResponse selectTypeById(Integer id);

    ComResponse deleteById(Integer id);
}
