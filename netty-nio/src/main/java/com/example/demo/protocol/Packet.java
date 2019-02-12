package com.example.demo.protocol;

import lombok.Data;

/**
 * TODO
 *
 * @author lsk
 * @class_name Packet
 * @date 2019/2/12
 */
@Data
public abstract class Packet {

    private Byte version = 1;

    public abstract Byte getCommand();
}
