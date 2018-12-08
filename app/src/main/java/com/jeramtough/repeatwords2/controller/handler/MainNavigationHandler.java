package com.jeramtough.repeatwords2.controller.handler;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jeramtough.jtandroid.controller.handler.JtIocHandler;
import com.jeramtough.jtandroid.ioc.annotation.InjectService;
import com.jeramtough.repeatwords2.R;
import com.jeramtough.repeatwords2.business.MainNavigationService;
import com.jeramtough.repeatwords2.component.learningmode.LearningMode;
import com.jeramtough.repeatwords2.component.teacher.TeacherType;
import com.jeramtough.repeatwords2.controller.fragment.*;

/**
 * @author 11718
 */
public class MainNavigationHandler extends JtIocHandler {
    private FrameLayout layoutFragmentsContainer;
    private BottomNavigationBar bottomNavigationBar;

    private DictionaryFragment dictionaryFragment;
    private SettingFragment settingFragment;
    private ListFragment listFragment;
    private LearningFragment learningFragment;

    private SparseArray<BaseFragment> fragments;

    private TeacherType currentTeacherType;
    private LearningMode currentLearningMode;

    @InjectService
    private MainNavigationService mainNavigationService;

    public MainNavigationHandler(AppCompatActivity activity) {
        super(activity);

        layoutFragmentsContainer = findViewById(R.id.layout_fragments_container);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setTabSelectedListener(new MySimpleOnTabSelectedListener());
        initResources();
    }

    @Override
    protected void initResources() {

        currentTeacherType = mainNavigationService.getTeacherType();
        currentLearningMode = mainNavigationService.getLearningMode();
        initFragments();
        initNavigationButtons();
    }


    public class MySimpleOnTabSelectedListener
            extends BottomNavigationBar.SimpleOnTabSelectedListener {
        @Override
        public void onTabSelected(int position) {

            if (mainNavigationService.getTeacherType() != currentTeacherType ||
                    mainNavigationService.getLearningMode() != currentLearningMode) {
                currentTeacherType = mainNavigationService.getTeacherType();
                currentLearningMode = mainNavigationService.getLearningMode();
                updateStudyFragment();
            }

            FragmentTransaction fragmentTransaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            for (int i = 0; i < fragments.size(); i++) {
                Fragment fragment = fragments.get(i);
                fragmentTransaction.hide(fragment);
            }
            fragmentTransaction.show(fragments.get(position));
            fragmentTransaction.commit();

            fragments.get(position).onSelected();
        }

        @Override
        public void onTabUnselected(int position) {
            fragments.get(position).onUnselected();
        }
    }

    public void updateStudyFragment() {

        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        Fragment beRemovedFragment = getActivity().getSupportFragmentManager().findFragmentByTag(
                0 + "");
        fragmentTransaction.remove(beRemovedFragment);
        fragmentTransaction.commitNow();

        FragmentTransaction fragmentTransaction1 =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.add(layoutFragmentsContainer.getId(), fragments.get(0), 0 + "");
        fragmentTransaction1.commitNow();

    }

    //*************************
    private void initFragments() {
        fragments = new SparseArray<>();

        dictionaryFragment = new DictionaryFragment();
        settingFragment = new SettingFragment();
        listFragment = new ListFragment();
        learningFragment = new LearningFragment();


        fragments.append(0, learningFragment);
        fragments.append(1, settingFragment);
        fragments.append(2, listFragment);
        fragments.append(3, dictionaryFragment);

        for (int i = 0; i < fragments.size(); i++) {
            Fragment fragment = fragments.get(i);
            FragmentTransaction fragmentTransaction =
                    getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(layoutFragmentsContainer.getId(), fragment, i + "");
            fragmentTransaction.commitNow();
        }
    }

    private void initNavigationButtons() {
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_black_24dp, "learning"))
                .addItem(new BottomNavigationItem(R.drawable.ic_menu_settings_holo_light,
                        "setting"))
                .addItem(new BottomNavigationItem(R.drawable.ic_list, "list")).addItem(
                new BottomNavigationItem(R.drawable.ic_dashboard_black_24dp, "dictionary"))
                .initialise();

        new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    Fragment fragment =
                            getActivity().getSupportFragmentManager().findFragmentByTag("0");
                    if (fragment.isVisible()) {
                        bottomNavigationBar.selectTab(0, true);
                        break;
                    }
                }
            }
        }.start();

    }


}
