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
        int chk_32b = 0;
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
            chk_32b = chk_32b + num_16b;
            j += 2;
        }

        chk_32b = (chk_32b & 0xffff) + ((chk_32b & 0xffff0000) >> 16);

        int chk_16b = chk_32b ^ 0xFFFF;

        return chk_16b;
    }

    public static void main(String[] args) {
        int[] stuff = {0xd0, 0x07, 0x29, 0x6a, 0xf3, 0x8d, 0x0a, 0x00, 0x09};
        System.out.println(calculateChecksum(stuff));
    }
}
