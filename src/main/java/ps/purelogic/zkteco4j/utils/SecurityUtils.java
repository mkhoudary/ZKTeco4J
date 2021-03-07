/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.utils;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Mohammed
 */
public class SecurityUtils {

    public static int calculateChecksum(int[] inputPayload) {
        int[] payload;
        int chk32b = 0;
        int j = 1;

        if (inputPayload.length % 2 == 1) {
            payload = new int[inputPayload.length + 1];
            System.arraycopy(inputPayload, 0, payload, 0, inputPayload.length);
            payload[payload.length - 1] = 0;
        } else {
            payload = inputPayload;
        }

        while (j < payload.length) {
            int num_16b = payload[j - 1] + (payload[j] << 8);
            chk32b = chk32b + num_16b;
            j += 2;
        }

        chk32b = (chk32b & 0xffff) + ((chk32b & 0xffff0000) >> 16);

        int chk_16b = chk32b ^ 0xFFFF;

        return chk_16b;
    }

    public static long makeKey(int key, int sessionId) {
        int k;
        int i;

        short swp;

        k = 0;
        for (i = 0; i < 32; i++) {
            if ((key & (1 << i)) != 0) {
                k = (k << 1 | 1);
            } else {
                k = k << 1;
            }
        }

        k += sessionId;

        String hex = StringUtils.leftPad(Integer.toHexString(k), 8, "0");

        int[] response = new int[4];
        int index = 3;

        while (hex.length() > 0) {
            response[index] = (int) Long.parseLong(hex.substring(0, 2), 16);
            index--;

            hex = hex.substring(2);
        }

        response[0] ^= 'Z';
        response[1] ^= 'K';
        response[2] ^= 'S';
        response[3] ^= 'O';

        long finalKey = response[0] + (response[1] * 0x100) + (response[2] * 0x10000) + (response[3] * 0x1000000);

        swp = (short) (finalKey >> 16);
        finalKey = (finalKey << 16) + swp;

        return finalKey;
    }

    public static int[] authKey(int comKey, int sessionId) {
        long k = Long.parseLong(Integer.toUnsignedString((int) makeKey(comKey, sessionId)));
        int rand = (int) (Math.random() * 255);

        int[] response = new int[4];
        int index = 3;

        String hex = StringUtils.leftPad(Long.toHexString(k), 8, "0");

        while (index >= 0) {
            response[index] = (int) Long.parseLong(hex.substring(0, 2), 16);
            index--;

            hex = hex.substring(2);
        }

        response[0] ^= rand;
        response[1] ^= rand;
        response[2] = rand;
        response[3] ^= rand;

        return response;
    }

}
