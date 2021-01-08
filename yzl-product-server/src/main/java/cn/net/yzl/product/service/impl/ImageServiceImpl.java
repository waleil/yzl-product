package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.entity.Page;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.AssemblerResultUtil;
import cn.net.yzl.product.dao.ImageMapper;
import cn.net.yzl.product.dao.ImageStoreMapper;
import cn.net.yzl.product.model.vo.image.ImageDTO;
import cn.net.yzl.product.model.vo.image.ImageVO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreDTO;
import cn.net.yzl.product.model.vo.imageStore.ImageStoreVO;
import cn.net.yzl.product.service.ImageService;
import com.github.pagehelper.PageHelper;
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
    public int insertImage(ImageVO image) {

        imageBeanMapper.insertSelective(image);

        return image.getId();

    }

    @Override
    public ComResponse createAlbum(ImageStoreVO imageStore) {
        imageStoreMapper.insertSelective(imageStore);
        return ComResponse.success();
    }

    @Override
    public ComResponse selectByStoreId(Integer id, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);

        List<ImageDTO> list  = imageBeanMapper.selectByStoreId(id,pageNo,pageSize);
        if (list == null || list.size() == 0){
            return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE,"该图片库下还没有图片");
        }
        Page<ImageDTO> page = AssemblerResultUtil.resultAssembler(list);
        return ComResponse.success(page);
    }

    @Override
    public ComResponse selectTypeById(Integer id) {
        Integer type = imageStoreMapper.selectTypeById(id);
        return ComResponse.success(type);
    }

    @Override
    public ComResponse deleteById(Integer id, String userId) {
        if(imageBeanMapper.selectQuoteById(id)>0){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(),"当前图片存在关联商品无法删除!");
        }
        imageBeanMapper.deleteByPrimaryKey(id,userId);
        return ComResponse.success();
    }

    @Override
    public ComResponse<List<ImageStoreDTO>> selectStores(Integer type) {
        List<ImageStoreDTO> list = imageStoreMapper.selectByType(type);
        return ComResponse.success(list);
    }

    @Override
    public ComResponse deleteStoreById(Integer id, String userId) {
        if(imageBeanMapper.selectQuoteByStoreId(id)>0){
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(),"当前图片库内存在图片无法删除!");
        }
        imageStoreMapper.deleteByPrimaryKey(id,userId);
        return ComResponse.success();
    }
}
