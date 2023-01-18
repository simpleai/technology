package com.xiaoruiit.knowledge.point.valited.factory;


import com.xiaoruiit.knowledge.point.valited.UserValid;

/**
 * @author hanxiaorui
 * @date 2022/2/17
 */
public class UserValidFactory {

    public static UserValid buildUserValid() {
        UserValid userValid = new UserValid();
        userValid.setUserId("1");
        return userValid;
    }
}
