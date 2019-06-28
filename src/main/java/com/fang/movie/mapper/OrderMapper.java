package com.fang.movie.mapper;

import com.fang.movie.entity.Order;
import com.fang.movie.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateStatus(@Param("orderId") int orderId,@Param("oldStatus") int oldStatus,@Param("newStatus") int newStatus);

}