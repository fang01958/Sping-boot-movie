package com.fang.movie.controller;
import com.fang.movie.emuns.OrderStatusEnum;
import com.fang.movie.entity.Cinema;
import com.fang.movie.entity.Order;
import com.fang.movie.entity.OrderInfo;
import com.fang.movie.service.FilmService;
import com.fang.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

@Controller
public class PageController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = "/")
    public String toIndex(){
        return "index";
    }

    @RequestMapping(value = "/toFilmInfo/{filmId}")
    public String toFilmInfo(@PathVariable int filmId){
        return "filmInfo";
    }

    @RequestMapping(value = "/toFilmCinema/{filmId}")
    public String toBuyTicket(@PathVariable int filmId){
        return "filmCinema";
    }

    @RequestMapping(value = "/toCinemaInfo/{filmId}/{cinemaCode}")
    public String toCinemaInfo(@PathVariable int filmId,@PathVariable String cinemaCode){
        return "cinemaInfo";
    }

    @RequestMapping(value = "/toTicket/{filmId}/{cinemaCode}/{sceneId}")
    public String toTicket(@PathVariable int filmId,@PathVariable String cinemaCode,@PathVariable int sceneId){
        return "ticket";
    }

    @RequestMapping(value = "/toSettlement/{filmId}/{cinemaCode}/{sceneId}")
    public String toSettlement(@PathVariable int filmId,
                               @PathVariable String cinemaCode,
                               @PathVariable int sceneId,
                               String seatInfo, HttpServletRequest request){
        request.setAttribute("seats",seatInfo);
        return "settlement";
    }

    @RequestMapping(value = "pay/toOrderInfo/{orderId}")
    public String toOrderInfo(@PathVariable int orderId , HttpServletRequest request){
        Order order = orderService.getOrder(orderId);
        request.setAttribute("filmId",order.getFilmId());
        request.setAttribute("sceneId",order.getFilmScheduleId());
        Cinema cinema = filmService.getCinema(order.getCinemaId());
        request.setAttribute("cinemaCode",cinema.getCinemaCode());

        List<OrderInfo> orderInfos = orderService.listOrderInfo(orderId);
        StringBuilder sx = new StringBuilder();
        for (OrderInfo orderInfo : orderInfos) {
            sx.append(orderInfo.getSeatRow()).append("-").append(orderInfo.getSeatCol()).append(",");
        }
        request.setAttribute("seats",sx.toString().substring(0,sx.length()-1));
        request.setAttribute("success",order.getStatus() == OrderStatusEnum.支付成功.getK());

        return "orderInfo";
    }

    @RequestMapping(value = "/picture/{fileName}",method = RequestMethod.GET)
    public void showPic(@PathVariable String fileName, HttpServletResponse response){
        response.setContentType("image/png");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
        } catch (Exception e){
            e.printStackTrace();
        }

        try (FileInputStream fis = new FileInputStream("/Users/fang/IO/filmPic/" + fileName)){
            byte[] buff = new byte[1024];
            int length = -1;
            while (-1 != (length = fis.read(buff))){
                os.write(buff,0,length);
                os.flush();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
