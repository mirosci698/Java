package pl.polsl.lab.hex2dec.ui.screens.second;

import android.content.Intent;
import android.view.View;

import pl.polsl.lab.hex2dec.ui.base.BasePresenter;
import pl.polsl.lab.hex2dec.ui.base.BaseView;

/**
 * Contract for main activity.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public interface SecondContract {

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

        /**
         * Method for getting text from edit text view.
         *
         * @return intent which started activity
         */
        String getEditTextValue();

        /**
         * Method getting value from textViewResult.
         *
         * @return String with value from textViewResult
         */
        void setTextViewResultValue(String targetValue);

        /**
         * Method performing operation when convert button is clicked.
         *
         * @param view view of application
         */
        void onConvertButtonClicked(android.view.View view);
    }

    /**
     * Interface representing presenter.
     */
    interface Presenter extends BasePresenter {

        /**
         * Method performing operation when we need to convert.
         */
        void onConvertButtonClicked(int calculatingMode);

    }

}
