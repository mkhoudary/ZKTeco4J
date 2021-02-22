/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.utils;

/**
 *
 * @author Mohammed
 */
public class ChecksumUtils {

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
}
