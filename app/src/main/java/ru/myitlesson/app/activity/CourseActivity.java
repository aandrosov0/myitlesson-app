package ru.myitlesson.app.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.App;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.FragmentListAdapter;
import ru.myitlesson.app.fragment.CourseInfoFragment;
import ru.myitlesson.app.fragment.ModulesFragment;
import ru.myitlesson.app.repository.RepositoryResult;

import java.util.Arrays;

public class CourseActivity extends AppCompatActivity {

    public static final String COURSE_EXTRA = "COURSE_ID";

    private final FragmentListAdapter fragmentAdapter = new FragmentListAdapter(this);

    private final CourseInfoFragment courseInfoFragment = new CourseInfoFragment();
    private final ModulesFragment modulesFragment = new ModulesFragment();

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        fragmentAdapter.getFragments().addAll(Arrays.asList(courseInfoFragment, modulesFragment));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.pager);

        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager, this::configureTabs).attach();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        App.getCourseRepository().get(getIntent().getIntExtra(COURSE_EXTRA, -1), this::onCourseGetCompleted);
    }

    private void configureTabs(TabLayout.Tab tab, int position) {
        switch (position) {
            case 0:
                tab.setText(getString(R.string.info));
                break;

            case 1:
                tab.setText(getString(R.string.modules));
                break;
        }
    }

    private void onCourseGetCompleted(RepositoryResult<CourseEntity> result) {
        if(result instanceof RepositoryResult.Success) {
            CourseEntity course = ((RepositoryResult.Success<CourseEntity>) result).data;

            runOnUiThread(() -> toolbar.setTitle(course.getTitle()));
            runOnUiThread(() -> courseInfoFragment.loadCourse(course));
            runOnUiThread(() -> modulesFragment.loadCourse(course));
        }
    }
}
