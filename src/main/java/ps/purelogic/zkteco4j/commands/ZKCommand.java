/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.commands;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import ps.purelogic.zkteco4j.utils.SecurityUtils;

/**
 *
 * @author Mohammed
 */
public class ZKCommand {

    public final static int[] PACKET_START = {0x50, 0x50, 0x82, 0x7d};

    public static int[] getPacket(CommandCode commandCode, int sessionId, int replyNumber, int[] data) {
        int[] payloadForChecksum = new int[6 + (data == null ? 0 : data.length)];
        int[] finalPayload = new int[8 + (data == null ? 0 : data.length)];
        int[] finalPacket = new int[8 + finalPayload.length];

        byte[] commandBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(commandCode.getCode()).array();
        byte[] sessionIdBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(sessionId).array();
        byte[] replyNumberBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(replyNumber).array();

        payloadForChecksum[0] = commandBytes[0] & 0xFF;
        payloadForChecksum[1] = commandBytes[1] & 0xFF;
        finalPayload[0] = commandBytes[0] & 0xFF;
        finalPayload[1] = commandBytes[1] & 0xFF;

        payloadForChecksum[2] = sessionIdBytes[0] & 0xFF;
        payloadForChecksum[3] = sessionIdBytes[1] & 0xFF;
        finalPayload[4] = sessionIdBytes[0] & 0xFF;
        finalPayload[5] = sessionIdBytes[1] & 0xFF;

        payloadForChecksum[4] = replyNumberBytes[0] & 0xFF;
        payloadForChecksum[5] = replyNumberBytes[1] & 0xFF;
        finalPayload[6] = replyNumberBytes[0] & 0xFF;
        finalPayload[7] = replyNumberBytes[1] & 0xFF;

        if (data != null) {
            System.arraycopy(data, 0, payloadForChecksum, 6, data.length);
            System.arraycopy(data, 0, finalPayload, 8, data.length);
        }

        int checksum = SecurityUtils.calculateChecksum(payloadForChecksum);

        byte[] checksumBytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(checksum).array();

        finalPayload[2] = checksumBytes[0] & 0xFF;
        finalPayload[3] = checksumBytes[1] & 0xFF;

        byte[] payloadSize = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(finalPayload.length).array();

        /* Preparing final packet */
        System.arraycopy(PACKET_START, 0, finalPacket, 0, PACKET_START.length);

        finalPacket[4] = payloadSize[0];
        finalPacket[5] = payloadSize[1];
        finalPacket[6] = payloadSize[2];
        finalPacket[7] = payloadSize[3];

        System.arraycopy(finalPayload, 0, finalPacket, 8, finalPayload.length);

        return finalPacket;
    }

}
