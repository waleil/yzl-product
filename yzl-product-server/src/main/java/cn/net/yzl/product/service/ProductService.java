package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.ProductBean;

import java.util.List;

public interface ProductService {

    ComResponse<Void> insertRelationOfProductAndImgUrl(String pCode, Integer imgId, Integer type);

    ComResponse<Void> deleteRelationOfProductAndImgId(Integer id, Integer type);

    ComResponse<List<ProductBean>> selectByIdList(String productIds);


}
