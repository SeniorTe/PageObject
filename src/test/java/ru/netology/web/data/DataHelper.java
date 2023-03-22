package ru.netology.web.data;

import lombok.Value;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }


    @Value
    public static class CardsInfo {
        private String CardId;
        private String NumberCard;
    }

    public static CardsInfo getFirstCardInfo() {
        return new CardsInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559_0000_0000_0001");
    }

    public static CardsInfo getSecondCardInfo() {
        return new CardsInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559_0000_0000_0002");
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    public static int newBalanceCardPlus(int balance, int sum) {
        int balanceCardPlus = balance + sum;
        return balanceCardPlus;
    }

    public static int newBalanceCardMinus(int balance, int sum) {
        int balanceCardMinus = balance - sum;
        return balanceCardMinus;
    }
}

