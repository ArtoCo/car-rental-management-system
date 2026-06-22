package popup;

import java.util.Locale;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public final class InputRules {

    private InputRules() {
    }

    public static String normalizePlate(String value) {
        return value == null ? "" : value.trim().toUpperCase(Locale.ROOT);
    }

    public static boolean isValidPlate(String value) {
        String plate = normalizePlate(value);
        return !plate.isEmpty() && plate.matches("[A-Z0-9 -]+");
    }

    public static String digitsOnly(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    public static boolean isValidPhilippinePhone(String value) {
        return digitsOnly(value).matches("09\\d{9}");
    }

    public static void installPlateFilter(JTextField field) {
        if (field == null || !(field.getDocument() instanceof AbstractDocument)) {
            return;
        }
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String clean = normalizePlate(text).replaceAll("[^A-Z0-9 -]", "");
                super.replace(fb, offset, length, clean, attrs);
            }
        });
    }

    public static void installPhilippinePhoneFilter(JTextField field) {
        if (field == null || !(field.getDocument() instanceof AbstractDocument)) {
            return;
        }
        ((AbstractDocument) field.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                    throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String current = fb.getDocument().getText(0, fb.getDocument().getLength());
                String candidate = current.substring(0, offset) + digitsOnly(text)
                        + current.substring(offset + length);
                String digits = digitsOnly(candidate);
                if (digits.length() > 11) {
                    return;
                }
                super.replace(fb, 0, fb.getDocument().getLength(), digits, attrs);
            }
        });
    }
}
