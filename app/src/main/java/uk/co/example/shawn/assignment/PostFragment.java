package uk.co.example.shawn.assignment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import uk.co.example.shawn.assignment.model.Post;
import uk.co.example.shawn.assignment.model.User;

import static uk.co.example.shawn.assignment.Constants.AVATAR_URL;

/**
 * Created by Shawn Li on 03/01/2017.
 */

public class PostFragment extends Fragment {

    @BindView(R.id.title_text)
    TextView title;
    @BindView(R.id.body_text)
    TextView body;
    @BindView(R.id.comment_text)
    TextView comment;
    @BindView(R.id.user_by)
    TextView userBy;
    @BindView(R.id.comments)
    ListView comments;
    @BindView(R.id.avatar)
    ImageView avatar;

    private int postId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, v);
        final View button = v.findViewById(R.id.show_button);
        if (button != null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    comments.setVisibility(View.VISIBLE);
                    button.setVisibility(View.GONE);
                }
            });
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null)
            postId = savedInstanceState.getInt("postId");
        if (postId <= 0)
            postId = getArguments().getInt("postId");
        Realm realm = Realm.getDefaultInstance();
        Post post = realm.where(Post.class).equalTo("id", postId).findFirst();
        User user = realm.where(User.class).equalTo("id", post.getUserId()).findFirst();
        title.setText(post.getTitle());
        body.setText(post.getBody());
        Picasso.with(getContext()).load(AVATAR_URL + user.getEmail() + ".png").into(avatar);
        userBy.setText(getContext().getString(R.string.user_by, user.getUsername()));
        realm.close();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new PostPresenter(this).getComments(postId);
    }

    public void setCommentAdapter(CommentAdapter adapter) {
        comments.setAdapter(adapter);
        comment.setText(getContext().getString(R.string.comments, adapter.getCount()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("postId", postId);
    }
}
