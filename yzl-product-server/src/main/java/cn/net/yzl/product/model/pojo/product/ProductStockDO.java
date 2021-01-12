package cn.net.yzl.product.model.pojo.product;
import lombok.Data;

import java.util.Date;

/**
 * @author lichanghong
 * @version 1.0
 * @title: ProductStockPOJO
 * @description 商品库存实体类
 * @date: 2021/1/12 10:57 下午
 */
@Data
public class ProductStockDO {
    /**
        商品编号
     */
    private String productCode;

    /**
        商品名称
     */
    private String name;
    /**
       库存数量
    */
    private Integer stock;
    /**
        是否限制库存
     */
    private boolean limitFlag;
    /**
     销售开始时间
     */
    private Date saleStartTime;
    /**
     销售结束时间
     */
    private Date saleEndTime ;
    /**
     上下架状态
     */
    private Integer status;
}
