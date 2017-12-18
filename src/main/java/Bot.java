import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private int day;
    User user;
    boolean codeMode = false;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            long chatid = update.getMessage().getChatId();
            sendText(text, chatid);
            if (text.equals("играть")) {
                playGame(chatid);
            } else if (text.equals("учить бота")) {
                teachBot();
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
    private void teachBot() {

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
