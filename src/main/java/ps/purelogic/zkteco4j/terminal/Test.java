/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.terminal;

import ps.purelogic.zkteco4j.command.events.EventCode;
import ps.purelogic.zkteco4j.commands.GetTimeReply;
import ps.purelogic.zkteco4j.commands.ZKCommandReply;
import ps.purelogic.zkteco4j.utils.HexUtils;
//DC 05 80 0E 86 E6 01 00 | 1C 01 00 00 | 00040000

/**
 *
 * @author Mohammed
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ZKTerminal terminal = new ZKTerminal("10.10.10.50", 4370);//10.10.10.50", 4370);
        ZKCommandReply reply = terminal.connect();
        
        System.out.println(reply.getCode());
        reply = terminal.connectAuth(15);
        System.out.println(reply.getCode());
        GetTimeReply time = terminal.getDeviceTime();
        System.out.println(time.getDeviceDate());
        reply = terminal.enableRealtime(EventCode.EF_ALARM);
        System.out.println(reply.getCode());
        
        terminal.readResponse();
        
        System.out.println("Resp!");
        
        /*terminal.enableRealtime(EventCode.EF_ALARM, EventCode.EF_ATTLOG, EventCode.EF_BUTTON);//enableDevice();
        
        
        int[] resp = terminal.readResponse();
        
        System.out.println(HexUtils.bytesToHex(resp));
        
        terminal.disconnect();*/
        
        /*terminal.disconnect();
        
        ZKCommandReply reply = terminal.getAttendanceRecords();
        
        System.out.println(reply.getCode());*/

        /*if (reply.getCode() == CommandReplyCode.CMD_ACK_UNAUTH) {
            reply = terminal.connectAuth(155);
        }*/

    }
}
