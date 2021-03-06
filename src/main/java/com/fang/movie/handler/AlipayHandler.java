package com.fang.movie.handler;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.fang.movie.entity.Film;
import com.fang.movie.entity.Order;
import com.fang.movie.exception.MyException;
import com.fang.movie.service.OrderService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AlipayHandler implements InitializingBean {

    @Value("${alipay.appid}")
    private String appid;

    @Value("${alipay.trade.pay.url}")
    private String payUrl;

    @Value("${alipay.merchant.private.key}")
    private String MerchantPrivatekey;

    @Value("${alipay.public.key}")
    private String alipayPublicKey;

    @Value("${alipay.return.url}")
    private String returnUrl;

    @Value("${alipay.notify.url}")
    private String notifyUrl;

    @Value("${alipay.body}")
    private String body;

    @Autowired
    private OrderService orderService;

    private AlipayClient alipayClient;

    public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
    public static final String TRADE_FINISHED = "TRADE_FINISHED";


    @Override
    public void afterPropertiesSet() throws Exception {
        alipayClient = new DefaultAlipayClient(payUrl,appid,MerchantPrivatekey,
                "json","utf-8",alipayPublicKey,"RSA2");
    }

    public String createPayform(Order order, Film film){
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(returnUrl + order.getId());
        alipayRequest.setNotifyUrl(notifyUrl);//在公共参数中设置回跳和通知地址

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ order.getId() +"\","
                + "\"total_amount\":\""+ order.getAmount() +"\","
                + "\"subject\":\""+ film.getFilmName() +"\","
                + "\"body\":\""+ body +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(form);
        return form;
    }



    public void processNotify(Map<String,String[]> paramterMap){
        Map<String,String> map = new HashMap<>(paramterMap.size());
        for (Map.Entry<String, String[]> entry : paramterMap.entrySet()) {
            map.put(entry.getKey(),entry.getValue()[0]);
        }

        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(map, alipayPublicKey, "utf-8", "RSA2");
            if(signVerified){
                throw new MyException("验签失败");
            }
            //验签成功
            String trade_status = map.get("trade_status");
            if (!TRADE_SUCCESS.equals(trade_status) && !TRADE_FINISHED.equals(trade_status)){
                throw new MyException("支付失败");
            }
            String orderId = map.get("out_trade_no");
            String total_amount = map.get("total_amount");
            String app_id = map.get("app_id");

            if (!appid.equals(app_id)){
                throw new MyException("appid不正确");
            }

            orderService.processAlipayNotify(Integer.parseInt(orderId),total_amount);

        } catch (AlipayApiException e) {
            throw new MyException("支付宝异步通知处理异常");
        }

    }

    public void queryOrder(Order order){
        alipayClient = new DefaultAlipayClient(payUrl,appid,MerchantPrivatekey,
                "json","utf-8",alipayPublicKey,"RSA2");

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{" +
                "\"out_trade_no\":\""+ order.getId() +"\"" +
                "  }");

        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);

            if(!response.isSuccess()){
                System.out.println("调用失败");
                return;
            }
            String totalAmount = response.getTotalAmount();
            String tradeStatus = response.getTradeStatus();

            if (!TRADE_SUCCESS.equals(tradeStatus) && !TRADE_FINISHED.equals(tradeStatus)){
                throw new MyException("支付失败");
            }
            orderService.processQueryOrder(order,totalAmount);

        } catch (Exception e){
            e.printStackTrace();
            throw new MyException("阿里订单查询失败");
        }


    }



}
