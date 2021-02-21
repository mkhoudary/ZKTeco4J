/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.command.events;

/**
 *
 * @author Mohammed
 */
public enum EventCode {
    //Attendance entry
    EF_ATTLOG(1000),
    //Pressed finger
    EF_FINGER(1001),
    //Enrolled user
    EF_ENROLLUSER(1002),
    //Enrolled user
    EF_ENROLLFINGER(1003),
    //Pressed keyboard key
    EF_BUTTON(1004),
    EF_UNLOCK(1005),
    //Registered user placed finger
    EF_VERIFY(1006),
    //Fingerprint score in enroll procedure
    EF_FPFTR(1007),
    //Triggered alarm
    EF_ALARM(1009);

    private final int code;

    private EventCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
