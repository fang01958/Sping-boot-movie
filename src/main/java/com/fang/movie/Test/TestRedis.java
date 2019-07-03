package com.fang.movie.Test;

import redis.clients.jedis.Jedis;

public class TestRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost");
        jedis.set("f","fff");
        System.out.println(jedis.get("f"));
    }
}
