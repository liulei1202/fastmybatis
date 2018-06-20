package com.gitee.fastmybatis.generator;

import com.gitee.fastmybatis.generator.client.Client;

public class Run_t_userAll {

    public static void main(String[] args) {
        Client client = new Client();
        client.genAll("cfg/t_user_all.properties");
    }

}
