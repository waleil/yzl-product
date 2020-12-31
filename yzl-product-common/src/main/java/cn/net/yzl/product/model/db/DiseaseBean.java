package cn.net.yzl.product.model.db;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class DiseaseBean  implements Serializable {
    private int id;

    private String name;

    private int pid;

    private int sort;

    private String createNo;

    private String updateNo;

    private boolean delFlag;

    private Date createTime;

    private Date updateTime;

}