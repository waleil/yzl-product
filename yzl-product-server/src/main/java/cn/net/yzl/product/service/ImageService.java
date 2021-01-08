package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.vo.image.ImageVO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreDTO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreVO;

import java.util.List;

public interface ImageService {
    int insertImage(ImageVO image);

    ComResponse createAlbum(ImageStoreVO imageStore);

    ComResponse selectByStoreId(Integer id, Integer pageNo, Integer pageSize);

    ComResponse selectTypeById(Integer id);

    ComResponse deleteById(Integer id, String userId);

    ComResponse<List<ImageStoreDTO>> selectStores(Integer type);

    ComResponse deleteStoreById(Integer id, String userId);
}
