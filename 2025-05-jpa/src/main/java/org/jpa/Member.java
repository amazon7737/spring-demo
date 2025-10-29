package org.jpa;

public class Member {

    private int number;
    private String nickname;

    public Member(int number, String nickname) {
        this.number = number;
        this.nickname = nickname;
    }

    public static Member of(int number, String nickname) {
        return new Member(number, nickname);
    }
}
