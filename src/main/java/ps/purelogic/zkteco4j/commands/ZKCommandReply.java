/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.commands;

/**
 *
 * @author Mohammed
 */
public class ZKCommandReply {
    
    private final CommandReplyCode code;
    private final int sessionId;
    private final int replyId;
    private final int[] payloads;

    public ZKCommandReply(CommandReplyCode code, int sessionId, int replyId, int[] payloads) {
        this.code = code;
        this.sessionId = sessionId;
        this.replyId = replyId;
        this.payloads = payloads;
    }

    /**
     * @return the code
     */
    public CommandReplyCode getCode() {
        return code;
    }

    /**
     * @return the sessionId
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * @return the replyId
     */
    public int getReplyId() {
        return replyId;
    }

    /**
     * @return the payloads
     */
    public int[] getPayloads() {
        return payloads;
    }
    
    
    
}
