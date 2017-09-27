package com.alexlesaka.carshare.models;

import com.alexlesaka.carshare.enumerations.UserRole;

/**
 * Created by aabuin on 28/08/2017.
 */

public class Member {
    String username = "";
    UserRole role =UserRole.MEMBER;
    int score;

    public Member(){}

    public Member(String username, UserRole role, int score) {
        this.username = username;
        this.role = role;
        this.score=score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
