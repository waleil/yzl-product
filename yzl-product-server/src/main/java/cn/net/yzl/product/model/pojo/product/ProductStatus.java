package cn.net.yzl.product.model.pojo.product;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;

import java.util.Date;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductStatus
 * @description 商品状态
 * @date: 2021/1/8 9:19 下午
 */
@Data
public class ProductStatus extends BaseObject {
    //商品ID唯一编号
    private String productCode;
    private Integer status;
    private Date updateTime;
}
