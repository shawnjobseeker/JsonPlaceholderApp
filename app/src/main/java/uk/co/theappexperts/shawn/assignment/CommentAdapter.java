package uk.co.theappexperts.shawn.assignment;

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
import uk.co.theappexperts.shawn.assignment.model.Comment;
import uk.co.theappexperts.shawn.assignment.model.Post;
import uk.co.theappexperts.shawn.assignment.model.User;

import static uk.co.theappexperts.shawn.assignment.Constants.AVATAR_URL;

/**
 * Created by TheAppExperts on 04/01/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {

    public CommentAdapter(Context context, List<Comment> comments) {
        super(context, 0, comments);
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
        Comment comment = getItem(position);
        holder.user.setText(comment.getName());
        holder.body.setText(comment.getBody());
        Picasso.with(getContext()).load(AVATAR_URL + comment.getEmail() + ".png").into(holder.avatar);
        return convertView;
    }
}
