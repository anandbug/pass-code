package `in`.nishachar.mypasscode.shared

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class UserDao {
    /**
     * Insert ETags into table.
     * @param user [User] to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUser(user: User)

    /**
     * Get the users from the table.This query gets users from the table.
     *
     * @return list of users from the table
     */
    @Query("SELECT * FROM users")
    abstract fun fetchUsers(): List<User>
}