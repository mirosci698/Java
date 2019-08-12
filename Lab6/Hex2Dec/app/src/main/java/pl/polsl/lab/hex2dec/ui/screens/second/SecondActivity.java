package pl.polsl.lab.hex2dec.ui.screens.second;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import pl.polsl.lab.hex2dec.R;

/**
 * Class for second activity of application.
 *
 * @author Mirosław Ściebura
 * @version 1.1
 */
public class SecondActivity extends AppCompatActivity implements SecondContract.View {

    /**
     * Field for presenter.
     */
    private SecondContract.Presenter presenter;

    /**
     * Field for mode (0 - Hex2Dec, 1 - Dec2Hex).
     */
    private int mode;

    /**
     * Method for getting activity title.
     *
     * @return String with correct title
     */
    private String getActivityTitle() {
        String title;
        switch(mode) {
            case 0:
                title = "Hexadecimal to decimal";
                break;
            case 1:
                title = "Decimal to hexadecimal";
                break;
            case 2:
                title = "History";
                break;
            default:
                title = "Other";
                break;
        }
        return title;
    }

    /**
     * Method for creating layout.
     *
     * @param savedInstanceState bundle with saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mode = getStartIntent().getIntExtra("mode", 3);
        SecondActivity.this.setTitle(getActivityTitle());
        Button convertButton = findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConvertButtonClicked(v);
            }
        });
        presenter = new SecondPresenter(this);
        presenter.start();
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

    /**
     * Method for getting text from edit text view.
     *
     * @return intent which started activity
     */
    @Override
    public String getEditTextValue() {
        EditText editText = (EditText)findViewById(R.id.editText);
        return editText.getText().toString();
    }

    /**
     * Method for setting value of text view result.
     *
     * @param targetValue String with label
     */
    @Override
    public void setTextViewResultValue(String targetValue) {
        ((TextView)findViewById(R.id.textViewResult)).setText(targetValue);
    }

    /**
     * Method performing operation when convert button is clicked.
     *
     * @param view view of application
     */
    @Override
    public void onConvertButtonClicked(View view) {
        presenter.onConvertButtonClicked(mode);
    }

}
