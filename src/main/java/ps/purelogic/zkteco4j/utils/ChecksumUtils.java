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
            payload[payload.length - 1] += 0x00;
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

    public static void main(String args[]) {
        int[] input = {0x0b, 0x00, 0xf3, 0x8d, 0x03, 0x00, 0x5a, 0x4b, 0x46, 0x61, 0x63, 0x65, 0x56, 0x65, 0x72, 0x73, 0x69, 0x6f, 0x6e, 0x00};
        
        System.out.println(calculateChecksum(input));
    }

}
