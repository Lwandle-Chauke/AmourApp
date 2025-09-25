package com.datingapp.amour.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
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
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<User> __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter<User> __updateAdapterOfUser;

  private final SharedSQLiteStatement __preparedStmtOfUpdateLastLogin;

  private final SharedSQLiteStatement __preparedStmtOfDeleteUser;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`uid`,`email`,`name`,`age`,`gender`,`orientation`,`location`,`profileImageUrl`,`imageUrls`,`createdAt`,`lastLogin`,`isProfileComplete`,`isEmailVerified`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        if (entity.getUid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUid());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEmail());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getAge() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getAge());
        }
        if (entity.getGender() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getGender());
        }
        if (entity.getOrientation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOrientation());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getProfileImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getProfileImageUrl());
        }
        if (entity.getImageUrls() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrls());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getLastLogin());
        final int _tmp = entity.isProfileComplete() ? 1 : 0;
        statement.bindLong(12, _tmp);
        final int _tmp_1 = entity.isEmailVerified() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `uid` = ?,`email` = ?,`name` = ?,`age` = ?,`gender` = ?,`orientation` = ?,`location` = ?,`profileImageUrl` = ?,`imageUrls` = ?,`createdAt` = ?,`lastLogin` = ?,`isProfileComplete` = ?,`isEmailVerified` = ? WHERE `uid` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final User entity) {
        if (entity.getUid() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUid());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEmail());
        }
        if (entity.getName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getName());
        }
        if (entity.getAge() == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, entity.getAge());
        }
        if (entity.getGender() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getGender());
        }
        if (entity.getOrientation() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOrientation());
        }
        if (entity.getLocation() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getLocation());
        }
        if (entity.getProfileImageUrl() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getProfileImageUrl());
        }
        if (entity.getImageUrls() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getImageUrls());
        }
        statement.bindLong(10, entity.getCreatedAt());
        statement.bindLong(11, entity.getLastLogin());
        final int _tmp = entity.isProfileComplete() ? 1 : 0;
        statement.bindLong(12, _tmp);
        final int _tmp_1 = entity.isEmailVerified() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        if (entity.getUid() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getUid());
        }
      }
    };
    this.__preparedStmtOfUpdateLastLogin = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE users SET lastLogin = ? WHERE uid = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteUser = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM users WHERE uid = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUser.insert(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateUser(final User user, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfUser.handle(user);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateLastLogin(final String uid, final long timestamp,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateLastLogin.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, timestamp);
        _argIndex = 2;
        if (uid == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, uid);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateLastLogin.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteUser(final String uid, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteUser.acquire();
        int _argIndex = 1;
        if (uid == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, uid);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteUser.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getUserByUid(final String uid, final Continuation<? super User> $completion) {
    final String _sql = "SELECT * FROM users WHERE uid = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (uid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, uid);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<User>() {
      @Override
      @Nullable
      public User call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "uid");
          final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfAge = CursorUtil.getColumnIndexOrThrow(_cursor, "age");
          final int _cursorIndexOfGender = CursorUtil.getColumnIndexOrThrow(_cursor, "gender");
          final int _cursorIndexOfOrientation = CursorUtil.getColumnIndexOrThrow(_cursor, "orientation");
          final int _cursorIndexOfLocation = CursorUtil.getColumnIndexOrThrow(_cursor, "location");
          final int _cursorIndexOfProfileImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "profileImageUrl");
          final int _cursorIndexOfImageUrls = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrls");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfLastLogin = CursorUtil.getColumnIndexOrThrow(_cursor, "lastLogin");
          final int _cursorIndexOfIsProfileComplete = CursorUtil.getColumnIndexOrThrow(_cursor, "isProfileComplete");
          final int _cursorIndexOfIsEmailVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "isEmailVerified");
          final User _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUid;
            if (_cursor.isNull(_cursorIndexOfUid)) {
              _tmpUid = null;
            } else {
              _tmpUid = _cursor.getString(_cursorIndexOfUid);
            }
            final String _tmpEmail;
            if (_cursor.isNull(_cursorIndexOfEmail)) {
              _tmpEmail = null;
            } else {
              _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
            }
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final Integer _tmpAge;
            if (_cursor.isNull(_cursorIndexOfAge)) {
              _tmpAge = null;
            } else {
              _tmpAge = _cursor.getInt(_cursorIndexOfAge);
            }
            final String _tmpGender;
            if (_cursor.isNull(_cursorIndexOfGender)) {
              _tmpGender = null;
            } else {
              _tmpGender = _cursor.getString(_cursorIndexOfGender);
            }
            final String _tmpOrientation;
            if (_cursor.isNull(_cursorIndexOfOrientation)) {
              _tmpOrientation = null;
            } else {
              _tmpOrientation = _cursor.getString(_cursorIndexOfOrientation);
            }
            final String _tmpLocation;
            if (_cursor.isNull(_cursorIndexOfLocation)) {
              _tmpLocation = null;
            } else {
              _tmpLocation = _cursor.getString(_cursorIndexOfLocation);
            }
            final String _tmpProfileImageUrl;
            if (_cursor.isNull(_cursorIndexOfProfileImageUrl)) {
              _tmpProfileImageUrl = null;
            } else {
              _tmpProfileImageUrl = _cursor.getString(_cursorIndexOfProfileImageUrl);
            }
            final String _tmpImageUrls;
            if (_cursor.isNull(_cursorIndexOfImageUrls)) {
              _tmpImageUrls = null;
            } else {
              _tmpImageUrls = _cursor.getString(_cursorIndexOfImageUrls);
            }
            final long _tmpCreatedAt;
            _tmpCreatedAt = _cursor.getLong(_cursorIndexOfCreatedAt);
            final long _tmpLastLogin;
            _tmpLastLogin = _cursor.getLong(_cursorIndexOfLastLogin);
            final boolean _tmpIsProfileComplete;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsProfileComplete);
            _tmpIsProfileComplete = _tmp != 0;
            final boolean _tmpIsEmailVerified;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsEmailVerified);
            _tmpIsEmailVerified = _tmp_1 != 0;
            _result = new User(_tmpUid,_tmpEmail,_tmpName,_tmpAge,_tmpGender,_tmpOrientation,_tmpLocation,_tmpProfileImageUrl,_tmpImageUrls,_tmpCreatedAt,_tmpLastLogin,_tmpIsProfileComplete,_tmpIsEmailVerified);
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
