package popup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jxmapviewer.viewer.GeoPosition;

/**
 * Keeps the pickup and return map selections separate for the active booking.
 */
public final class BookingRouteState {

    private static GeoPosition pickupPosition;
    private static GeoPosition returnPosition;
    private static String pickupAddress;
    private static String returnAddress;
    private static boolean pickupConfirmed;
    private static boolean returnConfirmed;
    private static boolean routeLoading;
    private static GeoPosition roadRouteDestination;
    private static List<GeoPosition> roadRoute = Collections.emptyList();

    private BookingRouteState() {
    }

    public static synchronized void setPickup(GeoPosition position, String address) {
        pickupPosition = position;
        pickupAddress = clean(address);
        pickupConfirmed = false;
        returnConfirmed = false;
        routeLoading = false;
        roadRouteDestination = null;
        roadRoute = Collections.emptyList();
    }

    public static synchronized void setReturn(GeoPosition position, String address) {
        returnPosition = position;
        returnAddress = clean(address);
        returnConfirmed = false;
        roadRouteDestination = null;
        roadRoute = Collections.emptyList();
    }

    public static synchronized GeoPosition getPickupPosition() {
        return pickupPosition;
    }

    public static synchronized GeoPosition getReturnPosition() {
        return returnPosition;
    }

    public static synchronized String getPickupAddress() {
        return pickupAddress;
    }

    public static synchronized String getReturnAddress() {
        return returnAddress;
    }

    public static synchronized boolean matchesPickup(String address) {
        return pickupPosition != null && sameAddress(pickupAddress, address);
    }

    public static synchronized boolean matchesReturn(String address) {
        return returnPosition != null && sameAddress(returnAddress, address);
    }

    public static synchronized void confirmPickup() {
        pickupConfirmed = pickupPosition != null && !pickupAddress.isEmpty();
    }

    public static synchronized void confirmReturn() {
        returnConfirmed = returnPosition != null && !returnAddress.isEmpty() && !roadRoute.isEmpty();
    }

    public static synchronized boolean isPickupConfirmed() {
        return pickupConfirmed;
    }

    public static synchronized boolean isReturnConfirmed() {
        return returnConfirmed;
    }

    public static synchronized void setRouteLoading(boolean loading) {
        routeLoading = loading;
    }

    public static synchronized boolean isRouteLoading() {
        return routeLoading;
    }

    public static synchronized void setRoadRoute(GeoPosition expectedReturn, List<GeoPosition> route) {
        if (samePosition(pickupPosition, expectedReturn) || samePosition(returnPosition, expectedReturn)) {
            roadRouteDestination = expectedReturn;
            roadRoute = route == null
                    ? Collections.emptyList()
                    : Collections.unmodifiableList(new ArrayList<>(route));
        }
        routeLoading = false;
    }

    public static synchronized List<GeoPosition> getRoadRoute() {
        return roadRoute;
    }

    public static synchronized boolean isRoadRouteFor(GeoPosition position) {
        return samePosition(roadRouteDestination, position);
    }

    public static synchronized boolean hasRoadRoute() {
        return !roadRoute.isEmpty();
    }

    public static synchronized void clear() {
        pickupPosition = null;
        returnPosition = null;
        pickupAddress = null;
        returnAddress = null;
        pickupConfirmed = false;
        returnConfirmed = false;
        routeLoading = false;
        roadRouteDestination = null;
        roadRoute = Collections.emptyList();
    }

    private static boolean sameAddress(String saved, String displayed) {
        return saved != null && saved.equals(clean(displayed));
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    private static boolean samePosition(GeoPosition a, GeoPosition b) {
        return a != null && b != null
                && Double.compare(a.getLatitude(), b.getLatitude()) == 0
                && Double.compare(a.getLongitude(), b.getLongitude()) == 0;
    }
}
