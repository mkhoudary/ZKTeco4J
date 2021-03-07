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
    EF_ATTLOG(1),
    //Pressed finger
    EF_FINGER(2),
    //Enrolled user
    EF_ENROLLUSER(4),
    //Enrolled user
    EF_ENROLLFINGER(8),
    //Pressed keyboard key
    EF_BUTTON(16),
    EF_UNLOCK(32),
    //Registered user placed finger
    EF_VERIFY(128),
    //Fingerprint score in enroll procedure
    EF_FPFTR(256),
    //Triggered alarm
    EF_ALARM(512);

    private final int code;

    private EventCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
