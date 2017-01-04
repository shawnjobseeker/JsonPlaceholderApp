package uk.co.theappexperts.shawn.assignment;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.theappexperts.shawn.assignment.model.Comment;

/**
 * Created by TheAppExperts on 03/01/2017.
 */

public class PostPresenter {

    private PostFragment fragment;
    private Realm realm;

    public PostPresenter(PostFragment fragment) {
        this.fragment = fragment;
        this.realm = Realm.getDefaultInstance();

    }

    public void getComments(int postId) {
        realm.where(Comment.class).equalTo("postId", postId).findAllAsync().asObservable().subscribe(new Observer<RealmResults<Comment>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(RealmResults<Comment> comments) {
                fragment.setCommentAdapter(new CommentAdapter(fragment.getContext(), comments));
            }
        });
    }
}
