package uk.co.theappexperts.shawn.assignment;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;
import uk.co.theappexperts.shawn.assignment.model.Comment;
import uk.co.theappexperts.shawn.assignment.model.Post;
import uk.co.theappexperts.shawn.assignment.model.User;

/**
 * Created by TheAppExperts on 04/01/2017.
 */


public interface ApiInterface {

    @GET("posts")
    Observable<List<Post>> getPosts();

    @GET("users")
    Observable<List<User>> getUsers();

    @GET("comments")
    Observable<List<Comment>> getComments();
}
