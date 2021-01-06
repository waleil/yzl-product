package cn.net.yzl.product.model.db;

import lombok.Data;

import java.util.Date;

@Data
public class ImageStore {
    private Integer id;

    private String imageUrl;

    private String descri;

    private Integer sort;

    private Date createTime;

    private Date updateTime;

}