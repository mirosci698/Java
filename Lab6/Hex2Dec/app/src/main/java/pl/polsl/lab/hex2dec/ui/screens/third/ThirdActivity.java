package pl.polsl.lab.hex2dec.ui.screens.third;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import pl.polsl.lab.hex2dec.R;

/**
 * Class for second activity of application.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class ThirdActivity extends AppCompatActivity implements ThirdContract.View {

    /**
     * Field for presenter.
     */
    private ThirdContract.Presenter presenter;

    /**
     * Method for setting layout.
     *
     * @param savedInstanceState bundle with saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        //setting title of activity, got from main activity
        ThirdActivity.this.setTitle(getStartIntent().getStringExtra("name"));
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        presenter = new ThirdPresenter(this);
        presenter.start();
        //getting history for displaying
        ArrayList<String> historyList = presenter.getHistory();
        //setting adapter - needed with recycler view
        recyclerView.setAdapter(new HistoryAdapter(historyList, recyclerView, presenter));
    }

    /**
     * Method for getting starting event.
     *
     * @return intent which started activity
     */
    @Override
    public Intent getStartIntent() {
        Intent intent = getIntent();
        return intent;
    }

}
