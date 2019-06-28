package com.fang.movie.service;
import com.fang.movie.entity.*;
import com.fang.movie.mapper.*;
import com.fang.movie.util.MoneyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class PayService {

    @Autowired
    private FilmMapper filmMapper;

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
            OrderInfo orderInfo = new OrderInfo();
            String[] seatRowCol = seat.split("-");
            orderInfo.setPrice(filmSchedule.getPrice());
            orderInfo.setOrderId(order.getId());
            orderInfo.setSeatRow(Integer.parseInt(seatRowCol[0]));
            orderInfo.setSeatCol(Integer.parseInt(seatRowCol[1]));
            orderInfoMapper.insert(orderInfo);
        }
        return order;
    }

}
