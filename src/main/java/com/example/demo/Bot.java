
package com.example.demo;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.*;


@EnableScheduling
@SpringBootApplication
class bot extends TelegramLongPollingBot{

	//Reminder reminder = new Reminder();


	public static void main(String[] args) throws TelegramApiException {
		bot Bot = new bot();
		TelegramBotsApi telegrambotapi = new TelegramBotsApi(DefaultBotSession.class);

		telegrambotapi.registerBot(Bot);
		//Timer timer = new Timer();
		//timer.schedule(new Reminder(),);
	}
	Reminder tmp = new Reminder();
	@Override
	public void onUpdateReceived(Update update) {

		//System.out.println(LocalDateTime.now());
		Message message = update.getMessage();
		if (update.hasMessage() && update.getMessage().hasText()) {
			String answer = update.getMessage().getText();
			if(answer.contains("/r"))
			{
				Reminder reminder = new Reminder();
				//Reminder reminder
				System.out.println(answer);
				//LocalDateTime d = LocalDateTime.parse(answer.replace("/r ", ""));
				DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					reminder.task.date = dateFormatter .parse(answer.replace("/r ", ""));
					tmp =reminder;
					//reminder.reminder_task(message.getChatId(),date,answer);
				} catch (ParseException e) {
					throw new RuntimeException(e);
				}
			}
			else if (answer.contains("/text")){
				Timer timer = new Timer();
				Reminder reminder = new Reminder();
				reminder = tmp;
				//	System.out.println(date);
				//Use this if you want to execute it once
				reminder.task.ChatId = update.getMessage().getChatId();
				reminder.task.message = answer.replace("/text","");
				timer.schedule((reminder), reminder.task.date);
			}
			else {
				try {
					//SendMessage.builder().chatId(message.getChatId().toString()).text(answer).build();
					execute(SendMessage.builder().chatId(message.getChatId().toString()).text(answer).build());

				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public String getBotUsername() {
		return "My13971_bot";
	}

	public String getBotToken() {
		return "5449751657:AAEOgTyf1fd6MSHCF5Ak3PDdOQuVowSS7-A";
	}


	public class Reminder extends TimerTask {

		reminder_task task = new reminder_task();
		private static class reminder_task  {
			Long ChatId;
			Date date;
			String message;
		}

		 void send_reminder (reminder_task task) {
			try {

				//SendMessage.builder().chatId(task.ChatId.toString()).text(task.message).build();
				execute(SendMessage.builder().chatId(task.ChatId.toString()).text(task.message).build());
			//	reminder_list.remove(task);

			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
		public void  run()
		{
			reminder_task Task = task;
		//	reminder_list.stream().filter(x -> x.date.isAfter(LocalDateTime.now())).forEach(x ->send_reminder(x));
			send_reminder (Task);
		}
	}



	//The task which you want to execute




}






