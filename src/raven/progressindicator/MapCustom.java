package raven.progressindicator;

import com.github.kevinsawicki.http.HttpRequest;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import popup.BookingRouteState;

public class MapCustom extends JXMapViewer {

    private static final GeoPosition DEFAULT_ROUTE_START = new GeoPosition(7.426153, 125.793685);

    public enum RouteRole {
        NONE,
        PICKUP,
        RETURN,
        EDIT_PICKUP,
        EDIT_RETURN
    }

    public EventLocationSelected getEvent() {
        return event;
    }

    public void setEvent(EventLocationSelected event) {
        this.event = event;
    }

    private final Image image;
    private final AtomicInteger selectionRequest = new AtomicInteger();
    private EventLocationSelected event;
    private RouteRole routeRole = RouteRole.NONE;
    private boolean centeredOnConfirmedPickup;
    private GeoPosition fixedDisplayPosition;
    private GeoPosition draftSelectionPosition;
    private String draftSelectionAddress;

    public MapCustom() {
        image = new ImageIcon(getClass().getResource("/raven/progressindicator/pin.png")).getImage();
    }

    public void setRouteRole(RouteRole routeRole) {
        this.routeRole = routeRole == null ? RouteRole.NONE : routeRole;
    }

    public void setSavedLocation(String address) {
        String savedAddress = address == null ? "" : address.trim();
        if (savedAddress.isEmpty() || routeRole == RouteRole.NONE) {
            return;
        }
        new Thread(() -> {
            try {
                GeoPosition position = findPosition(savedAddress);
                SwingUtilities.invokeLater(() -> {
                    setAddressLocation(position);
                    saveRouteSelection(position, savedAddress);
                    if (isPickupRole()) {
                        BookingRouteState.confirmPickup();
                    }
                    repaint();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "SavedLocationGeocodeThread").start();
    }

    public void setDisplayLocation(String address) {
        String savedAddress = address == null ? "" : address.trim();
        if (savedAddress.isEmpty()) {
            return;
        }
        new Thread(() -> {
            try {
                GeoPosition position = findPosition(savedAddress);
                SwingUtilities.invokeLater(() -> {
                    fixedDisplayPosition = position;
                    setAddressLocation(position);
                    repaint();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "DisplayLocationGeocodeThread").start();
    }

    public void searchAndSelectLocation(String address) {
        String searchAddress = address == null ? "" : address.trim();
        if (searchAddress.isEmpty()) {
            return;
        }
        final int requestId = selectionRequest.incrementAndGet();
        new Thread(() -> {
            try {
                GeoPosition position = findPosition(searchAddress);
                String location = getLocation(position);
                SwingUtilities.invokeLater(() -> {
                    if (requestId != selectionRequest.get()) {
                        return;
                    }
                    setAddressLocation(position);
                    saveDraftSelection(position, location);
                    if (event != null) {
                        event.onSelected(location);
                    }
                    repaint();
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this,
                        "Location was not found on the map.",
                        "Location Not Found",
                        JOptionPane.WARNING_MESSAGE));
            }
        }, "SearchLocationGeocodeThread").start();
    }

    public void setSavedRouteLocations(String pickupAddress, String returnAddress) {
        setSavedRouteLocations(pickupAddress, returnAddress, null);
    }

    public void setSavedRouteLocations(String pickupAddress, String returnAddress, MapCustom pickupMap) {
        String pickup = pickupAddress == null ? "" : pickupAddress.trim();
        String returned = returnAddress == null ? "" : returnAddress.trim();
        if (pickup.isEmpty() || returned.isEmpty() || !isReturnRole()) {
            return;
        }
        new Thread(() -> {
            try {
                GeoPosition pickupPosition = findPosition(pickup);
                GeoPosition returnPosition = findPosition(returned);
                SwingUtilities.invokeLater(() -> {
                    if (pickupMap != null) {
                        pickupMap.fixedDisplayPosition = pickupPosition;
                        pickupMap.setAddressLocation(pickupPosition);
                        pickupMap.repaint();
                    }
                    fixedDisplayPosition = returnPosition;
                    BookingRouteState.setPickup(pickupPosition, pickup);
                    BookingRouteState.confirmPickup();
                    BookingRouteState.setReturn(returnPosition, returned);
                    setAddressLocation(returnPosition);
                    requestRoadRoute(returnPosition, true);
                    repaint();
                });
            } catch (IOException ex) {
                // A previously saved address may no longer be geocodable.
                // Keep the edit form usable and let the user select it again.
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, "SavedRouteGeocodeThread").start();
    }

    public void init() {
        setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo("", "https://tile.openstreetmap.org")));
        setAddressLocation(getInitialPosition());
        setZoom(12);

        MouseInputListener mm = new PanMouseInputListener(this);
        addMouseListener(mm);
        addMouseMotionListener(mm);
        addMouseWheelListener(new ZoomMouseWheelListenerCenter(this));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent me) {
                showLocation();
            }
        });
    }

    private void showLocation() {
        // If event isn't set yet, avoid NullPointerException
        if (event == null) {
            return;
        }
        final GeoPosition selectedPosition = getCenterPosition();
        final int requestId = selectionRequest.incrementAndGet();
        new Thread(() -> {
            try {
                String location = getLocation(selectedPosition);

                SwingUtilities.invokeLater(() -> {
                    if (requestId != selectionRequest.get()) {
                        return;
                    }
                    saveDraftSelection(selectedPosition, location);
                    event.onSelected(location);
                    repaint();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "ReverseGeocodeThread").start();
    }

    private GeoPosition getInitialPosition() {
        if (routeRole == RouteRole.NONE) {
            return DEFAULT_ROUTE_START;
        }
        GeoPosition position = isReturnRole()
                ? BookingRouteState.getReturnPosition()
                : BookingRouteState.getPickupPosition();
        return position == null ? DEFAULT_ROUTE_START : position;
    }

    private void saveRouteSelection(GeoPosition position, String location) {
        if (isPickupRole()) {
            BookingRouteState.setPickup(position, location);
            if (routeRole == RouteRole.EDIT_PICKUP) {
                fixedDisplayPosition = position;
            }
        } else if (isReturnRole()) {
            BookingRouteState.setReturn(position, location);
        }
    }

    private void saveDraftSelection(GeoPosition position, String location) {
        draftSelectionPosition = position;
        draftSelectionAddress = location == null ? "" : location.trim();
    }

    public boolean matchesDraftOrSavedSelection(String address) {
        String clean = address == null ? "" : address.trim();
        if (draftSelectionPosition != null) {
            return clean.equals(draftSelectionAddress);
        }
        return isPickupRole()
                ? BookingRouteState.matchesPickup(clean)
                : BookingRouteState.matchesReturn(clean);
    }

    public void commitSavedSelection() {
        if (draftSelectionPosition != null) {
            saveRouteSelection(draftSelectionPosition, draftSelectionAddress);
        }
        if (routeRole == RouteRole.PICKUP) {
            BookingRouteState.confirmPickup();
            fixedDisplayPosition = BookingRouteState.getPickupPosition();
            GeoPosition pickup = BookingRouteState.getPickupPosition();
            if (pickup != null) {
                requestRoadRoute(pickup, false);
            }
        } else if (routeRole == RouteRole.RETURN) {
            GeoPosition returned = BookingRouteState.getReturnPosition();
            if (returned != null) {
                requestRoadRoute(returned, false);
            }
        } else if (routeRole == RouteRole.EDIT_PICKUP) {
            BookingRouteState.confirmPickup();
            fixedDisplayPosition = BookingRouteState.getPickupPosition();
            GeoPosition pickup = BookingRouteState.getPickupPosition();
            if (pickup != null) {
                requestRoadRoute(pickup, false);
            }
        } else if (routeRole == RouteRole.EDIT_RETURN) {
            GeoPosition returned = BookingRouteState.getReturnPosition();
            if (returned != null) {
                fixedDisplayPosition = returned;
                requestRoadRoute(returned, false);
            }
        }
        draftSelectionPosition = null;
        draftSelectionAddress = null;
        repaint();
    }

    private boolean isPickupRole() {
        return routeRole == RouteRole.PICKUP || routeRole == RouteRole.EDIT_PICKUP;
    }

    private boolean isReturnRole() {
        return routeRole == RouteRole.RETURN || routeRole == RouteRole.EDIT_RETURN;
    }

    private void fitRouteToView() {
        GeoPosition pickup = BookingRouteState.getPickupPosition();
        GeoPosition returned = BookingRouteState.getReturnPosition();
        if (routeRole == RouteRole.NONE || pickup == null || returned == null) {
            return;
        }
        Set<GeoPosition> route = new HashSet<>();
        route.add(pickup);
        route.add(returned);
        zoomToBestFit(route, 0.8);
    }

    private void requestRoadRoute(GeoPosition expectedReturn, boolean fitAfterLoad) {
        GeoPosition routeStart = getRouteStartPosition();
        if (routeStart == null) {
            return;
        }
        BookingRouteState.setRouteLoading(true);
        new Thread(() -> {
            try {
                List<GeoPosition> route = getRoadRoute(routeStart, expectedReturn);
                BookingRouteState.setRoadRoute(expectedReturn, route);
                if (routeRole == RouteRole.RETURN || routeRole == RouteRole.EDIT_RETURN) {
                    BookingRouteState.confirmReturn();
                }
                SwingUtilities.invokeLater(() -> {
                    if (fitAfterLoad) {
                        fitRouteToView();
                    }
                    repaint();
                });
            } catch (Exception ex) {
                BookingRouteState.setRoadRoute(expectedReturn, null);
                ex.printStackTrace();
                SwingUtilities.invokeLater(() -> repaint());
            }
        }, "RoadRouteThread").start();
    }

    private GeoPosition getRouteStartPosition() {
        return routeRole == RouteRole.NONE ? null : DEFAULT_ROUTE_START;
    }

    private List<GeoPosition> getRoadRoute(GeoPosition pickup, GeoPosition returned) throws IOException {
        String url = String.format(Locale.US,
                "https://router.project-osrm.org/route/v1/driving/%f,%f;%f,%f?overview=full&geometries=geojson&alternatives=true",
                pickup.getLongitude(), pickup.getLatitude(),
                returned.getLongitude(), returned.getLatitude());
        HttpRequest req = HttpRequest.get(url)
                .userAgent("MyApp/1.0 (contact: artoco86@gmail.com)")
                .header("Accept", "application/json");
        String body = req.body();
        if (req.code() != 200 || body == null || !body.trim().startsWith("{")) {
            throw new IOException("Unable to calculate the road route.");
        }

        JSONArray routes = new JSONObject(body).optJSONArray("routes");
        if (routes == null || routes.length() == 0) {
            throw new IOException("No driving route was found.");
        }

        JSONObject shortestRoute = routes.getJSONObject(0);
        double shortestDistance = shortestRoute.optDouble("distance", Double.MAX_VALUE);
        for (int i = 1; i < routes.length(); i++) {
            JSONObject candidate = routes.getJSONObject(i);
            double candidateDistance = candidate.optDouble("distance", Double.MAX_VALUE);
            if (candidateDistance < shortestDistance) {
                shortestDistance = candidateDistance;
                shortestRoute = candidate;
            }
        }

        JSONArray coordinates = shortestRoute
                .getJSONObject("geometry")
                .getJSONArray("coordinates");
        List<GeoPosition> route = new ArrayList<>();
        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray coordinate = coordinates.getJSONArray(i);
            route.add(new GeoPosition(coordinate.getDouble(1), coordinate.getDouble(0)));
        }
        return route;
    }

    private GeoPosition findPosition(String address) throws IOException {
        String url = "https://nominatim.openstreetmap.org/search?q="
                + URLEncoder.encode(address, "UTF-8")
                + "&format=jsonv2&limit=1";
        HttpRequest req = HttpRequest.get(url)
                .userAgent("MyApp/1.0 (contact: artoco86@gmail.com)")
                .header("Accept", "application/json");
        String body = req.body();
        if (req.code() != 200 || body == null || !body.trim().startsWith("[")) {
            throw new IOException("Unable to locate the saved address.");
        }
        JSONArray results = new JSONArray(body);
        if (results.length() == 0) {
            throw new IOException("Saved address was not found on the map.");
        }
        JSONObject result = results.getJSONObject(0);
        return new GeoPosition(result.getDouble("lat"), result.getDouble("lon"));
    }

    public String getLocation(GeoPosition pos) throws IOException, JSONException {

        // jsonv2 is more consistent than json
        String url = String.format(Locale.US,
                "https://nominatim.openstreetmap.org/reverse?lat=%f&lon=%f&format=jsonv2",
                pos.getLatitude(), pos.getLongitude());

        HttpRequest req = HttpRequest.get(url)
                // Nominatim requires a valid User-Agent
                .userAgent("MyApp/1.0 (contact: artoco86@gmail.com)")
                .header("Accept", "application/json");

        int code = req.code();
        String body = req.body();
        body = (body == null) ? "" : body.trim();

        // If blocked / rate-limited, body may be HTML/text, not JSON
        if (code != 200) {
            throw new IOException("Nominatim HTTP " + code + ": "
                    + body.substring(0, Math.min(body.length(), 300)));
        }

        if (!body.startsWith("{")) {
            throw new IOException("Unexpected response (not JSON): "
                    + body.substring(0, Math.min(body.length(), 300)));
        }

        JSONObject json = new JSONObject(body);
        return json.optString("display_name", "");
    }

    @Override
    protected void paintComponent(Graphics g) {
        centerReturnMapOnConfirmedPickup();
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        paintRoute(g2);
        paintSavedPins(g2);

        int x = getWidth() / 2 - 12;
        int y = getHeight() / 2 - 24;
        g2.drawImage(image, x, y, null);

        Area area = new Area(new Rectangle.Double(0, 0, getWidth(), getHeight()));
        area.subtract(new Area(new RoundRectangle2D.Double(5, 5, getWidth() - 10, getHeight() - 10, 20, 20)));
        g2.setColor(new Color(255, 255, 255));
        g2.fill(area);
        g2.dispose();
    }

    private void paintSavedPins(Graphics2D g2) {
        if (routeRole == RouteRole.PICKUP && BookingRouteState.isPickupConfirmed()) {
            paintPin(g2, DEFAULT_ROUTE_START);
            paintPin(g2, BookingRouteState.getPickupPosition());
            paintPin(g2, draftSelectionPosition);
            return;
        }
        if (routeRole == RouteRole.EDIT_PICKUP && fixedDisplayPosition != null) {
            paintPin(g2, DEFAULT_ROUTE_START);
            paintPin(g2, fixedDisplayPosition);
            paintPin(g2, draftSelectionPosition);
            return;
        }
        if (routeRole == RouteRole.EDIT_RETURN) {
            paintPin(g2, DEFAULT_ROUTE_START);
            paintPin(g2, fixedDisplayPosition);
            paintPin(g2, draftSelectionPosition);
        }
    }

    private void paintPin(Graphics2D g2, GeoPosition position) {
        if (position == null || getTileFactory() == null) {
            return;
        }
        Rectangle viewport = getViewportBounds();
        Point2D point = getTileFactory().geoToPixel(position, getZoom());
        int x = (int) Math.round(point.getX() - viewport.getX()) - 12;
        int y = (int) Math.round(point.getY() - viewport.getY()) - 24;
        g2.drawImage(image, x, y, null);
    }

    private void centerReturnMapOnConfirmedPickup() {
        if (routeRole != RouteRole.RETURN || centeredOnConfirmedPickup
                || !BookingRouteState.isPickupConfirmed()
                || BookingRouteState.getReturnPosition() != null) {
            return;
        }
        GeoPosition pickup = BookingRouteState.getPickupPosition();
        if (pickup != null) {
            centeredOnConfirmedPickup = true;
            setCenterPosition(pickup);
        }
    }

    private void paintRoute(Graphics2D g2) {
        if (routeRole == RouteRole.NONE) {
            return;
        }
        GeoPosition routeStart = getRouteStartPosition();
        GeoPosition destination = isReturnRole()
                ? BookingRouteState.getReturnPosition()
                : BookingRouteState.getPickupPosition();
        if (routeStart == null || getTileFactory() == null) {
            return;
        }

        Rectangle viewport = getViewportBounds();

        List<GeoPosition> route = BookingRouteState.getRoadRoute();
        if (BookingRouteState.isRoadRouteFor(destination) && route.size() > 1) {
            g2.setColor(new Color(204, 0, 0));
            g2.setStroke(new BasicStroke(4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            Point2D previous = null;
            for (GeoPosition position : route) {
                Point2D point = getTileFactory().geoToPixel(position, getZoom());
                Point2D visible = new Point2D.Double(
                        point.getX() - viewport.getX(),
                        point.getY() - viewport.getY());
                if (previous != null) {
                    g2.drawLine((int) previous.getX(), (int) previous.getY(),
                            (int) visible.getX(), (int) visible.getY());
                }
                previous = visible;
            }
        }

        if (routeRole == RouteRole.RETURN || routeRole == RouteRole.PICKUP) {
            paintPin(g2, routeStart);
            if (destination != null) {
                paintPin(g2, destination);
            }
            paintPin(g2, draftSelectionPosition);
        }
    }
}
