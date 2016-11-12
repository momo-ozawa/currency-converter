package com.mozawa.coineyapp.ui.rates;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mozawa.coineyapp.R;
import com.mozawa.coineyapp.ui.base.BaseActivity;
import com.mozawa.coineyapp.ui.widgets.DividerItemDecoration;

import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatesActivity extends BaseActivity implements RatesMvpView {

    @Inject
    RatesPresenter presenter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ratesRecyclerView)
    RecyclerView ratesRecyclerView;

    private RatesAdapter ratesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_rates);
        ButterKnife.bind(this);

        // Set up toolbar.
        setSupportActionBar(toolbar);

        // Set up adapter.
        ratesAdapter = new RatesAdapter(this);

        // Always need to attach the view before calling any presenter methods.
        presenter.attachView(this);

        presenter.loadMap();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*******
     * RatesMvpView implementation
     *******/

    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public void showResult(Map<String, Double> map) {
        ratesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ratesRecyclerView.setAdapter(ratesAdapter);
        ratesRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        ratesAdapter.setData(map);
        ratesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showResultEmpty() {

    }
}
