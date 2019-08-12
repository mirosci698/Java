package pl.polsl.lab.hex2dec.ui.screens.main;

import android.content.Intent;

import pl.polsl.lab.hex2dec.ui.base.BasePresenter;
import pl.polsl.lab.hex2dec.ui.base.BaseView;

/**
 * Contract for main activity.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public interface MainContract {

    /**
     * Interface representing view.
     */
    interface View extends BaseView {


        /**
         * Method for creating new intent.
         *
         * @param cls class which will be used
         * @return intent which perform redirection
         */
        Intent createNewIntent(Class cls);

        /**
         * Method for creating new intent.
         *
         * @param intent which starts new activity
         */
        void startNewActivity(Intent intent);
    }

    /**
     * Interface representing presenter.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method performing operation when we need to convert.
         */
        void onConvertButtonClicked(int calculatingMode);

        /**
         * Method performing operation when we need history.
         */
        void onHistoryButtonClicked();
    }

}
