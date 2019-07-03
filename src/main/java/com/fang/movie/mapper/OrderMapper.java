package com.fang.movie.mapper;
import com.fang.movie.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    int updateStatus(@Param("orderId") int orderId,@Param("oldStatus") int oldStatus,@Param("newStatus") int newStatus);

    List<Order> listOrder(@Param("status") int status);

    List<Order> listOrder4Cancel(@Param("status") int status, @Param("deadTime") Date deadTime);

}