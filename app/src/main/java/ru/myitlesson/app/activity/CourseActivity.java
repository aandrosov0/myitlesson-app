package ru.myitlesson.app.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.FragmentListAdapter;
import ru.myitlesson.app.fragment.CourseInfoFragment;
import ru.myitlesson.app.fragment.ModulesFragment;

import java.io.IOException;
import java.util.Arrays;

public class CourseActivity extends ClientActivity {

    public static final String COURSE_EXTRA = "COURSE_ID";

    private final FragmentListAdapter fragmentAdapter = new FragmentListAdapter(this);

    private Toolbar toolbar;

    private final CourseInfoFragment courseInfoFragment = new CourseInfoFragment();
    private final ModulesFragment modulesFragment = new ModulesFragment();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        fragmentAdapter.getFragments().addAll(Arrays.asList(courseInfoFragment, modulesFragment));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.pager);

        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager, this::configureTabs).attach();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    protected void onLoadApiData() throws IOException {
        int courseId = getIntent().getIntExtra(COURSE_EXTRA, -1);
        CourseEntity course = api.course().get(courseId);

        courseInfoFragment.loadCourse(course);
        modulesFragment.loadCourseData(course);

        runOnUiThread(() -> toolbar.setTitle(course.getTitle()));
    }

    private void configureTabs(TabLayout.Tab tab, int position) {
        switch (position) {
            case 0:
                tab.setText(getString(R.string.course_info_page_title));
                break;

            case 1:
                tab.setText(getString(R.string.modules_page_title));
                break;
        }
    }
}
