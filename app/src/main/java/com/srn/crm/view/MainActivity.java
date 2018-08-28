package com.srn.crm.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.srn.crm.R;
import com.srn.crm.core.api.UserApi;
import com.srn.crm.view.base.BaseActivity;
import com.srn.crm.view.common.OpeningActivity;
import com.srn.crm.view.home.HomeFragment;
import com.srn.crm.view.location.LocationFragment;
import com.srn.crm.view.notification.NotificationFragment;
import com.srn.crm.view.points.PointsFragment;
import com.srn.crm.view.rewards.RewardsFragment;
import com.srn.crm.view.utils.Redirector;
import com.srn.crm.view.widget.CircleTransform;

import java.lang.reflect.Field;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private UserApi mUserApi;

    /*@BindView(R.id.message)
    protected TextView mTextMessage;*/

    @BindView(R.id.drawer_layout)
    protected DrawerLayout mDrawer;

    @BindView(R.id.navigation)
    protected BottomNavigationView mBottomNavigation;

    @BindView(R.id.drawer_navigationview)
    protected NavigationView mNavigationView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    addFragment(new HomeFragment());
                    return true;
                case R.id.navigation_dashboard:
                    addFragment(new LocationFragment());
                    return true;
                case R.id.navigation_notifications:
                    addFragment(new PointsFragment());
                    return true;
                case R.id.navigation_location:
                    addFragment(new NotificationFragment());
                    return true;
                case R.id.navigation_points:
                    addFragment(new RewardsFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserApi = new UserApi(getApplicationContext());
        setupProfileImage();
        mActionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigation.setSelectedItemId(R.id.navigation_home);
        removeShiftMode(mBottomNavigation);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                    mDrawer.closeDrawer(GravityCompat.START);
                } else {
                    mDrawer.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setupProfileImage() {
        ImageView profileImage = mNavigationView.getHeaderView(0).findViewById(R.id.profile_image);
        Picasso.with(mActivity).load("https://lh3.googleusercontent.com/-AWsjux-MPUc/WTzaAMLGNKI/AAAAAAAAAmo/vq8dH_LaqZIuf2nirZYMarpBd4KMBgyXgCEwYBhgL/w140-h140-p/tmp_32224-20170611_0933242045922055.jpg")
                .error(R.drawable.ic_profile_default)
                .noFade()
                .transform(new CircleTransform())
                .into(profileImage);
    }

    private void removeShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("ERROR NO SUCH FIELD", "Unable to get shift mode field");
        } catch (IllegalAccessException e) {
            Log.e("ERROR ILLEGAL ALG", "Unable to change value of shift mode");
        }
    }
}
