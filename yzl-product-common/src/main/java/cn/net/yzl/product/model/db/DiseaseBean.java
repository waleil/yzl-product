package cn.net.yzl.product.model.db;

import cn.net.yzl.product.model.BaseObject;
import lombok.Data;
import java.util.Date;

@Data
public class DiseaseBean  extends BaseObject {
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