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
import ps.purelogic.zkteco4j.utils.HexUtils;

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
        
        int[] response = readResponse();
        
        System.out.println("Resp " + HexUtils.bytesToHex(response));
    }

    public void disconnect() throws IOException {
        int[] packet = ZKCommand.getPacket(CommandCode.CMD_EXIT, sessionId, replyNo, null);

        for (int toSend : packet) {
            os.write(toSend);
        }

        os.flush();

        replyNo++;
        
        int[] response = readResponse();
        
        System.out.println("Resp " + HexUtils.bytesToHex(response));

        is.close();
        os.close();
        socket.close();
    }

    private int[] readResponse() throws IOException {
        int index = 0;
        int[] data = new int[1000000];

        int read;
        int size = 0;

        boolean reading = true;

        while (reading && (read = is.read()) != -1) {
            if (index >= 4 && index <= 7) {
                size += read * Math.pow(16, index - 4);
            } else if (index > 7) {
                if (index - 7 > size) {
                    reading = false;
                }
            }

            data[index] = read;
            index++;
        }

        int[] finalData = new int[index];

        System.arraycopy(data, 0, finalData, 0, index);

        return finalData;
    }

}
