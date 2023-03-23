package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    private DashboardPage dashboardPage;

    @BeforeEach
    void logIn() {
        Configuration.holdBrowserOpen = true;
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstByNormalSum() {

        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpBalance(DataHelper.getFirstCardInfo().getCardId());
        var transferFromSecondCardToFirst = DataHelper.getSecondCardInfo();
        int sum = 2_000;
        dashboardPage = transferPage.transfer(transferFromSecondCardToFirst, sum);
        var balanceFirstCard = DataHelper.newBalanceCardPlus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardMinus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstByOverSum() {

        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpBalance(DataHelper.getFirstCardInfo().getCardId());
        var transferFromSecondCardToFirst = DataHelper.getSecondCardInfo();
        int sum = 12_000;
        dashboardPage = transferPage.transfer(transferFromSecondCardToFirst, sum);
        var balanceFirstCard = DataHelper.newBalanceCardPlus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardMinus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByOverSum() {

        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpBalance(DataHelper.getSecondCardInfo().getCardId());
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 12_000;
        dashboardPage = transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByNormalSum() {

        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpBalance(DataHelper.getSecondCardInfo().getCardId());
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 3_650;
        dashboardPage = transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByZero() {

        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpBalance(DataHelper.getSecondCardInfo().getCardId());
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 0;
        dashboardPage = transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }
}