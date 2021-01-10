package cn.net.yzl.product.model.pojo.product;

import java.util.Date;

public class ProductDisease {
    private Integer id;

    private String productCode;

    private Integer diseaseId;
    private String diseaseName;
    private Integer dtype;

    private Date createTime;

    private Date updateTime;

    private Integer diseasePid;

    public Integer getDiseasePid() {
        return diseasePid;
    }

    public void setDiseasePid(Integer diseasePid) {
        this.diseasePid = diseasePid;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public Integer getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(Integer diseaseId) {
        this.diseaseId = diseaseId;
    }

    public Integer getDtype() {
        return dtype;
    }

    public void setDtype(Integer dtype) {
        this.dtype = dtype;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}