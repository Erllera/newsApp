package com.geektech.a41.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.geektech.a41.NewsModel;

import java.util.List;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM newsmodel ORDER BY date DESC")
    List<NewsModel> getAllSorted();

    @Insert
    void insert(NewsModel model);

    @Delete
    void delete(NewsModel model);

    @Delete
    void deleteAll(List<NewsModel> model);

    @Query("SELECT * FROM newsmodel WHERE text LIKE '%' || :search || '%'")
    List<NewsModel> listSearch(String search);
}
