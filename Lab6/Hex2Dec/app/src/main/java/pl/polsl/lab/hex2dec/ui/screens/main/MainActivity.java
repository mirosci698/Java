package pl.polsl.lab.hex2dec.ui.screens.main;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;

import pl.polsl.lab.hex2dec.R;

/**
 * Class for main activity of application.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class MainActivity extends AppCompatActivity implements MainContract.View {

    /**
     * Field for presenter.
     */
    private MainContract.Presenter presenter;

    /**
     * Method for setting text view value.
     *
     * @param savedInstanceState bundle with saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.options);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Resources resources = getResources();
        String[] modes = resources.getStringArray(R.array.modes);
        ArrayList<String> modesList = new ArrayList<>(Arrays.asList(modes));
        presenter = new MainPresenter(this);
        presenter.start();
        //adapter - implementation needed in case of RecyclerView
        recyclerView.setAdapter(new MenuAdapter(modesList, recyclerView, presenter));
    }

    /**
     * Method for setting text view value.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Method for creating new intent.
     *
     * @param cls class which will be used
     * @return intent which perform redirection
     */
    @Override
    public Intent createNewIntent(Class cls) {
        return new Intent(this, cls);
    }

    /**
     * Method for creating new intent.
     *
     * @param intent which starts new activity
     */
    @Override
    public void startNewActivity(Intent intent) {
        startActivity(intent);
    }

}
