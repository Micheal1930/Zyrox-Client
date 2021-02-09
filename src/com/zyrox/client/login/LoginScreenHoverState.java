package com.zyrox.client.login;

/**
 * Created by Jonny on 8/19/2019
 **/
public enum LoginScreenHoverState {

    DEFAULT(false),
    FORUMS_QUICK_LINK(true),
    VOTE_QUICK_LINK(true),
    STORE_QUICK_LINK(true),
    YOUTUBE_QUICK_LINK(true),
    DISCORD_QUICK_LINK(true),
    ACCOUNTS_1(true),
    ACCOUNTS_2(true),
    ACCOUNTS_3(true),

    ACCOUNTS_1_DELETE(true),
    ACCOUNTS_2_DELETE(true),
    ACCOUNTS_3_DELETE(true),

    ;

    public boolean handCursor;

    LoginScreenHoverState(boolean handCursor) {
        this.handCursor = handCursor;
    }


}
