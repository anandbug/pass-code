package `in`.nishachar.mypasscode.home

import `in`.nishachar.mypasscode.R
import `in`.nishachar.mypasscode.shared.User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {
    private var users: MutableList<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindData(users[position])
    }

    internal fun addUser(user: User) {
        users.add(user)
        notifyItemInserted(users.size)
    }

    internal fun loadUsers(users: List<User>) {
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}