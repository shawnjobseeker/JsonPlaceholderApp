package uk.co.example.shawn.assignment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import uk.co.example.shawn.assignment.model.Post;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.post_list)
    ListView postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RealmConfiguration config = new RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
        new MainActivityPresenter(this).load();
    }
    public void setPostAdapter(List<Post> posts) {
        postList.setAdapter(new PostAdapter(this, posts));
        postList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostFragment fragment = new PostFragment();
                Post post = (Post)adapterView.getItemAtPosition(i);
                Bundle bundle = new Bundle();
                bundle.putInt("postId", post.getId());
                fragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.activity_main, fragment, "post");
                transaction.show(fragment);
                transaction.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("post");
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
        else
            super.onBackPressed();
    }
}
