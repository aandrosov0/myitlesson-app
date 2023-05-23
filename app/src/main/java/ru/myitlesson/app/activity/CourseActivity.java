package ru.myitlesson.app.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import ru.myitlesson.api.MyItLessonClient;
import ru.myitlesson.api.entity.CourseEntity;
import ru.myitlesson.api.entity.ModuleEntity;
import ru.myitlesson.app.R;
import ru.myitlesson.app.adapter.FragmentListAdapter;
import ru.myitlesson.app.api.Client;
import ru.myitlesson.app.fragment.CourseInfoFragment;
import ru.myitlesson.app.fragment.ModulesFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CourseActivity extends AppCompatActivity {

    public static final String COURSE_ID_EXTRA = "COURSE_ID";

    private final FragmentListAdapter fragmentAdapter = new FragmentListAdapter(this);

    private Toolbar toolbar;

    private CourseInfoFragment courseInfoFragment;
    private ModulesFragment modulesFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_activity);

        courseInfoFragment = new CourseInfoFragment();
        modulesFragment = new ModulesFragment();

        fragmentAdapter.getFragments().addAll(Arrays.asList(courseInfoFragment, modulesFragment));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.pager);

        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager, this::configureTabs).attach();

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_back);

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        int courseId = getIntent().getExtras().getInt(COURSE_ID_EXTRA);
        new Thread(() -> Client.showDialogIfApiError(this, () -> loadCourseData(courseId))).start();
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

    private void loadCourseData(int id) throws IOException {
        MyItLessonClient api = Client.getInstance().api();

        CourseEntity course = api.course().get(id);

        courseInfoFragment.loadCourse(course);
        modulesFragment.loadCourseData(course);

        runOnUiThread(() -> toolbar.setTitle(course.getTitle()));
    }
}
