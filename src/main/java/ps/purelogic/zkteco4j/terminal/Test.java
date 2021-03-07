/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.terminal;

import ps.purelogic.zkteco4j.commands.GetTimeReply;
//DC 05 80 0E 86 E6 01 00 | 1C 01 00 00 | 00040000

/**
 *
 * @author Mohammed
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ZKTerminal terminal = new ZKTerminal("192.168.1.201", 4370);//10.10.10.50", 4370);
        terminal.connect();
        //terminal.connectAuth(15);
        GetTimeReply reply = terminal.getDeviceTime();
        System.out.println(reply.getDeviceDate());
        
        /*terminal.disconnect();
        
        ZKCommandReply reply = terminal.getAttendanceRecords();
        
        System.out.println(reply.getCode());*/

        /*if (reply.getCode() == CommandReplyCode.CMD_ACK_UNAUTH) {
            reply = terminal.connectAuth(155);
        }*/

    }
}
