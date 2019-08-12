package pl.polsl.lab.hex2dec.ui.screens.third;

import java.util.ArrayList;
import java.util.Hashtable;

import pl.polsl.lab.hex2dec.data.model.Converter;
import pl.polsl.lab.hex2dec.ui.screens.main.SimpleSingleton;
import pl.polsl.lab.hex2dec.ui.screens.third.ThirdContract.Presenter;

/**
 * Class for presenter of third activity.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class ThirdPresenter implements Presenter {

    /**
     * Field for view.
     */
    private ThirdContract.View view;

    /**
     * Field for required model.
     */
    private Converter model;

    /**
     * Constructor of presenter.
     *
     * @param view reference to view
     */
    ThirdPresenter(ThirdContract.View view) {
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
     * Getter for history of calculations.
     *
     * @return ArrayList<String> list with history of calculations
     */
    @Override
    public ArrayList<String> getHistory() {
        Hashtable<Integer, String> history = model.getHistory();
        ArrayList<String> historyRepresentation = new ArrayList<>();
        for (int i = 1; i <= history.size(); i++)
            historyRepresentation.add("" + String.valueOf(i) + ":" + history.get(i));
        return historyRepresentation;
    }

}
