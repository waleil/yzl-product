package cn.net.yzl.product.model.db;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BrandBean extends BaseObject {
    private Integer id;

    private String name;

    private boolean status;

    private String brandUrl;

    private boolean delFlag;

    private int sort;

    private String descri;

    private String createNo;

    private Date createTime;

    private String updateNo;

    private Date updateTime;

}