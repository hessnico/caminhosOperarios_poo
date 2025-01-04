package caminhosOperarios;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class containing static methods for generating Google Maps URLs and parsing coordinates.
 *
 * Methods:
 *    generateGoogleMapsLocalURL: String
 *        Generates a Google Maps URL for a specific location based on WKT (Well-Known Text) coordinate string.
 *        The method extracts the latitude and longitude values from the provided WKT string.
 *
 *    generateGoogleMapsRouteURL: String
 *        Generates a Google Maps route URL for multiple coordinates.
 *        This method processes a string of coordinates, formats them into a route URL.
 *
 *    getWhatsInsideParenthesis: String
 *        Extracts and returns the content inside parentheses from a given string using regex matching.
 *
 *    retrieveWKTLongLat: String[]
 *        Extracts and returns the latitude and longitude from a WKT (Well-Known Text) coordinate string.
 *        The coordinates are returned as a string array where the first element is latitude and the second is longitude.
 *
 *    retrieveListOfCoordinates: List<String[]>
 *        Parses and retrieves a list of coordinates (latitude, longitude) from a string containing multiple coordinates.
 *        The coordinates are returned as a list of string arrays, with each array containing a latitude and longitude.
 *
 */

public class utils {

    public static String generateGoogleMapsLocalURL(String wkt) {
        StringBuilder url = new StringBuilder("https://www.google.com/maps?q=");

        String[] valoresLatLng = utils.retrieveWKTLongLat(wkt);

        return(url.append(valoresLatLng[0]).append(",").append(valoresLatLng[1]).toString());
    }

    public static String generateGoogleMapsRouteURL(String coordinates) {
        StringBuilder url = new StringBuilder("https://www.google.com/maps/dir/");

        List<String[]> listCoordinates = retrieveListOfCoordinates(coordinates);

        for (String[] coord : listCoordinates) {
            url.append(coord[1]).append(",").append(coord[0]).append("/");
        }

        return url.toString();
    }
    public static String getWhatsInsideParenthesis(String dummyString) {
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(dummyString);
        String toReturnString= "";
        while (m.find()) {
            toReturnString = m.group(1);
        }

        return(toReturnString);
    }

    public static String[] retrieveWKTLongLat(String coordinates) {
        coordinates = getWhatsInsideParenthesis(coordinates);
        String[] latLng = coordinates.trim().split(" ");

        return new String[]{latLng[1].trim(),latLng[0].trim()};
    }

    public static List<String[]> retrieveListOfCoordinates(String coordinates) {
        String cleanCoordinates = getWhatsInsideParenthesis(coordinates);
        List<String[]> coordinatesToRoute = new ArrayList<>();

        for (String coord : List.of(cleanCoordinates.split(","))) {
            String[] latLng = coord.trim().split(" ");
            coordinatesToRoute.add(latLng);
        }

        return(coordinatesToRoute);
    }
}
