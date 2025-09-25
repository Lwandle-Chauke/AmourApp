package com.datingapp.amour.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile UserDao _userDao;

  private volatile UserProfileDao _userProfileDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`uid` TEXT NOT NULL, `email` TEXT NOT NULL, `name` TEXT NOT NULL, `age` INTEGER, `gender` TEXT, `orientation` TEXT, `location` TEXT, `profileImageUrl` TEXT, `imageUrls` TEXT, `createdAt` INTEGER NOT NULL, `lastLogin` INTEGER NOT NULL, `isProfileComplete` INTEGER NOT NULL, `isEmailVerified` INTEGER NOT NULL, PRIMARY KEY(`uid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS `user_profiles` (`uid` TEXT NOT NULL, `email` TEXT NOT NULL, `bio` TEXT NOT NULL, `prompt1` TEXT NOT NULL, `prompt2` TEXT NOT NULL, `prompt3` TEXT NOT NULL, `interests` TEXT NOT NULL, `agePreference` TEXT NOT NULL, `distancePreference` TEXT NOT NULL, `lookingFor` TEXT NOT NULL, PRIMARY KEY(`uid`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '5abd59afb42fde8527102bf91e9e41a2')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `users`");
        db.execSQL("DROP TABLE IF EXISTS `user_profiles`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(13);
        _columnsUsers.put("uid", new TableInfo.Column("uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("age", new TableInfo.Column("age", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("gender", new TableInfo.Column("gender", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("orientation", new TableInfo.Column("orientation", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("location", new TableInfo.Column("location", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("profileImageUrl", new TableInfo.Column("profileImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("imageUrls", new TableInfo.Column("imageUrls", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("lastLogin", new TableInfo.Column("lastLogin", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isProfileComplete", new TableInfo.Column("isProfileComplete", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsers.put("isEmailVerified", new TableInfo.Column("isEmailVerified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(db, "users");
        if (!_infoUsers.equals(_existingUsers)) {
          return new RoomOpenHelper.ValidationResult(false, "users(com.datingapp.amour.data.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsUserProfiles = new HashMap<String, TableInfo.Column>(10);
        _columnsUserProfiles.put("uid", new TableInfo.Column("uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("bio", new TableInfo.Column("bio", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("prompt1", new TableInfo.Column("prompt1", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("prompt2", new TableInfo.Column("prompt2", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("prompt3", new TableInfo.Column("prompt3", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("interests", new TableInfo.Column("interests", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("agePreference", new TableInfo.Column("agePreference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("distancePreference", new TableInfo.Column("distancePreference", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserProfiles.put("lookingFor", new TableInfo.Column("lookingFor", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserProfiles = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserProfiles = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserProfiles = new TableInfo("user_profiles", _columnsUserProfiles, _foreignKeysUserProfiles, _indicesUserProfiles);
        final TableInfo _existingUserProfiles = TableInfo.read(db, "user_profiles");
        if (!_infoUserProfiles.equals(_existingUserProfiles)) {
          return new RoomOpenHelper.ValidationResult(false, "user_profiles(com.datingapp.amour.data.UserProfile).\n"
                  + " Expected:\n" + _infoUserProfiles + "\n"
                  + " Found:\n" + _existingUserProfiles);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "5abd59afb42fde8527102bf91e9e41a2", "41c5ddcd7f5eca7efb49718f81a194b2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "users","user_profiles");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `users`");
      _db.execSQL("DELETE FROM `user_profiles`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserProfileDao.class, UserProfileDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public UserProfileDao userProfileDao() {
    if (_userProfileDao != null) {
      return _userProfileDao;
    } else {
      synchronized(this) {
        if(_userProfileDao == null) {
          _userProfileDao = new UserProfileDao_Impl(this);
        }
        return _userProfileDao;
      }
    }
  }
}
