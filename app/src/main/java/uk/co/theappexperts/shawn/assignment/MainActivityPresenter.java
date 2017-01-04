package uk.co.theappexperts.shawn.assignment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.theappexperts.shawn.assignment.model.Comment;
import uk.co.theappexperts.shawn.assignment.model.Post;
import uk.co.theappexperts.shawn.assignment.model.User;

/**
 * Created by TheAppExperts on 03/01/2017.
 */

public class MainActivityPresenter {

    private Context context;
    private ApiInterface api;
    private Realm realm;
    private boolean postsDone, usersDone, commentsDone;

    public MainActivityPresenter(Activity context) {
        this.context = context;
        this.api = ApiClient.getClient(context.getApplication()).create(ApiInterface.class);
        realm = Realm.getDefaultInstance();
    }
    private void loadPosts() {
        api.getPosts().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Post>>() {
                    @Override
                    public void onCompleted() {
                        postsDone = true;
                        if (postsDone && usersDone && commentsDone)
                            realm.close();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final List<Post> posts) {
                        ((MainActivity)context).setPostAdapter(posts);
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(posts);
                            }
                        });
                    }
                });

    }
    private void loadComments() {
        api.getComments().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {
                        commentsDone = true;
                        if (postsDone && usersDone && commentsDone)
                            realm.close();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final List<Comment> comments) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(comments);
                            }
                        });
                    }
                });
    }
    private void loadUsers() {
        api.getUsers().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onCompleted() {
                        usersDone = true;
                        if (postsDone && usersDone && commentsDone)
                            realm.close();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(final List<User> users) {
                        realm.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.copyToRealmOrUpdate(users);
                            }
                        });
                    }
                });
    }
    public void load() {
        loadComments();
        loadPosts();
        loadUsers();
    }
}
