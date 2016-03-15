package domain;

import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author thomas
 */
public interface DataSource {

    String getName();

    String getUnit();

    Map<LocalDate, Double> getData();
}
