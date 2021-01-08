package cn.net.yzl.product.model.vo.image;

import lombok.Data;

import java.util.Date;

@Data
public class ImageVO {

    private Integer id;

    private int imageStoreId;

    private String url;

    private int delFlag;

    private Date createTime;

    private Date updateTime;

    private String creator;

    private String updator;

    private int type;

}
