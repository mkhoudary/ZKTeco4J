/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.tests;

import junit.framework.TestCase;
import org.junit.Test;
import ps.purelogic.zkteco4j.utils.SecurityUtils;

/**
 *
 * @author Mohammed
 */
public class ChecksumUtilsTest {
    
    public ChecksumUtilsTest() {
    }
    
    @Test
    public void headerChecksumTest() {
        int[] input = {0x0b, 0x00, 0xf3, 0x8d, 0x03, 0x00, 0x5a, 0x4b, 0x46, 0x61, 0x63, 0x65, 0x56, 0x65, 0x72, 0x73, 0x69, 0x6f, 0x6e, 0x00};
        
        int result = SecurityUtils.calculateChecksum(input);
        
        TestCase.assertEquals(5978, result);
    }
    
}
