package proj.melkowski.uicomponents.spinner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;

import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;

public class RepeatSpinner extends Spinner<Integer> {

    private static int maxLimit = 50;
    private static int minLimit = 1;

    public RepeatSpinner() {
        super(minLimit, maxLimit, 1);

        NumberFormat format = NumberFormat.getIntegerInstance();
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (c.isContentChange()) {
                ParsePosition parsePosition = new ParsePosition(0);
                format.parse(c.getControlNewText(), parsePosition);
                if (parsePosition.getIndex() == 0 ||
                        parsePosition.getIndex() < c.getControlNewText().length()) {
                    return null;
                }
            }
            return c;
        };
        TextFormatter<Integer> priceFormatter = new TextFormatter<Integer>(new IntegerStringConverter(), 1, filter);

        focusedProperty().addListener(new ChangeListener<Boolean>() {
                                          @Override
                                          public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                                              Integer value = Integer.valueOf(getEditor().getText());
                                              value = value > maxLimit ? maxLimit :
                                                      value < minLimit ? minLimit : value;
                                              getValueFactory().setValue(value);
                                              getEditor().setText(value.toString());
                                          }
                                      }
        );

        setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1));
        getEditor().setTextFormatter(priceFormatter);
    }
}
