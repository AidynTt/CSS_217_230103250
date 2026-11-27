package week13_practice.task10;

class Route {
    String name;
    double distance;
    int time;
    boolean toll;

    public Route(String name, double distance,
                 int time, boolean toll) {
        this.name = name;
        this.distance = distance;
        this.time = time;
        this.toll = toll;
    }
}

interface RoutingStrategy {
    Route selectRoute(Route[] routes);
}

class FastestTimeRouting implements RoutingStrategy {
    public Route selectRoute(Route[] routes) {
        Route best = routes[0];

        for (Route route : routes) {
            if (route.time < best.time) {
                best = route;
            }
        }

        return best;
    }
}

class ShortestDistanceRouting implements RoutingStrategy {
    public Route selectRoute(Route[] routes) {
        Route best = routes[0];

        for (Route route : routes) {
            if (route.distance < best.distance) {
                best = route;
            }
        }

        return best;
    }
}

class AvoidTollsRouting implements RoutingStrategy {
    public Route selectRoute(Route[] routes) {
        Route best = null;

        for (Route route : routes) {
            if (!route.toll) {
                if (best == null || route.time < best.time) {
                    best = route;
                }
            }
        }

        return best;
    }
}

// Subsystems
class GpsTracker {
    private String currentLocation;

    public void start(String origin) {
        currentLocation = origin;
        System.out.println("GPS started at: " + origin);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }
}

class MapTileLoader {
    public void load(String origin, String destination) {
        System.out.println("Loading map: "
                + origin + " -> " + destination);
    }
}

class TrafficFeedClient {
    public Route[] getRoutes(String origin,
                             String destination) {
        System.out.println("Checking traffic conditions");

        return new Route[]{
            new Route("Highway", 15, 20, true),
            new Route("City Road", 10, 35, false),
            new Route("Alternative Road", 12, 25, false)
        };
    }
}

class VoiceGuidanceUnit {
    public void guide(Route route) {
        System.out.println("Selected route: " + route.name);
        System.out.println("Distance: " + route.distance + " km");
        System.out.println("Estimated time: " + route.time + " min");
        System.out.println("Toll road: "
                + (route.toll ? "Yes" : "No"));
        System.out.println("Voice guidance activated");
    }
}

class NavigationFacade {
    private RoutingStrategy strategy;
    private String destination;
    private boolean navigating = false;

    private final GpsTracker gps = new GpsTracker();
    private final MapTileLoader map = new MapTileLoader();
    private final TrafficFeedClient traffic =
            new TrafficFeedClient();
    private final VoiceGuidanceUnit voice =
            new VoiceGuidanceUnit();

    public NavigationFacade(RoutingStrategy strategy) {
        this.strategy = strategy;
    }

    public void startNavigation(String origin,
                                String destination) {
        this.destination = destination;

        gps.start(origin);
        navigating = true;

        calculateRoute();
    }

    public void setStrategy(RoutingStrategy strategy) {
        this.strategy = strategy;

        if (navigating) {
            System.out.println("Recalculating route...");
            calculateRoute();
        }
    }

    private void calculateRoute() {
        String origin = gps.getCurrentLocation();

        map.load(origin, destination);

        Route[] routes = traffic.getRoutes(
                origin, destination);

        Route selected = strategy.selectRoute(routes);

        if (selected == null) {
            System.out.println("No suitable route found");
            return;
        }

        voice.guide(selected);
    }
}

// Client
public class Main {
    public static void main(String[] args) {
        NavigationFacade navigation =
                new NavigationFacade(new FastestTimeRouting());

        System.out.println("Fastest Route:");
        navigation.startNavigation("Home", "University");

        System.out.println("\nShortest Route:");
        navigation.setStrategy(new ShortestDistanceRouting());

        System.out.println("\nAvoid Toll Roads:");
        navigation.setStrategy(new AvoidTollsRouting());
    }
}

