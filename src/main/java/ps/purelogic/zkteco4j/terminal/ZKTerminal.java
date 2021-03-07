/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.terminal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import ps.purelogic.zkteco4j.commands.CommandCode;
import ps.purelogic.zkteco4j.commands.CommandReplyCode;
import ps.purelogic.zkteco4j.commands.ZKCommand;
import ps.purelogic.zkteco4j.commands.ZKCommandReply;
import ps.purelogic.zkteco4j.utils.HexUtils;
import ps.purelogic.zkteco4j.utils.SecurityUtils;

/**
 *
 * @author Mohammed
 */
public class ZKTerminal {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private DatagramSocket socket;
    private InetAddress address;

    private final String ip;
    private final int port;

    private InputStream is;
    private OutputStream os;

    private int sessionId;
    private int replyNo;

    public ZKTerminal(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public ZKCommandReply connect() throws IOException {
        sessionId = 0;
        replyNo = 0;

        socket = new DatagramSocket();
        address = InetAddress.getByName(ip);

        int[] toSend = ZKCommand.getPacket(CommandCode.CMD_CONNECT, sessionId, replyNo, null);
        byte[] buf = new byte[toSend.length];

        int index = 0;

        for (int byteToSend : toSend) {
            buf[index++] = (byte) byteToSend;
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        replyNo++;

        int[] response = readResponse();

        CommandReplyCode replyCode = CommandReplyCode.decode(response[0] + (response[1] * 0x100));

        sessionId = response[4] + (response[5] * 0x100);
        int replyId = response[6] + (response[7] * 0x100);

        int[] payloads = new int[response.length - 8];

        System.arraycopy(response, 8, payloads, 0, payloads.length);

        return new ZKCommandReply(replyCode, sessionId, replyId, payloads);
    }

    public ZKCommandReply connectAuth(int comKey) throws IOException {
        int[] key = SecurityUtils.authKey(comKey, sessionId);

        int[] toSend = ZKCommand.getPacket(CommandCode.CMD_AUTH, sessionId, replyNo, key);
        byte[] buf = new byte[toSend.length];

        int index = 0;

        for (int byteToSend : toSend) {
            buf[index++] = (byte) byteToSend;
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        replyNo++;

        int[] response = readResponse();

        CommandReplyCode replyCode = CommandReplyCode.decode(response[0] + (response[1] * 0x100));

        int replyId = response[6] + (response[7] * 0x100);

        int[] payloads = new int[response.length - 8];

        System.arraycopy(response, 8, payloads, 0, payloads.length);

        return new ZKCommandReply(replyCode, sessionId, replyId, payloads);
    }

    public ZKCommandReply enableRealtime() throws IOException {
        int[] realtime = new int[4];
        realtime[0] = 0;
        realtime[1] = 0;
        realtime[2] = 0xff;
        realtime[3] = 0xff;

        int[] toSend = ZKCommand.getPacket(CommandCode.CMD_REG_EVENT, sessionId, replyNo, realtime);
        byte[] buf = new byte[toSend.length];

        int index = 0;

        for (int byteToSend : toSend) {
            buf[index++] = (byte) byteToSend;
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        replyNo++;

        int[] response = readResponse();

        CommandReplyCode replyCode = CommandReplyCode.decode(response[8] + (response[9] * 0x100));

        int replyId = response[14] + (response[15] * 0x100);

        int[] payloads = new int[response.length - 16];

        System.arraycopy(response, 16, payloads, 0, payloads.length);

        return new ZKCommandReply(replyCode, sessionId, replyId, payloads);
    }

    public ZKCommandReply getAttendanceRecords() throws IOException, ParseException {
        int[] toSend = ZKCommand.getPacket(CommandCode.CMD_ATTLOG_RRQ, sessionId, replyNo, null);
        byte[] buf = new byte[toSend.length];

        int index = 0;

        for (int byteToSend : toSend) {
            buf[index++] = (byte) byteToSend;
        }

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);

        replyNo++;

        int[] response = readResponse();

        CommandReplyCode replyCode = CommandReplyCode.decode(response[0] + (response[1] * 0x100));

        if (replyCode == CommandReplyCode.CMD_PREPARE_DATA) {
            response = readResponse();
        }

        String attendance = HexUtils.bytesToHex(response).substring(24);

        while (attendance.length() > 0) {
            String record = attendance.substring(0, 80);

            int seq = Integer.valueOf(record.substring(2, 4) + record.substring(0, 2), 16);
            record = record.substring(4);

            String userId = Character.toString((char) Integer.valueOf(record.substring(0, 2), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(2, 4), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(4, 6), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(6, 8), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(8, 10), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(10, 12), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(12, 14), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(14, 16), 16).intValue())
                    + Character.toString((char) Integer.valueOf(record.substring(16, 18), 16).intValue());

            record = record.substring(48);

            int method = Integer.valueOf(record.substring(0, 2), 16);

            record = record.substring(2);

            long encDate = Integer.valueOf(record.substring(6, 8), 16) * 0x1000000L
                    + (Integer.valueOf(record.substring(4, 6), 16) * 0x10000L)
                    + (Integer.valueOf(record.substring(2, 4), 16) * 0x100L)
                    + (Integer.valueOf(record.substring(0, 2), 16));

            long second = encDate % 60;
            encDate = encDate / 60;
            long minute = encDate % 60;
            encDate = encDate / 60;
            long hour = encDate % 24;
            encDate = encDate / 24;
            long day = encDate % 31 + 1;
            encDate = encDate / 31;
            long month = encDate % 12 + 1;
            encDate = encDate / 12;
            long year = (long) Math.floor(encDate + 2000);

            String attendanceDateStr = year + "-"
                    + StringUtils.leftPad(String.valueOf(month), 2, "0") + "-"
                    + StringUtils.leftPad(String.valueOf(day), 2, "0") + " "
                    + StringUtils.leftPad(String.valueOf(hour), 2, "0") + ":"
                    + StringUtils.leftPad(String.valueOf(minute), 2, "0") + ":"
                    + StringUtils.leftPad(String.valueOf(second), 2, "0");

            Date attendanceDate = DATE_FORMAT.parse(attendanceDateStr);

            System.out.println(attendanceDate);

            record = record.substring(8);

            int operation = Integer.valueOf(record.substring(0, 2), 16);

            attendance = attendance.substring(80);
        }

        int replyId = response[6] + (response[7] * 0x100);

        int[] payloads = new int[response.length - 8];

        System.arraycopy(response, 8, payloads, 0, payloads.length);

        return new ZKCommandReply(replyCode, sessionId, replyId, payloads);
    }

    public void disconnect() throws IOException {
        /*int[] packet = ZKCommand.getPacket(CommandCode.CMD_EXIT, sessionId, replyNo, null);

        for (int toSend : packet) {
            os.write(toSend);
        }

        os.flush();

        replyNo++;*/

        is.close();
        os.close();
        socket.close();
    }

    private int[] readResponse() throws IOException {
        byte[] buf = new byte[1000000];

        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);

        int[] response = new int[packet.getLength()];

        for (int i = 0; i < response.length; i++) {
            response[i] = buf[i] & 0xFF;
        }

        return response;

        /*int index = 0;
        int[] data = new int[1000000];

        int read;
        int size = 0;

        boolean reading = true;

        while (reading && (read = is.read()) != -1) {
            if (index >= 4 && index <= 7) {
                size += read * Math.pow(16, index - 4);
            } else if (index > 7) {
                if (index - 7 >= size) {
                    reading = false;
                }
            }

            data[index] = read;
            index++;
        }

        int[] finalData = new int[index];

        System.arraycopy(data, 0, finalData, 0, index);

        return finalData;*/
    }

}
