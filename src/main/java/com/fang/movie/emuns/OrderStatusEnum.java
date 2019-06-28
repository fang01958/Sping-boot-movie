package com.fang.movie.emuns;

public enum OrderStatusEnum {

    待支付(0,"待支付"),支付成功(1,"支付成功"),取消订单(2,"取消订单");

    private int k;
    private String v;

    private OrderStatusEnum(int k, String v){
        this.k = k;
        this.v = v;
    }

    public int getK() {
        return k;
    }

    public String getV() {
        return v;
    }
}
