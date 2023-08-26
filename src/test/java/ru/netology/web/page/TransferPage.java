package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private  final SelenideElement transferHead =$ (byText("Пополнение карты"));
    private  final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final  SelenideElement amountInputNew = $("[data-test-id='amount'] input");
    private final  SelenideElement fromInput = $("[data-test-id='from'] input");

    private final  SelenideElement errorMessage = $("[data-test-id= 'error-notification']");




    public TransferPage () {
        transferHead.shouldBe(visible);
    }

    public void MakeTransfer (String amountToTransfer, DataHelper.CardInfo cardInfo){
        amountInputNew.setValue(amountToTransfer);
        fromInput.setValue(cardInfo.getCardNumber());
        transferButton.click();
    }

    public DashboardPage MakeValidTransfer (String amountToTransfer, DataHelper.CardInfo cardInfo){
        MakeTransfer(amountToTransfer, cardInfo);
        return new DashboardPage();
    }

    public void FindErrorMessage (String expectedText, Condition condition){
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}


