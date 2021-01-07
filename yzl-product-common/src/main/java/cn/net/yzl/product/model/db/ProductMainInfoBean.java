package cn.net.yzl.product.model.db;


import lombok.Data;

import java.io.Serializable;

@Data
public class ProductMainInfoBean  implements Serializable {

    private Integer id;

    private String name;

    private String productCode;

    private Integer stock;

    private Byte status;



}
