package com.church.warsaw.help.refugees.foodsets.email;

import static java.lang.String.format;

import com.church.warsaw.help.refugees.foodsets.config.CompanyConfiguration;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {

  private final CompanyConfiguration configuration;

  private final MimeMessage mimeMessage;

  @SneakyThrows
  public void sendMail(String mail, String date, String time) {
    MimeMessage messagePreparator = getMessagePreparator(mail, date, time);
    try {
      Transport.send(messagePreparator);
    } catch (MailException ex) {
      log.error("Something went wrong, message={}", ex.getMessage());
    }

  }

  @SneakyThrows
  private MimeMessage getMessagePreparator(String mail, String date, String time) {

    mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
    mimeMessage.setSubject("Допомога iCareUkraine");

    String textMessage = format("Вас вітає цент допомоги біженцям %s! \n"
            + "Дякуємо за Вашу довіру, чекаємо Вас на зустрічі за адерсою: %s, перший під'їзд, \n"
            + "другий поверх, на обрану вами дату та час (%s %s) \n"
            + "Хай бог благословить Вас!\n"
            + "Запрошуємо Вас відвідати наші інформаційні ресурси: \n"
            + "https://icareukraine.pl/\n"
            + "https://www.baptist.pl/\n"
            + "https://www.facebook.com/baptistpl\n",
        configuration.getName(), configuration.getAddress(), date, time);

    mimeMessage.setText(textMessage);

    return mimeMessage;
  }
}
