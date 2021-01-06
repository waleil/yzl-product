package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.ProductMainInfoBean;
import cn.net.yzl.product.model.vo.product.ProductBO;

import java.util.List;

public interface ProductService {

    ComResponse<Void> insertRelationOfProductAndImgUrl(String pCode, Integer imgId, Integer type);

    ComResponse<Void> deleteRelationOfProductAndImgId(Integer id, Integer type);

    ComResponse selectByIdList(String productIds);


    ComResponse selectByCondition(ProductBO productBO);

    ComResponse reduceStock(Integer productNo, Integer stock);

    ComResponse increaseStock(Integer productNo, Integer stock);

    List<ProductMainInfoBean> getProductMainInfoPage(Integer pageNo, Integer pageSize);

    ComResponse<List<ProductMainInfoBean>> getMainInfoByIds(String ids);
}
