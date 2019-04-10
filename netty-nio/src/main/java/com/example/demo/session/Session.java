package com.example.demo.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author lsk
 * @class_name Session
 * @date 2019/2/15
 */
@Data
@NoArgsConstructor
public class Session {
    private String userId;

    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}

