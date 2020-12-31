package cn.net.yzl.product.model.db;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AttributeValueBean implements Serializable {

    private int id;
    private int attributeId;//属性id
    private String avalue;//属性值
    private int delFlag;//是否删除(0代表否,1代表是)
    private Date createTime;
    private Date updateTime;

}
