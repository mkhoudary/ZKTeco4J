/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.tests;

import junit.framework.TestCase;
import org.junit.Test;
import ps.purelogic.zkteco4j.commands.CommandCode;
import ps.purelogic.zkteco4j.commands.ZKCommand;
import ps.purelogic.zkteco4j.utils.HexUtils;

/**
 *
 * @author Mohammed
 */
public class ZKCommandTest {
    
    public ZKCommandTest() {
    }
    
    @Test
    public void zkCommandTest() {
        int[] someData = {0x7E, 0x4F, 0x53, 0x00};

        ZKCommand command = new ZKCommand(CommandCode.CMD_OPTIONS_RRQ, someData);

        String result = HexUtils.bytesToHex(command.getPacket(0xC0C5, 0x0005));
        
        TestCase.assertEquals("5050827D0C0000000B0058EFC5C005007E4F5300", result);
    }
    
}
