package com.fang.movie.service;
import com.fang.movie.entity.*;
import com.fang.movie.exception.MyException;
import com.fang.movie.mapper.*;
import com.fang.movie.util.MoneyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PayService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private FilmScheduleMapper filmScheduleMapper;


    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(int filmId, String cinemaCode , int sceneId , String seatInfo){
        Cinema cinema = cinemaMapper.queryCienma(cinemaCode);
        FilmSchedule filmSchedule = filmScheduleMapper.selectByPrimaryKey(sceneId);
        if (null == cinema || filmSchedule == null || StringUtils.isBlank(seatInfo) || StringUtils.isBlank(cinemaCode)){
            throw new MyException("参数有异常");
        }

        String[] seats = seatInfo.split(",");
        String amount = MoneyUtils.mul(String.valueOf(seats.length), filmSchedule.getPrice());
        Order order = new Order();
        order.setCinemaId(cinema.getId());
        order.setAmount(amount);
        order.setCreateTime(new Date());
        order.setFilmId(filmId);
        order.setModifyTime(new Date());
        order.setFilmScheduleId(sceneId);
        order.setStatus(0);
        orderMapper.insert(order);

        for (String seat : seats) {
            String[] seatRowCol = seat.split("-");

//            //防止并发
//            Boolean aBoolean = redisService.cacheSeatInfo(sceneId, Integer.parseInt(seatRowCol[0]), Integer.parseInt(seatRowCol[1]));
//            if (!aBoolean){
//                throw new MyException("座位号已被选中，请重新选择");
//            }

            //查询数据有没有
            OrderInfo info = orderInfoMapper.loadSelectedSeats(sceneId, Integer.parseInt(seatRowCol[0]), Integer.parseInt(seatRowCol[1]));

            if (null != info){
                throw new MyException("座位号已被选中，请重新选择");
            }

            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setPrice(filmSchedule.getPrice());
            orderInfo.setOrderId(order.getId());
            orderInfo.setSeatRow(Integer.parseInt(seatRowCol[0]));
            orderInfo.setSeatCol(Integer.parseInt(seatRowCol[1]));
            orderInfoMapper.insert(orderInfo);
        }
        return order;
    }

}
