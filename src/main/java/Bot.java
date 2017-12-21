import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class Bot extends TelegramLongPollingBot {

    private int day;
    User user;
    Food pizza;
    boolean codeMOde = false;
    boolean eatMode = false;
    //Modes mode = Modes.MAIN_MODE;


    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatid = update.getMessage().getChatId();
            sendText(text, chatid);
            if (text.equals("играть")) {
                playGame(chatid);
            } else if (text.equals("учить бота")) {

            } else if (text.equals("кушать")) {
                eatMode = true;
                eat(text, chatid);
            }
        } else if (update.getMessage().hasPhoto()) {
            String photo = update.getMessage().getPhoto().get(0).getFileId();
            long chatid = update.getMessage().getChatId();
            sendPhoto(photo, chatid);
        }
    }
    private void sendText(String text, long chatid) {
        SendMessage request =  new SendMessage();
        request.setText(text);
        request.setChatId(chatid);
        try {
            execute(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void sendPhoto(String photo, long chatid) {
        SendPhoto request =  new SendPhoto();
        request.setPhoto(photo);
        request.setChatId(chatid);
        try {
            sendPhoto(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    private void playGame(long chatid) {
        day = 0;
        user = new User();
        nextDay(chatid);
    }
    private void nextDay(long chatid) {
        day++;
        sendText("день номер" + day, chatid);
        sendText(user.getInfo(), chatid);
    }
    private void eat(String text, long chatid) {
        sendText("что хотите съесть?", chatid);
        if (text.equals("пицца")) {
            pizza = new Pizza();
            sendText("Сколько кусков хотите", chatid);
            int number = Integer.parseInt(text);
            sendText("Вы съели" + pizza.getPrice(number), chatid);
        }
    }
    private void Keyboard(String text, long chatid /*String... buttonsName*/) {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new KeyboardButton("kzkzkzkz"));
        firstRow.add(new KeyboardButton("blblblblbl"));
        keyboard.add(firstRow);
        markup.setKeyboard(keyboard);

        SendMessage request =  new SendMessage();
        request.setText(text);
        request.setChatId(chatid);
        request.setReplyMarkup(markup);

        try {
            execute(request);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getBotUsername() {
        return "SobolRosBot";
        //возвращаем юзера
    }

    @Override
    public String getBotToken() {
        return "382237573:AAHJi3TAiv9ikPcmZh9qb2EddRuND2WKuxM";
        //Токен бота
    }
}
