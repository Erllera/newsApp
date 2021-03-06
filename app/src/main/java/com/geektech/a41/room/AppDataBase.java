package com.geektech.a41.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.a41.NewsModel;

@Database(entities = {NewsModel.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ArticleDao articleDao();

}
