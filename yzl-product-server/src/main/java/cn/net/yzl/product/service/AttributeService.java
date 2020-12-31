package cn.net.yzl.product.service;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.product.model.db.AttributeBean;

public interface AttributeService {

    void insertAttribute(AttributeBean attributeBean);
//    void insertAttributeValue(AttributeBean attributeBean);
    ComResponse selectPageAttribute(int pageNo, int pageSize);

    ComResponse selectByclassifyIdAttribute(Integer id);
    ComResponse selectById(Integer id);

    ComResponse updateAttribute(AttributeBean attributeBean);

}
