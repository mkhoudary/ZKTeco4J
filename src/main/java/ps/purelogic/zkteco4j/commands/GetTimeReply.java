/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.commands;

import java.text.ParseException;
import java.util.Date;
import ps.purelogic.zkteco4j.utils.HexUtils;

/**
 *
 * @author Mohammed
 */
public class GetTimeReply extends ZKCommandReply {

    private final Date deviceDate;

    public GetTimeReply(CommandReplyCode code, int sessionId, int replyId, int[] payloads) throws ParseException {
        super(code, sessionId, replyId, payloads);

        String payloadsStr = HexUtils.bytesToHex(payloads);

        long encDate = Integer.valueOf(payloadsStr.substring(6, 8), 16) * 0x1000000L
                + (Integer.valueOf(payloadsStr.substring(4, 6), 16) * 0x10000L)
                + (Integer.valueOf(payloadsStr.substring(2, 4), 16) * 0x100L)
                + (Integer.valueOf(payloadsStr.substring(0, 2), 16));

        deviceDate = HexUtils.extractDate(encDate);
    }

    /**
     * @return the deviceDate
     */
    public Date getDeviceDate() {
        return deviceDate;
    }

}
