package cn.net.yzl.product.model.db;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BrandBean implements Serializable {
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