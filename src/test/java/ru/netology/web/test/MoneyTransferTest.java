package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV2;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @BeforeEach
    void logIn() {
        Configuration.holdBrowserOpen = true;
        var loginPage = open("http://localhost:9999", LoginPageV2.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstByNormalSum() {

        var dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpFirstCard();
        var transferFromSecondCardToFirst = DataHelper.getSecondCardInfo();
        int sum = 2_000;
        transferPage.transfer(transferFromSecondCardToFirst, sum);
        var balanceFirstCard = DataHelper.newBalanceCardPlus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardMinus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromSecondCardToFirstByOverSum() {

        var dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpFirstCard();
        var transferFromSecondCardToFirst = DataHelper.getSecondCardInfo();
        int sum = 12_000;
        transferPage.transfer(transferFromSecondCardToFirst, sum);
        var balanceFirstCard = DataHelper.newBalanceCardPlus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardMinus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByOverSum() {

        var dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpSecondCard();
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 12_000;
        transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByNormalSum() {

        var dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpSecondCard();
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 3_650;
        transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }

    @Test
    void shouldTransferMoneyFromFirstCardToSecondByZero() {

        var dashboardPage = new DashboardPage();
        int beforeBalanceFirstCard = dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId());
        int beforeBalanceSecondCard = dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId());

        var transferPage = dashboardPage.topUpSecondCard();
        var transferFromFirstCardToSecond = DataHelper.getFirstCardInfo();
        int sum = 0;
        transferPage.transfer(transferFromFirstCardToSecond, sum);
        var balanceFirstCard = DataHelper.newBalanceCardMinus(beforeBalanceFirstCard, sum);
        var balanceSecondCard = DataHelper.newBalanceCardPlus(beforeBalanceSecondCard, sum);

        assertEquals(balanceFirstCard, dashboardPage.getCardBalance(DataHelper.getFirstCardInfo().getCardId()));
        assertEquals(balanceSecondCard, dashboardPage.getCardBalance(DataHelper.getSecondCardInfo().getCardId()));
    }
}