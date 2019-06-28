package com.fang.movie.controller;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fang.movie.dto.ResponseDTO;
import com.fang.movie.entity.Film;
import com.fang.movie.entity.Order;
import com.fang.movie.handler.AlipayHandler;
import com.fang.movie.service.FilmService;
import com.fang.movie.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
public class PayController extends BaseController {

    @Autowired
    private PayService payService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private AlipayHandler alipayHandler;


    @RequestMapping(value = "/pay/toPay/{filmId}/{cinemaCode}/{sceneId}")
    @ResponseBody
    public ResponseDTO toPay(@PathVariable int filmId,@PathVariable String cinemaCode,
                             @PathVariable int sceneId, String seatInfo) {

        //TODO 生成订单
        Order order = payService.createOrder(filmId, cinemaCode, sceneId, seatInfo);
        Film film = filmService.queryFilm(filmId);

        return ResponseDTO.success(alipayHandler.createPayform(order,film));
    }

    @RequestMapping(value = "/pay/alipayNotify")
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response){
        alipayHandler.processNotify(request.getParameterMap());
        try {
            OutputStream os = response.getOutputStream();
            os.write("success".getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
