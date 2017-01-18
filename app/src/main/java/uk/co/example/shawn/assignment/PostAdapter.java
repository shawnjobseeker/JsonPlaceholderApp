package uk.co.example.shawn.assignment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import uk.co.example.shawn.assignment.model.Post;
import uk.co.example.shawn.assignment.model.User;

import static uk.co.example.shawn.assignment.Constants.AVATAR_URL;

/**
 * Created by Shawn Li on 04/01/2017.
 */

public class PostAdapter extends ArrayAdapter<Post> {


    public PostAdapter(Context context, List<Post> posts) {
        super(context, 0, posts);
    }
    class ViewHolder {
        @BindView(R.id.user_text)
        TextView user;
        @BindView(R.id.body_text)
        TextView body;
        @BindView(R.id.avatar)
        ImageView avatar;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder)convertView.getTag();
        Realm realm = Realm.getDefaultInstance();
        Post post = getItem(position);
        User user = realm.where(User.class).equalTo("id", post.getUserId()).findFirst();
        holder.user.setText(user.getUsername());
        holder.body.setText(post.getTitle());
        Picasso.with(getContext()).load(AVATAR_URL + user.getEmail() + ".png").into(holder.avatar);
        realm.close();
        return convertView;
    }
}
