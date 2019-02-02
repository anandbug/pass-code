package `in`.nishachar.mypasscode.home

import `in`.nishachar.mypasscode.shared.GlideApp
import `in`.nishachar.mypasscode.shared.User
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import kotlinx.android.synthetic.main.item_user.view.*

class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindData(user: User) {
        with(itemView) {
            title?.text = user.userName
            subTitle?.text = user.passCode.toString()
            GlideApp.with(itemView)
                .load(user.image)
                .transform(MultiTransformation<Bitmap>(CenterCrop(), CircleCrop()))
                .transition(GenericTransitionOptions.with<Drawable>(android.R.anim.fade_in))
                .into(profileImage)
        }
    }
}