package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class DashboardPage {

    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private final SelenideElement heading = $("[data-test-id='dashboard']");
    private final ElementsCollection cards = $$ (".list__item div");


    public DashboardPage() {
        heading.shouldBe(visible);
    }


//    public int GetCardBalance(DataHelper.CardInfo cardInfo) {
//        var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15).getText()));
//        return extractBalance (text);
//
//    }

    public int GetCardBalance (int index){
        var text = cards.get(index).getText();
        return extractBalance (text);
    }

    public TransferPage SelectCardToTransfer (int index){
        cards.get(index).$("button").click();
        return new TransferPage ();
    }


    private int extractBalance (String text){
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start+balanceStart.length(),finish);
        return Integer.parseInt(value);
    }

}