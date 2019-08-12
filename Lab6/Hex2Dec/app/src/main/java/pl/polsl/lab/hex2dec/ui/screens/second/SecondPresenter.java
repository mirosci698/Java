package pl.polsl.lab.hex2dec.ui.screens.second;

import java.util.LinkedList;

import pl.polsl.lab.hex2dec.data.model.Converter;
import pl.polsl.lab.hex2dec.data.model.InvalidConversionException;
import pl.polsl.lab.hex2dec.data.model.InvalidHexNumberException;
import pl.polsl.lab.hex2dec.ui.screens.main.SimpleSingleton;
import pl.polsl.lab.hex2dec.ui.screens.second.SecondContract.Presenter;

/**
 * Class for presenter of main activity.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class SecondPresenter implements Presenter {

    /**
     * Field for view.
     */
    private SecondContract.View view;

    /**
     * Constant representing number of recquired arguments (not static according to instructions on page platforma.polsl.pl.).
     */
    private final int NUMBER_OF_ARGS = 3;

    /**
     * Field for required model.
     */
    private Converter model;

    /**
     * Constructor of presenter.
     *
     * @param view reference to view
     */
    SecondPresenter(SecondContract.View view) {
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
     * Method for setting model.
     *
     * @param mode information about type of conversion
     * @param number string with number to convert
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void setModel (int mode, String number) throws NumberFormatException {
        switch(mode) {
            case 0:
                model.setHexNumber(number);
                break;
            case 1:
                model.setDecNumber(Double.parseDouble(number));
                break;
            default:
                //for other integer than 0 or 1
                throw new NumberFormatException();
        }
    }

    /**
     * Method for getting input after getting wrong program parameters.
     *
     * @param mode information about type of conversion
     * @throws NumberFormatException in case of unparsable decimal number or wrong mode
     */
    private void setConvertion (int mode) throws NumberFormatException,
            InvalidConversionException, InvalidHexNumberException {
        switch(mode) {
            case 0:
                model.calculateDec2Hex();
                break;
            case 1:
                model.calculateHex2Dec();
                break;
            default:
                //for other integer than 0 or 1
                throw new NumberFormatException();
        }
    }

    /**
     * Method for getting input and setting variables.
     *
     * @return info about success of operations (no exceptions)
     * @throws InvalidConversionException in case of wrong type of conversion
     * @throws InvalidHexNumberException in case of wrong hex number
     * @throws NumberFormatException in case of wrong int or double
     */
    private boolean inputAndProcessControl(String args[]) throws InvalidConversionException,
            InvalidHexNumberException, NumberFormatException {
        int inputType = Integer.parseInt(args[0]);
        this.setModel(inputType, args[1]);
        int typeOfConvertion = Integer.parseInt(args[2]);
        if (inputType != 0 && inputType != 1)
            throw new NumberFormatException("Integer of wrong value.");
        if (typeOfConvertion != 0 && typeOfConvertion != 1)
            throw new NumberFormatException("Integer of wrong value.");
        //try to convert
        this.setConvertion(typeOfConvertion);
        return true;
    }

    /**
     * Method for getting arguments properly.
     *
     * @return array of Strings with arguments
     */
    private String[] getArguments(int modeFromLayout) {
        LinkedList<String> listOfValues = new LinkedList<String>();
        listOfValues.add(String.valueOf(modeFromLayout));
        String temporary = view.getEditTextValue();
        if (!temporary.isEmpty())
            listOfValues.add(temporary);
        if (modeFromLayout == 0)
            listOfValues.add(String.valueOf(1));
        else
            listOfValues.add(String.valueOf(0));
        String[] result = new String[listOfValues.size()];
        result = listOfValues.toArray(result);
        return result;
    }

    /**
     * Method performing operation when convert button is clicked.
     */
    @Override
    public void onConvertButtonClicked(int calculatingMode) {
        String[] arguments = getArguments(calculatingMode);
        try {
            if (inputAndProcessControl(arguments))
                view.setTextViewResultValue(model.toString());
        }
        catch(ArrayIndexOutOfBoundsException e) {
            view.setTextViewResultValue("Wrong number of parameters.");
        }
        catch(NumberFormatException | InvalidConversionException | InvalidHexNumberException e) {
            view.setTextViewResultValue(e.getMessage());
        }
    }

}
