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
                                "Chandler", "Monica", "Ross"));
                        questionsDao.insert(new Questions("We were on a break!", "Rachel", "Joe",
                                "Phoebe", "Ross", "Ross"));
                        questionsDao.insert(new Questions("Oh... My... Good!", "Phoebe", "Gunther",
                                "Chandler", "Janice", "Janice"));
                        questionsDao.insert(new Questions("The fridge broke so I had to eat everything.", "Phoebe", "Joe",
                                "Chandler", "Richard", "Joe"));
                        questionsDao.insert(new Questions("I was wondering if you'd like to go to a movie with me sometime... as my lover.", "Ross", "The Copygirl",
                                "Gunther", "Joe", "Gunther"));
                        questionsDao.insert(new Questions("I've got this uncontrollable need to please people.", "Monica", "Phoebe",
                                "Ross", "Joe", "Monica"));
                        questionsDao.insert(new Questions("Pants: like shorts but longer.", "Joe", "Jack Geller",
                                "Chandler", "Phoebe", "Chandler"));
                        questionsDao.insert(new Questions("Joey doesn’t share food!", "Joe", "Chandler", "Rachel", "Monica", "Joe"));
                        questionsDao.insert(new Questions("Isn’t that just kick you in the crotch, spit on your neck fantastic?", "Chandler", "Ross",
                                "Monica", "Rachel", "Rachel"));
                        questionsDao.insert(new Questions("OK, no uterus, no opinion.", "Monica", "Rachel", "Phoebe", "Caroll", "Rachel"));
                        questionsDao.insert(new Questions("Gum would be perfection.", "Chandler", "Joe", "Phoebe", "Ross", "Chandler"));
                        questionsDao.insert(new Questions("I wish I could, but I don’t want to.", "Phoebe", "Ursula", "Ross",
                                "chandler", "Phoebe" ));
                        questionsDao.insert(new Questions("I’m hopeless and awkward and desperate for love!", "Monica", "Ross",
                                "Chandler", "Frank Jr.", "Chandler"));
                        questionsDao.insert(new Questions("See? He’s her lobster. ", "Phoebe", "Monica", "Rachel", "Janice", "Phoebe"));
                        questionsDao.insert(new Questions("We better stick to the routine; we don't want to look stupid.", "Ross", "Monica", "Rachel", "Chandler", "Monica"));



                    }

                }
            });
        }
    };


}
