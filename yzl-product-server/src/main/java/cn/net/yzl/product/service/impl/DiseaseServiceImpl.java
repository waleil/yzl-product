package cn.net.yzl.product.service.impl;

import cn.net.yzl.common.entity.ComResponse;
import cn.net.yzl.common.enums.ResponseCodeEnums;
import cn.net.yzl.common.util.JsonUtil;
import cn.net.yzl.product.dao.DiseaseBeanMapper;
import cn.net.yzl.product.dao.ProductBeanMapper;
import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.pojo.disease.Disease;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseTreeNode;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import cn.net.yzl.product.model.vo.product.dto.ProductDTO;
import cn.net.yzl.product.service.DiseaseService;
import cn.net.yzl.product.utils.CacheKeyUtil;
import cn.net.yzl.product.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    //缓存时间，12个小时
    private static long CACHE_TIME = 60 * 60 * 12;
    //空字符串，表示数据库中不存在
    private static String NULL_STR = "null";
    @Autowired
    private DiseaseBeanMapper diseaseBeanMapper;
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ProductBeanMapper productBeanMapper;

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
        long maxId = redisUtil.incr(cacheKey, 1);
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
        //删除缓存
        deleteCache(delVo.getId(),delVo.getPId());
        return ComResponse.success();
    }

    @Override
    public List<DiseaseBean> selectAllDiseases() {
        List<DiseaseBean> diseaseBeans = diseaseBeanMapper.selectAll();
        return diseaseBeans;
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
            if (CollectionUtils.isEmpty(pList)) {
                return ComResponse.success(Collections.EMPTY_LIST);
            }
            //循环
            pList.stream().forEach(node -> {
                DiseaseTreeNode treeNode = transform(node);
                List<DiseaseBean> subList = map.get(node.getId());
                if (!CollectionUtils.isEmpty(subList)) {
                    List<DiseaseTreeNode> treeNodes = new ArrayList<>(subList.size());
                    subList.forEach(suNode -> {
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
     * @param node 节点
     * @Author: lichanghong
     * @Description: 转换
     * @Date: 2021/1/6 5:35 下午
     * @Return: cn.net.yzl.product.model.vo.disease.DiseaseTreeNode
     */
    private DiseaseTreeNode transform(DiseaseBean node) {
        DiseaseTreeNode treeNode = new DiseaseTreeNode();
        treeNode.setId(node.getId());
        treeNode.setPid(node.getPid());
        treeNode.setName(node.getName());
        if (treeNode.getPid() == 0) {
            treeNode.setNodeList(new ArrayList<>());
        }
        if (node.getPid() != 0) {
            List<ProductDTO> list = new ArrayList<>();
            ProductDTO dto = new ProductDTO();
            dto.setName("商品名称");
            dto.setCode("1000001");
            dto.setImageUrl("http://fast.staff.yuzhilin.net.cn/group1/M00/00/01/wKggg1_4IDGAU5tUAAAWIYXlGys238.png");
            dto.setSource(1);
            for (int i = 0; i < 2; i++) {
                list.add(dto);
            }
            treeNode.setProductDTOList(list);
        }
        return treeNode;
    }

    @Override
    public List<DiseaseDTO> queryByPID(Integer pid) {
        List<DiseaseDTO> list = diseaseBeanMapper.queryByPID(pid);
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public ComResponse changeDiseaseName(Integer id, String name, String userId) {
        Integer pid = diseaseBeanMapper.selectPid(id);
        if (null == pid) {
            return ComResponse.fail(ResponseCodeEnums.NO_DATA_CODE.getCode(), "目标数据为空");
        }
        if (pid != 0) {
            return ComResponse.fail(ResponseCodeEnums.PARAMS_ERROR_CODE.getCode(), "暂时只支持修改一级分类");
        }
        diseaseBeanMapper.updateByPrimaryKeySelective(id, name, userId);
        //删除缓存
        deleteCache(id,pid);
        return ComResponse.success();
    }

    @Override
    public Disease queryById(Integer id,Integer pid) {
        if (id == null || id < 1) {
            return null;
        }
        //先从缓存中查询
        String cacheKey = CacheKeyUtil.diseaseCacheKey(id,pid);
        String result = redisUtil.getStr(cacheKey);
        if (StringUtils.hasText(result)) {
            if (!NULL_STR.equals(result)) {
                return JsonUtil.getObjectFromJSONString(result, Disease.class);
            } else {
                return null;
            }
        } else {
            Disease disease = diseaseBeanMapper.queryById(id,pid);
            if (disease != null) {
                redisUtil.set(cacheKey, JsonUtil.toJsonStr(disease), CACHE_TIME);
            } else {
                //缓存5分钟
                redisUtil.set(cacheKey, NULL_STR, 300);
            }
            return disease;
        }
    }


    /**
     * @param id
     * @Author: lichanghong
     * @Description: 删除缓存信息
     * @Date: 2021/1/9 9:32 下午
     * @Return: void
     */
    private void deleteCache(Integer id,Integer pid) {
        String cacheKey = CacheKeyUtil.diseaseCacheKey(id,pid);
        redisUtil.del(cacheKey);
    }

}
