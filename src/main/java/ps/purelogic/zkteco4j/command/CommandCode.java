/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps.purelogic.zkteco4j.command;

/**
 *
 * @author Mohammed
 */
public enum CommandCode {
    //Begin cognnection
    CMD_CONNECT(1000),
    //Disconnect
    CMD_EXIT(1001),
    //Change machine state to "normal work"
    CMD_ENABLEDEVICE(1002),
    //Disables fingerprint, rfid reader and keyboard
    CMD_DISABLEDEVICE(1003),
    //Restart machine
    CMD_RESTART(1004),
    //Shut-down machine
    CMD_POWEROFF(1005),
    //Change machine state to "idle"
    CMD_SLEEP(1006),
    //Change machine state to "awaken"
    CMD_RESUME(1007),
    //Capture fingerprint picture
    CMD_CAPTUREFINGER(1009),
    //Test if fingerprint exists
    CMD_TEST_TEMP(1011),
    //Capture the entire image
    CMD_CAPTUREIMAGE(1012),
    //Refresh the machine stored data
    CMD_REFRESHDATA(1013),
    //Refresh the configuration parameters
    CMD_REFRESHOPTION(1014),
    //Test voice
    CMD_TESTVOICE(1017),
    //Request the firmware edition
    CMD_GET_VERSION(1100),
    //Change transmission speed
    CMD_CHANGE_SPEED(1101),
    //Request to begin session using commkey
    CMD_AUTH(1102),
    //Prepare for data transmission
    CMD_PREPARE_DATA(1500),
    //Data packet
    CMD_DATA(1501),
    //Release buffer used for data transmission
    CMD_FREE_DATA(1502),
    //Read/Write a large data set
    CMD_DATA_WRRQ(1503),
    //Indicates that it is ready to receive data
    CMD_DATA_RDY(1504),
    //Read saved data
    CMD_DB_RRQ(7),
    //Upload user data
    CMD_USER_WRQ(8),
    //Read user fingerprint template
    CMD_USERTEMP_RRQ(9),
    //Upload user fingerprint template
    CMD_USERTEMP_WRQ(10),
    //Read configuration value of the machine
    CMD_OPTIONS_RRQ(11),
    //Change configuration value of the machine
    CMD_OPTIONS_WRQ(12),
    //Request attendance log
    CMD_ATTLOG_RRQ(13),
    //Delete data
    CMD_CLEAR_DATA(14),
    //Delete attendance record
    CMD_CLEAR_ATTLOG(15),
    //Delete user
    CMD_DELETE_USER(18),
    //Delete user fingerprint template
    CMD_DELETE_USERTEMP(19),
    //Clears admins privileges
    CMD_CLEAR_ADMIN(20),
    //Read user group
    CMD_USERGRP_RRQ(21),
    //Set user group
    CMD_USERGRP_WRQ(22),
    //Get user timezones
    CMD_USERTZ_RRQ(23),
    //Set the user timezones
    CMD_USERTZ_WRQ(24),
    //Get group timezone
    CMD_GRPTZ_RRQ(25),
    //Set group timezone
    CMD_GRPTZ_WRQ(26),
    //Get device timezones
    CMD_TZ_RRQ(27),
    //Set device timezones
    CMD_TZ_WRQ(28),
    //Get group combination to unlock
    CMD_ULG_RRQ(29),
    //Set group combination to unlock
    CMD_ULG_WRQ(30),
    //Unlock door for a specified amount of time
    CMD_UNLOCK(31),
    //Restore access control to default
    CMD_CLEAR_ACC(32),
    //Delete operations log
    CMD_CLEAR_OPLOG(33),
    //Read operations log
    CMD_OPLOG_RRQ(34),
    //Request machine status (remaining space)
    CMD_GET_FREE_SIZES(50),
    //Enables the ":" in screen clock
    CMD_ENABLE_CLOCK(57),
    //Set the machine to authentication state
    CMD_STARTVERIFY(60),
    //Start enroll procedure
    CMD_STARTENROLL(61),
    //Disable normal authentication of users
    CMD_CANCELCAPTURE(62),
    //Query state
    CMD_STATE_RRQ(64),
    //Prints chars to the device screen
    CMD_WRITE_LCD(66),
    //Clear screen captions
    CMD_CLEAR_LCD(67),
    //Request max size for users id
    CMD_GET_PINWIDTH(69),
    //Upload short message
    CMD_SMS_WRQ(70),
    //Download short message
    CMD_SMS_RRQ(71),
    //Delete short message
    CMD_DELETE_SMS(72),
    //Set user short message
    CMD_UDATA_WRQ(73),
    //Delete user short message
    CMD_DELETE_UDATA(74),
    //Get door state
    CMD_DOORSTATE_RRQ(75),
    //Write data to Mifare card
    CMD_WRITE_MIFARE(76),
    //Clear Mifare card
    CMD_EMPTY_MIFARE(78),
    //Change verification style of a given user
    CMD_VERIFY_WRQ(79),
    //Read verification style of a given user
    CMD_VERIFY_RRQ(80),
    //Transfer fp template from buffer
    CMD_TMP_WRITE(87),
    //Get checksum of machine's buffer
    CMD_CHECKSUM_BUFFER(119),
    //Deletes fingerprint template
    CMD_DEL_FPTMP(134),
    //Request machine time
    CMD_GET_TIME(201),
    //Set machine time
    CMD_SET_TIME(202),
    //Realtime events
    CMD_REG_EVENT(500);

    private final int code;

    private CommandCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
