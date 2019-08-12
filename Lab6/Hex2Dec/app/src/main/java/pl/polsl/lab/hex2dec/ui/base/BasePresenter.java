package pl.polsl.lab.hex2dec.ui.base;

/**
 * Base presenter for MVP.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public interface BasePresenter {
    /**
     * Method for starting.
     */
    void start();

    /**
     * Method for getting model (one instance).
     */
    void getModel();
}
