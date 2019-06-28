package com.fang.movie.service;
import com.fang.movie.emuns.OrderStatusEnum;
import com.fang.movie.entity.Order;
import com.fang.movie.entity.OrderInfo;
import com.fang.movie.exception.MyException;
import com.fang.movie.mapper.OrderInfoMapper;
import com.fang.movie.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    public Order getOrder(int orderId){
        return orderMapper.selectByPrimaryKey(orderId);
    }

    public List<OrderInfo> listOrderInfo(int orderId){
        return orderInfoMapper.listOrderInfo(orderId);
    }

    public void processAlipayNotify(int orderId, String totalAmount){
        //参数校验
        Order order = orderMapper.selectByPrimaryKey(orderId);
        if (null ==order){
            throw new MyException("订单不存在");
        }

        if (order.getStatus() != OrderStatusEnum.待支付.getK()){
            throw new MyException("订单状态不正确");
        }

        if (order.getStatus() == OrderStatusEnum.取消订单.getK()){
            throw new MyException("订单状态不正确");
        }

        if (Double.parseDouble(order.getAmount()) != Double.parseDouble(totalAmount)){
            throw new MyException("订单金额不相等");
        }

        //修改订单状态
        int rows = orderMapper.updateStatus(orderId, order.getStatus(), OrderStatusEnum.支付成功.getK());
        if (1 != rows){
            throw new MyException("异步处理失败");
        }
    }
}
