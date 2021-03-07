/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.terminal;

import ps.purelogic.zkteco4j.command.events.EventCode;
import ps.purelogic.zkteco4j.commands.ZKCommandReply;
import ps.purelogic.zkteco4j.utils.HexUtils;

/**
 *
 * @author Mohammed
 */
public class Test {

    public static void main(String[] args) throws Exception {
        ZKTerminal terminal = new ZKTerminal("10.10.10.50", 4370);//10.10.10.50", 4370);
        ZKCommandReply reply = terminal.connect();
        reply = terminal.connectAuth(15); 
        System.out.println(reply.getCode());
        reply = terminal.disableDevice();
        System.out.println(reply.getCode());
        terminal.getDeviceTime();
        reply = terminal.enableDevice();
        System.out.println(reply.getCode()); 
        
        reply = terminal.enableRealtime(EventCode.EF_FINGER);//enableDevice();
        
        System.out.println(reply.getCode());
        
        System.out.println("Reading");
        
        while (true) {
            int[] response = terminal.readResponse();
            
            System.out.println(HexUtils.bytesToHex(response));
        }
        
        //terminal.disconnect();
        
        /*terminal.disconnect();
        
        ZKCommandReply reply = terminal.getAttendanceRecords();
        
        System.out.println(reply.getCode());*/

        /*if (reply.getCode() == CommandReplyCode.CMD_ACK_UNAUTH) {
            reply = terminal.connectAuth(155);
        }*/

    }
}
