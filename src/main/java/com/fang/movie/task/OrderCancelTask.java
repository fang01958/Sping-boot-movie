package com.fang.movie.task;

import com.fang.movie.emuns.OrderStatusEnum;
import com.fang.movie.entity.Order;
import com.fang.movie.mapper.OrderMapper;
import com.fang.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class OrderCancelTask extends BaseTask {


    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

    @Override
    @Scheduled(cron = "0/30 * * * * *")
    public void doTask() {
        processCancel();
    }

    public void processCancel(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,-1);
        List<Order> orders = orderMapper.listOrder4Cancel(OrderStatusEnum.待支付.getK(), cal.getTime());
        for (Order order : orders) {
            try{
                orderService.processCancelOrder(order);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
