package com.example.smartchef.database;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDao_Impl implements FavoriteDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteRecipe> __insertionAdapterOfFavoriteRecipe;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFavorite;

  public FavoriteDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteRecipe = new EntityInsertionAdapter<FavoriteRecipe>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_recipes` (`id`,`title`,`imageUrl`,`rating`,`cookingTimeMinutes`,`difficulty`,`cuisine`,`addedTimestamp`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final FavoriteRecipe entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getImageUrl() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getImageUrl());
        }
        statement.bindDouble(4, entity.getRating());
        statement.bindLong(5, entity.getCookingTimeMinutes());
        if (entity.getDifficulty() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDifficulty());
        }
        if (entity.getCuisine() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCuisine());
        }
        statement.bindLong(8, entity.getAddedTimestamp());
      }
    };
    this.__preparedStmtOfDeleteFavorite = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorite_recipes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertFavorite(final FavoriteRecipe recipe) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFavoriteRecipe.insert(recipe);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFavorite(final String recipeId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFavorite.acquire();
    int _argIndex = 1;
    if (recipeId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, recipeId);
    }
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteFavorite.release(_stmt);
    }
  }

  @Override
  public List<FavoriteRecipe> getAllFavorites() {
    final String _sql = "SELECT * FROM favorite_recipes ORDER BY addedTimestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfCookingTimeMinutes = CursorUtil.getColumnIndexOrThrow(_cursor, "cookingTimeMinutes");
      final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
      final int _cursorIndexOfCuisine = CursorUtil.getColumnIndexOrThrow(_cursor, "cuisine");
      final int _cursorIndexOfAddedTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTimestamp");
      final List<FavoriteRecipe> _result = new ArrayList<FavoriteRecipe>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final FavoriteRecipe _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpImageUrl;
        if (_cursor.isNull(_cursorIndexOfImageUrl)) {
          _tmpImageUrl = null;
        } else {
          _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        }
        final double _tmpRating;
        _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
        final int _tmpCookingTimeMinutes;
        _tmpCookingTimeMinutes = _cursor.getInt(_cursorIndexOfCookingTimeMinutes);
        final String _tmpDifficulty;
        if (_cursor.isNull(_cursorIndexOfDifficulty)) {
          _tmpDifficulty = null;
        } else {
          _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
        }
        final String _tmpCuisine;
        if (_cursor.isNull(_cursorIndexOfCuisine)) {
          _tmpCuisine = null;
        } else {
          _tmpCuisine = _cursor.getString(_cursorIndexOfCuisine);
        }
        final long _tmpAddedTimestamp;
        _tmpAddedTimestamp = _cursor.getLong(_cursorIndexOfAddedTimestamp);
        _item = new FavoriteRecipe(_tmpId,_tmpTitle,_tmpImageUrl,_tmpRating,_tmpCookingTimeMinutes,_tmpDifficulty,_tmpCuisine,_tmpAddedTimestamp);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isFavorite(final String recipeId) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorite_recipes WHERE id = ? LIMIT 1)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (recipeId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, recipeId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if (_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
