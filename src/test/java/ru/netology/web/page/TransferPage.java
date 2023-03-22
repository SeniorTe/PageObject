package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement sumTransfer = $("[data-test-id=amount] input");
    private SelenideElement numberCardFrom = $("[data-test-id=from] input");
    private SelenideElement buttonStartTransfer = $("[data-test-id=action-transfer]");

    public void transfer(DataHelper.CardsInfo info, int sum) {
        sumTransfer.setValue(String.valueOf(sum));
        numberCardFrom.setValue(info.getNumberCard());
        buttonStartTransfer.click();
    }
}
