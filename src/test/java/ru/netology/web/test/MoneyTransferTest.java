package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.web.data.DataHelper.*;

public class MoneyTransferTest {

  LoginPage loginPage;
  DashboardPage dashboardPage;

  @BeforeAll
  static void setUpAll() {
      System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");}


  @BeforeEach
  void setup() {
    loginPage = open("http://localhost:9999", LoginPage.class);
    var authInfo = getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCode();
    dashboardPage = verificationPage.validVerify(verificationCode);
  }

  @Test
   void shouldTransferFromFirstToSecond() {
    var firstCardInfo = getFirstCardInfo();
    var secondCardInfo = getSecondCardInfo();
    var firstCardBalance = dashboardPage.GetCardBalance(0);
    var secondCardBalance = dashboardPage.GetCardBalance(1);
    var amount = generateValidAmount(firstCardBalance, 10);
    var expectedBalanceFirstCard = firstCardBalance - amount;
    var expectedBalanceSecondCard = secondCardBalance + amount;
    var transferPage = dashboardPage.SelectCardToTransfer(1);
    dashboardPage = transferPage.MakeValidTransfer(String.valueOf(amount), firstCardInfo);
    var actualBalanceFirstCard = dashboardPage.GetCardBalance(0);
    var actualBalanceSecondCard = dashboardPage.GetCardBalance(1);
    Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
    Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }




    @Test
    void shouldPushErrorMessageIfTransferMoreThanHave() {
        var firstCardInfo = getFirstCardInfo();
        var secondCardInfo = getSecondCardInfo();
        var firstCardBalance = dashboardPage.GetCardBalance(0);
        var secondCardBalance = dashboardPage.GetCardBalance(1);
        var amount = generateInvalidAmount(0);
        var expectedBalanceFirstCard = firstCardBalance - amount;
        var expectedBalanceSecondCard = secondCardBalance + amount;
        var transferPage = dashboardPage.SelectCardToTransfer(1);
        dashboardPage = transferPage.MakeValidTransfer(String.valueOf(amount), firstCardInfo);
        transferPage.FindErrorMessage ("Не достаточно средств на карте списания.", Condition.visible);
        var actualBalanceFirstCard = dashboardPage.GetCardBalance(0);
        var actualBalanceSecondCard = dashboardPage.GetCardBalance(1);
        Assertions.assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
        Assertions.assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
    }
}








