package cn.net.yzl.product.model.vo.disease;

import cn.net.yzl.product.model.BaseObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author lichanghong
 * @version 1.0
 * @title: DiseaseVo
 * @description 病症接收实体
 * @date: 2021/1/5 10:39 下午
 */
@Data
public class DiseaseVo extends BaseObject {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "name", value = "病症名称",required = true)
    @NotEmpty(message = "病症名称,不能为空!")
    private String name;
    @ApiModelProperty(name = "pid", value = "上级节点")
    private int pid;
    @ApiModelProperty(name = "sort", value = "排序")
    private int sort;
    @ApiModelProperty(name = "updateNo", value = "员工编号")
    private String updateNo;
    @ApiModelProperty(name = "updateTime", value = "修改时间")
    private Date updateTime;
}
