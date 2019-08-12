package pl.polsl.lab.hex2dec.ui.screens.main;

import android.content.Intent;

import pl.polsl.lab.hex2dec.data.model.Converter;
import pl.polsl.lab.hex2dec.ui.screens.main.MainContract.Presenter;
import pl.polsl.lab.hex2dec.ui.screens.second.SecondActivity;
import pl.polsl.lab.hex2dec.ui.screens.third.ThirdActivity;

/**
 * Class for presenter of main activity.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class MainPresenter implements Presenter {

    /**
     * Field for view.
     */
    private MainContract.View view;

    /**
     * Field for required model.
     */
    private Converter model;

    /**
     * Constructor of presenter.
     *
     * @param view reference to view
     */
    MainPresenter(MainContract.View view) {
        this.view = view;
        getModel();
    }

    /**
     * Method for starting.
     */
    @Override
    public void start() {

    }

    /**
     * Method for getting model (one instance).
     */
    @Override
    public void getModel() {
        model = SimpleSingleton.getInstance().getModel();
    }

    /**
     * Method performing operation when convert button is clicked.
     */
    @Override
    public void onConvertButtonClicked(int calculatingMode) {
        Intent intent = view.createNewIntent(SecondActivity.class);
        //information about mode of calculations
        intent.putExtra("mode", calculatingMode);
        view.startNewActivity(intent);
    }

    /**
     * Method performing operation when history button is clicked.
     */
    @Override
    public void onHistoryButtonClicked() {
        Intent intent = view.createNewIntent(ThirdActivity.class);
        //name of next activity
        intent.putExtra("name", "History of calculations");
        view.startNewActivity(intent);
    }

}
