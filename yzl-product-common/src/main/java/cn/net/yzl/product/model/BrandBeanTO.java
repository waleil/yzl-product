package cn.net.yzl.product.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandBeanTO implements Serializable {

    private BrandBean brandBean;

    private Integer productCount;

}
