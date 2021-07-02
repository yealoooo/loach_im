package cn.loach.start;

import cn.loach.server.LoachTcpServer;

public class ServerStart {

    public static void main(String[] args) {
        LoachTcpServer loachTcpServer = new LoachTcpServer();
        loachTcpServer.init();
    }
}
