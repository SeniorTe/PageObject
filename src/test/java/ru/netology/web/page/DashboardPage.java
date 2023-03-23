package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection buttonsCards = $$("[data-test-id=action-deposit]");

    //  public Dashboard() {}
    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
        val text = cards.find(Condition.attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public TransferPage topUpBalance(String id) {
        int cardIndex = 0;
        if (id.equals(DataHelper.getSecondCardInfo().getCardId())) {
            cardIndex = 1;
        }
        ;
        buttonsCards.get(cardIndex).click();
        return new TransferPage();
    }
}