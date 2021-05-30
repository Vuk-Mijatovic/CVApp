package com.example.cvapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = Questions.class, version = 1)
public abstract class QuestionsDatabase extends RoomDatabase {

    public static QuestionsDatabase instance;

    public abstract QuestionsDao questionsDao();

    private static final int NUMBER_OF_THREADS = 1;

    public static synchronized QuestionsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), QuestionsDatabase.class, "questions_database").
                    addCallback(roomCallback).fallbackToDestructiveMigration().build();
        }
        return instance;
    }


    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            QuestionsDatabase.databaseWriteExecutor.execute(new Runnable() {

                QuestionsDao questionsDao;

                @Override
                public void run() {
                    questionsDao = instance.questionsDao();
                    if (questionsDao != null) {
                        questionsDao.insert(new Questions("Pivot! Pivot! PIVOT!", "Ross", "Rachel",
                                "Chandler", "Monica", 1));
                        questionsDao.insert(new Questions("We were on a break!", "Rachel", "Joe",
                                "Phoebe", "Ross", 4));
                        questionsDao.insert(new Questions("Oh... My... Good!", "Phoebe", "Gunther",
                                "Chandler", "Janice", 4));
                    }
                }
            });
        }
    };


}
