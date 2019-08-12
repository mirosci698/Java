package pl.polsl.lab.hex2dec.ui.screens.main;

import pl.polsl.lab.hex2dec.data.model.Converter;

/**
 * Implementation of singleton for model.
 *
 * @author Mirosław Ściebura
 * @version 1.0
 */
public class SimpleSingleton {

    /**
     * Field for instance of singleton.
     */
    private static SimpleSingleton instance;

    /**
     * Field for model (ony when we have instance of singleton, used because I want to provide full compatibility of model).
     */
    private Converter model;

    /**
     * Constructor which fill table with hexadecimal digits.
     *
     * @param dec double with decimal number
     */
    private SimpleSingleton(double dec) {
        model = new Converter(dec);
    }

    /**
     * Getter for instance of Singleton
     *
     * @return SimpleSingleton instance of Simple singleton
     */
    public static SimpleSingleton getInstance(){
        if (instance == null)
            instance = new SimpleSingleton(0.0);
        return instance;
    }

    /**
     * Getter for instance model from instance of singleton.
     *
     * @return Converter instance of model
     */
    public Converter getModel(){
        return model;
    }
}
