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
public enum CommandReply {
    //The request was processed sucessfully
    CMD_ACK_OK(2000),
    //There was an error when processing the request
    CMD_ACK_ERROR(2001),
    CMD_ACK_DATA(2002),
    CMD_ACK_RETRY(2003),
    CMD_ACK_REPEAT(2004),
    //Connection not authorized
    CMD_ACK_UNAUTH(2005),
    //Received unknown command
    CMD_ACK_UNKNOWN(65535),
    CMD_ACK_ERROR_CMD(65533),
    CMD_ACK_ERROR_INIT(65532),
    CMD_ACK_ERROR_DATA(65531);

    private final int code;

    private CommandReply(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
