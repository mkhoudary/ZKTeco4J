/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.terminal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import ps.purelogic.zkteco4j.commands.CommandCode;
import ps.purelogic.zkteco4j.commands.ZKCommand;

/**
 *
 * @author Mohammed
 */
public class ZKTerminal {

    private final String ip;
    private final int port;

    private Socket socket;

    private InputStream is;
    private OutputStream os;

    private int sessionId;
    private int replyNo;

    public ZKTerminal(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void connect() throws IOException {
        sessionId = 0;
        replyNo = 0;

        socket = new Socket(ip, port);
        is = socket.getInputStream();
        os = socket.getOutputStream();

        int[] packet = ZKCommand.getPacket(CommandCode.CMD_CONNECT, sessionId, replyNo, null);

        for (int toSend : packet) {
            os.write(toSend);
        }

        os.flush();

        replyNo++;
    }

    public void disconnect() throws IOException {
        int[] packet = ZKCommand.getPacket(CommandCode.CMD_EXIT, sessionId, replyNo, null);

        for (int toSend : packet) {
            os.write(toSend);
        }

        os.flush();

        replyNo++;
        
        is.close();
        os.close();
        socket.close();
    }

}
