package ufcg.embedded.miniprojeto.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import ufcg.embedded.miniprojeto.R;
import ufcg.embedded.miniprojeto.models.Fruit;
import ufcg.embedded.miniprojeto.models.Shop;
import ufcg.embedded.miniprojeto.toolbox.PagerAdapter;

public class MainActivity extends AppCompatActivity {
    private String BASE_URL = "https://api.predic8.de";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Shop shop;
    private Fruit fruit;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);

        //Criando Tabs
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.titles)));
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
