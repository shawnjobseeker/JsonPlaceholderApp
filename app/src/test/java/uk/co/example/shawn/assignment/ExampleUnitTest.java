package uk.co.example.shawn.assignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.example.shawn.assignment.model.Comment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19)
public class ExampleUnitTest {

    private PostFragment fragment;
    private CommentAdapter adapter;
    private ListView comments;

    @Before
    public void setup() {
        MainActivity activity = Robolectric.buildActivity(MainActivity.class).get();
        RealmConfiguration config = new RealmConfiguration.Builder(activity).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        fragment = new PostFragment();
        setDefaultArguments(fragment);
        startFragment(fragment);
        comments = fragment.comments;
        Comment comment = new Comment();
        adapter = new CommentAdapter(fragment.getContext(), Arrays.asList(comment));
        fragment.setCommentAdapter(adapter);
    }

    @Test
    public void fragmentNotNull() throws Exception {
        assertNotNull(fragment);
    }
    @Test
    public void fragmentViewVisible() throws Exception {
        assertNotNull(fragment.getView());
        assertThat(fragment.getView().getVisibility(), equalTo(View.VISIBLE));
    }
    @Test
    public void listViewNotEmpty() throws Exception {
        assertNotNull(comments);
        assertEquals(comments, fragment.comments);
        assertEquals(adapter, fragment.comments.getAdapter());
        assertThat(adapter.getCount(), not(0));

    }
    private void setDefaultArguments(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putInt("postId", 1);
        fragment.setArguments(bundle);
    }

}