package pl.polsl.lab.hex2dec.ui.screens.third;

import android.content.Intent;

import java.util.ArrayList;

import pl.polsl.lab.hex2dec.ui.base.BasePresenter;
import pl.polsl.lab.hex2dec.ui.base.BaseView;

/**
 * Contract for third activity.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public interface ThirdContract {

    /**
     * Interface representing view.
     */
    interface View extends BaseView {

        /**
         * Method for getting starting event.
         *
         * @return intent which started activity
         */
        Intent getStartIntent();

    }

    /**
     * Interface representing presenter.
     */
    interface Presenter extends BasePresenter {

        /**
         * Getter for history of calculations.
         *
         * @return ArrayList<String> list with history of calculations
         */
        ArrayList<String> getHistory();

    }

}
