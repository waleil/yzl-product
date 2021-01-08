package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseTreeNode;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import cn.net.yzl.product.service.DiseaseService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseBeanMapper diseaseBeanMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * @param diseaseVo:
     * @author lichanghong
     * @description 新增病症
     * @date: 2021/1/5 11:03 下午
     * @return: null
     */
    @Override
    public ComResponse<Integer> insertDisease(DiseaseVo diseaseVo) {
        String cacheKey = CacheKeyUtil.maxDiseaseCacheKey();
        long maxId= redisUtil.incr(cacheKey,1);
        diseaseVo.setId(Integer.parseInt(String.valueOf(maxId)));
        diseaseVo.setUpdateTime(null);
        diseaseBeanMapper.insertSelective(diseaseVo);
        return ComResponse.success(Integer.parseInt(String.valueOf(maxId)));
    }

    /**
     * @param delVo
     * @Author: lichanghong
     * @Description:
     * @Date: 2021/1/6 4:56 下午
     * @Return: cn.net.yzl.common.entity.ComResponse<java.lang.Void>
     */
    @Override
    public ComResponse<Void> deleteByPrimaryKey(DiseaseDelVo delVo) {

        //首先判断是否有子集
        List<DiseaseBean> list = diseaseBeanMapper.selectByPid(delVo.getId());
        if (!CollectionUtils.isEmpty(list)) {
            return ComResponse.fail(ResponseCodeEnums.BIZ_ERROR_CODE.getCode(), "存在子项，无法删除!");
        }
        //判断是否有商品
        // TODO: 2021/1/5  查询商品信息
        diseaseBeanMapper.deleteByPrimaryKey(delVo);
        return ComResponse.success();
    }

    @Override
    public ComResponse<List<DiseaseBean>> selectAllDiseases() {
        List<DiseaseBean> diseaseBeans = diseaseBeanMapper.selectAll();
        return ComResponse.success(diseaseBeans);
    }

    /**
     * @Author: lichanghong
     * @Description: 查询树形结构
     * @Date: 2021/1/6 4:57 下午
     * @Return: cn.net.yzl.common.entity.ComResponse<java.util.List < cn.net.yzl.product.model.vo.disease.DiseaseTreeNode>>
     */
    @Override
    public ComResponse<List<DiseaseTreeNode>> queryTreeNode() {
            List<DiseaseBean> diseaseBeans = diseaseBeanMapper.selectAll();
            //判断是否为空
            if (CollectionUtils.isEmpty(diseaseBeans)) {
                return ComResponse.success(Collections.EMPTY_LIST);
            }
            //进行
            Map<Integer, List<DiseaseBean>> map = diseaseBeans.stream().collect(Collectors.groupingBy(DiseaseBean::getPid));
            List<DiseaseTreeNode> list = new ArrayList<>(map.size());
            List<DiseaseBean> pList = map.get(0);
            if(CollectionUtils.isEmpty(pList)){
                return ComResponse.success(Collections.EMPTY_LIST);
            }
            //循环
            pList.stream().forEach(node->{
                DiseaseTreeNode treeNode = transform(node);
                List<DiseaseBean> subList = map.get(node.getId());
                if(!CollectionUtils.isEmpty(subList)){
                    List<DiseaseTreeNode>  treeNodes = new ArrayList<>(subList.size());
                    subList.forEach(suNode->{
                        DiseaseTreeNode subTreeNode = transform(suNode);
                        //TODO 查询商品信息
                        treeNodes.add(subTreeNode);
                    });
                    treeNode.setNodeList(treeNodes);
                }
                list.add(treeNode);
            });
            return ComResponse.success(list);
    }
    /**
     * @Author: lichanghong
     * @Description: 转换
     * @Date: 2021/1/6 5:35 下午
     * @param node 节点
     * @Return: cn.net.yzl.product.model.vo.disease.DiseaseTreeNode
     */
    private DiseaseTreeNode transform(DiseaseBean node){
        DiseaseTreeNode treeNode = new DiseaseTreeNode();
        treeNode.setId(node.getId());
        treeNode.setPid(node.getPid());
        treeNode.setName(node.getName());
        treeNode.setNodeList(new ArrayList<>());
        return treeNode;
    }

    @Override
    public List<DiseaseDTO> queryByPID(Integer pid) {
        return diseaseBeanMapper.queryByPID(pid);
    }

}
