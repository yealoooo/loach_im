package cn.loach.start;

import cn.loach.client.LoachTcpClient;

public class ClientStart {
    public static void main(String[] args) {
        LoachTcpClient loachTcpClient = new LoachTcpClient();
        loachTcpClient.init("127.0.0.1", 8080);
    }
}
