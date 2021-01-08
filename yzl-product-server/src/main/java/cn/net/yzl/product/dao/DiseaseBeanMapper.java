package cn.net.yzl.product.dao;

import cn.net.yzl.product.model.db.DiseaseBean;
import cn.net.yzl.product.model.vo.disease.DiseaseDTO;
import cn.net.yzl.product.model.vo.disease.DiseaseDelVo;
import cn.net.yzl.product.model.vo.disease.DiseaseVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiseaseBeanMapper {

    int deleteByPrimaryKey(DiseaseDelVo delVo);

    /**
     * @author lichanghong
     * @description 新增病症
     * @date: 2021/1/5 11:03 下午
     * @param diseaseVo :
     * @return: null
     */
    int insertSelective(DiseaseVo diseaseVo);


    Integer selectPid(Integer id);

    int updateByPrimaryKeySelective(@Param("id") Integer id,@Param("name") String name,@Param("userId")String userId);

    List<DiseaseBean> selectAll();

    List<DiseaseBean> findRoot();

    List<Integer> getProductsByDid(@Param("id") Integer id);

    List<DiseaseBean> selectByPid(@Param("pid") Integer pid);
    /**
     * @author lichanghong
     * @description 查询
     * @date: 2021/1/6 2:00 下午
     * @param pid:  父级编号
     * @return: List<DiseaseDTO>
     */
    List<DiseaseDTO> queryByPID(@Param("pid") Integer pid);
    /**
     * @Author: lichanghong
     * @Description: 查询最大主键
     * @Date: 2021/1/7 6:40 下午
     * @Return: Integer
     */
    int queryMaxId();
}