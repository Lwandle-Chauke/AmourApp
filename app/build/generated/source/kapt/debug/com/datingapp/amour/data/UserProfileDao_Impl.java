package com.datingapp.amour.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UserProfileDao_Impl implements UserProfileDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserProfile> __insertionAdapterOfUserProfile;

  public UserProfileDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserProfile = new EntityInsertionAdapter<UserProfile>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `user_profiles` (`email`,`bio`,`gender`,`prompt1`,`prompt2`,`prompt3`,`interests`,`agePreference`,`distancePreference`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserProfile entity) {
        if (entity.getEmail() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getEmail());
        }
        if (entity.getBio() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getBio());
        }
        if (entity.getGender() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getGender());
        }
        if (entity.getPrompt1() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPrompt1());
        }
        if (entity.getPrompt2() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPrompt2());
        }
        if (entity.getPrompt3() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getPrompt3());
        }
        if (entity.getInterests() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getInterests());
        }
        if (entity.getAgePreference() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getAgePreference());
        }
        if (entity.getDistancePreference() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getDistancePreference());
        }
      }
    };
  }

  @Override
  public Object insert(final UserProfile profile, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUserProfile.insert(profile);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getProfile(final String email,
      final Continuation<? super UserProfile> $completion) {
    final String _sql = "SELECT * FROM user_profiles WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UserProfile>() {
      @Override
      @Nullable
      public UserProfile call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfBio = CursorUtil.getColumnIndexOrThrow(_cursor, "bio");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfPrompt1 = CursorUtil.getColumnIndexOrThrow(_cursor, "prompt1");
          final int _cursorIndexOfPrompt2 = CursorUtil.getColumnIndexOrThrow(_cursor, "prompt2");
          final int _cursorIndexOfPrompt3 = CursorUtil.getColumnIndexOrThrow(_cursor, "prompt3");
          final int _cursorIndexOfInterests = CursorUtil.getColumnIndexOrThrow(_cursor, "interests");
          final int _cursorIndexOfAgePreference = CursorUtil.getColumnIndexOrThrow(_cursor, "agePreference");
          final int _cursorIndexOfDistancePreference = CursorUtil.getColumnIndexOrThrow(_cursor, "distancePreference");
          final UserProfile _result;
          if (_cursor.moveToFirst()) {
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpBio;
            if (_cursor.isNull(_cursorIndexOfBio)) {
              _tmpBio = null;
            } else {
              _tmpBio = _cursor.getString(_cursorIndexOfBio);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpPrompt1;
            if (_cursor.isNull(_cursorIndexOfPrompt1)) {
              _tmpPrompt1 = null;
            } else {
              _tmpPrompt1 = _cursor.getString(_cursorIndexOfPrompt1);
            }
            final String _tmpPrompt2;
            if (_cursor.isNull(_cursorIndexOfPrompt2)) {
              _tmpPrompt2 = null;
            } else {
              _tmpPrompt2 = _cursor.getString(_cursorIndexOfPrompt2);
            }
            final String _tmpPrompt3;
            if (_cursor.isNull(_cursorIndexOfPrompt3)) {
              _tmpPrompt3 = null;
            } else {
              _tmpPrompt3 = _cursor.getString(_cursorIndexOfPrompt3);
            }
            final String _tmpInterests;
            if (_cursor.isNull(_cursorIndexOfInterests)) {
              _tmpInterests = null;
            } else {
              _tmpInterests = _cursor.getString(_cursorIndexOfInterests);
            }
            final String _tmpAgePreference;
            if (_cursor.isNull(_cursorIndexOfAgePreference)) {
              _tmpAgePreference = null;
            } else {
              _tmpAgePreference = _cursor.getString(_cursorIndexOfAgePreference);
            }
            final String _tmpDistancePreference;
            if (_cursor.isNull(_cursorIndexOfDistancePreference)) {
              _tmpDistancePreference = null;
            } else {
              _tmpDistancePreference = _cursor.getString(_cursorIndexOfDistancePreference);
            }
            _result = new UserProfile(_tmpEmail,_tmpBio,_tmpGender,_tmpPrompt1,_tmpPrompt2,_tmpPrompt3,_tmpInterests,_tmpAgePreference,_tmpDistancePreference);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
