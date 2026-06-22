/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package customers;

/**
 *
 * @author artoc
 */


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.Icon;

public final class AppSession3 {

    public static final String PROP_PAYMENT_DATA = "paymentData";

    private static final Object LOCK = new Object();
    private static final PropertyChangeSupport PCS =
            new PropertyChangeSupport(AppSession3.class);

    private static PaymentData paymentData = PaymentData.empty();

    private AppSession3() {
        // Prevent object creation
    }

    public static final class PaymentData {
        private final String methodName;
        private final Icon icon;
        private final long updatedAt;

        public PaymentData(String methodName, Icon icon) {
            this.methodName = clean(methodName);
            this.icon = icon;
            this.updatedAt = System.currentTimeMillis();
        }

        public static PaymentData empty() {
            return new PaymentData("N/A", null);
        }

        private static String clean(String value) {
            if (value == null || value.trim().isEmpty()) {
                return "N/A";
            }
            return value.trim();
        }

        public String getMethodName() {
            return methodName;
        }

        public Icon getIcon() {
            return icon;
        }

        public long getUpdatedAt() {
            return updatedAt;
        }

        public boolean hasIcon() {
            return icon != null;
        }
    }

    public static void setPaymentData(String methodName, Icon icon) {
        if (icon == null) {
            System.out.println("AppSession3: icon is null, payment data not saved.");
            return;
        }

        PaymentData oldData;
        PaymentData newData = new PaymentData(methodName, icon);

        synchronized (LOCK) {
            oldData = paymentData;
            paymentData = newData;
        }

        PCS.firePropertyChange(PROP_PAYMENT_DATA, oldData, newData);
    }

    // Backward compatible method, in case old code calls setPaymentData(icon)
    public static void setPaymentData(Icon icon) {
        setPaymentData("Selected Payment Method", icon);
    }

    public static PaymentData getPaymentData() {
        synchronized (LOCK) {
            return paymentData;
        }
    }

    public static Icon getPaymentIcon() {
        return getPaymentData().getIcon();
    }

    // Backward compatible method for your old FULL_RECEIPT code
    public static Icon getTransferIcon() {
        return getPaymentIcon();
    }

    public static String getPaymentMethod() {
        return getPaymentData().getMethodName();
    }

    public static void clearPaymentData() {
        PaymentData oldData;

        synchronized (LOCK) {
            oldData = paymentData;
            paymentData = PaymentData.empty();
        }

        PCS.firePropertyChange(PROP_PAYMENT_DATA, oldData, paymentData);
    }

    public static void addPaymentListener(PropertyChangeListener listener) {
        if (listener != null) {
            PCS.addPropertyChangeListener(listener);
        }
    }

    public static void removePaymentListener(PropertyChangeListener listener) {
        if (listener != null) {
            PCS.removePropertyChangeListener(listener);
        }
    }
}
