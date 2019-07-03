package com.fang.movie.task;
import com.fang.movie.emuns.OrderStatusEnum;
import com.fang.movie.entity.Order;
import com.fang.movie.handler.AlipayHandler;
import com.fang.movie.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AlipayQueryTask extends BaseTask {

    @Autowired
    private AlipayHandler alipayHandler;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    @Scheduled(cron = "0/15 * * * * *")
    public void doTask() {
//        processQuery();
    }

    public void processQuery(){
        //从数据库查询阿里代支付的订单
        List<Order> orders = orderMapper.listOrder(OrderStatusEnum.待支付.getK());
        for (Order order : orders) {
            try{
                alipayHandler.queryOrder(order);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
